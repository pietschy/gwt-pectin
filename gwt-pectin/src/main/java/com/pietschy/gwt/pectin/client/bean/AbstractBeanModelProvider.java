package com.pietschy.gwt.pectin.client.bean;

import com.pietschy.gwt.pectin.client.ListModelProvider;
import com.pietschy.gwt.pectin.client.ValueModelProvider;
import com.pietschy.gwt.pectin.client.condition.OrFunction;
import com.pietschy.gwt.pectin.client.value.ReducingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.HashMap;

/**
 * AbstractBeanModelProvider provides common behaviour for all variants that provide value and
 * list models from bean properties.
 */
public abstract class AbstractBeanModelProvider<B> implements BeanPropertyAdapter<B>, ValueModelProvider, ListModelProvider
{
   private HashMap<Key<?>, BeanPropertyValueModel<B, ?>> valueModels = new HashMap<Key<?>, BeanPropertyValueModel<B, ?>>();
   private HashMap<Key<?>, BeanPropertyListModel<B, ?>> listModels = new HashMap<Key<?>, BeanPropertyListModel<B, ?>>();

   private ReducingValueModel<Boolean, Boolean> dirtyModel = new ReducingValueModel<Boolean, Boolean>(new OrFunction());

   private CollectionConverters collectionConverters = new CollectionConverters();

   protected AbstractBeanModelProvider()
   {
   }

   /**
    * Copies the current state of the provider to the specified bean.  No changes are made
    * to the dirty state of the value models.
    *
    * @param bean            the bean to update
    * @param clearDirtyState <code>true</code> to reset the dirty state to <code>false</code> to
    *                        leave the dirty state unchanged.
    */
   protected void copyTo(final B bean, final boolean clearDirtyState)
   {
      // performance could be improved here if the dirty model went deaf for a bit.
      withEachModel(new ModelVisitor<B>()
      {
         public void visit(BeanPropertyValueModel<B, ?> model)
         {
            model.copyTo(bean, clearDirtyState);
         }

         public void visit(BeanPropertyListModel<B, ?> listModel)
         {
            listModel.copyTo(bean, clearDirtyState);
         }
      });
   }

   /**
    * Reads values from the specified bean.  After the read all value models will have a
    * dirty state of <code>false</code>.
    *
    * @param bean the bean to read the values from.
    */
   protected void readFrom(final B bean)
   {
      withEachModel(new ModelVisitor<B>()
      {
         public void visit(BeanPropertyValueModel<B, ?> model)
         {
            model.readFrom(bean);
         }

         public void visit(BeanPropertyListModel<B, ?> listModel)
         {
            listModel.readFrom(bean);
         }
      });
   }

   /**
    * Resets the dirty state of all models.  After this call all value models will have a dirty state of
    * <code>false</code>.  The current state of the models is recorded and any future changes will update
    * the dirty state accordingly.
    */
   protected void checkpoint()
   {
      withEachModel(new ModelVisitor<B>()
      {
         public void visit(BeanPropertyValueModel<B, ?> model)
         {
            model.checkpoint();
         }

         public void visit(BeanPropertyListModel<B, ?> listModel)
         {
            listModel.checkpoint();
         }
      });
   }

   /**
    * Resets all the models back to their last checkpointed state.  If checkpoint hasn't been called then
    * it will revert to the last call to readFrom.
    */
   protected void revertToCheckpoint()
   {
      withEachModel(new ModelVisitor<B>()
      {
         public void visit(BeanPropertyValueModel<B, ?> model)
         {
            model.revertToCheckpoint();
         }

         public void visit(BeanPropertyListModel<B, ?> listModel)
         {
            listModel.revertToCheckpoint();
         }
      });
   }

   public ValueModel<Boolean> getDirtyModel()
   {
      return dirtyModel;
   }

   protected void withEachModel(final ModelVisitor<B> modelVisitor)
   {
      // we only update the dirty model after we've finished updating all
      // the models, otherwise we'll get a lot of recomputing for nothing.
      dirtyModel.recomputeAfterRunning(new Runnable()
      {
         public void run()
         {
            // performance could be improved here if the dirty model went deaf for a bit.
            for (BeanPropertyValueModel<B, ?> model : valueModels.values())
            {
               modelVisitor.visit(model);
            }

            for (BeanPropertyListModel<B, ?> model : listModels.values())
            {
               modelVisitor.visit(model);
            }
         }
      });
   }


   /**
    * Gets a {@link com.pietschy.gwt.pectin.client.list.ListModel} based on the specified property name and value type.  Property types
    * of the generic interface types {@link java.util.Collection}, {@link java.util.List}, {@link java.util.Set}, {@link java.util.SortedSet} are supported
    * out of the box.  Additional types can be supported by registering a suitable {@link CollectionConverter}.
    * <p/>
    * Multiple calls to this method will return the same model.
    *
    * @param propertyName the name of the property.
    * @param valueType    the type contained by the collection
    * @return the {@link com.pietschy.gwt.pectin.client.list.ListModel} for the specified property.  Multiple calls to this method will return the same
    *         model.
    * @throws UnknownPropertyException if the bean doesn't define the specified property.
    * @throws UnsupportedCollectionTypeException
    *                                  if a suitable {@link CollectionConverter} hasn't been registered
    *                                  for the bean property collection type.
    * @see #registerCollectionConverter(Class, CollectionConverter)
    */
   @SuppressWarnings("unchecked")
   public <T> BeanPropertyListModel<B, T> getListModel(String propertyName, Class<T> valueType)
      throws UnknownPropertyException, UnsupportedCollectionTypeException,
             IncorrectElementTypeException, NotCollectionPropertyException
   {
      Key<T> key = new Key<T>(valueType, propertyName);
      BeanPropertyListModel<B, T> listModel = (BeanPropertyListModel<B, T>) listModels.get(key);

      if (listModel == null)
      {
         Class collectionType = getPropertyType(propertyName);
         Class elementType = getElementType(propertyName);

         if (!valueType.equals(elementType))
         {
            throw new IncorrectElementTypeException(valueType, elementType);
         }

         CollectionConverter converter = collectionConverters.getConverter(collectionType);

         if (converter == null)
         {
            throw new UnsupportedCollectionTypeException(collectionType);
         }

         listModel = createListValueModel(propertyName, converter);
         listModels.put(key, listModel);
         dirtyModel.addSourceModel(listModel.getDirtyModel());
      }

      return listModel;
   }

   protected <T> BeanPropertyListModel<B, T> createListValueModel(String propertyName, CollectionConverter converter)
   {
      return new BeanPropertyListModel<B, T>(this, propertyName, converter);
   }

   /**
    * Gets a {@link ValueModel} for the specified bean property and the specified type.  Multiple calls to this method
    * will return the same model.
    *
    * @param propertyName the name of the property.
    * @param modelType    the type of the property.
    * @return a {@link ValueModel} for the specified bean property.  Multiple calls to this method
    *         will return the same model.
    * @throws UnknownPropertyException       if the property isn't defined by the bean.
    * @throws IncorrectPropertyTypeException if the type of the property doesn't match the model type.
    */
   @SuppressWarnings("unchecked")
   public <T> BeanPropertyValueModel<B, T> getValueModel(String propertyName, Class<T> modelType) throws UnknownPropertyException, IncorrectPropertyTypeException
   {
      Class beanPropertyType = getPropertyType(propertyName);

      if (!modelType.equals(beanPropertyType))
      {
         throw new IncorrectPropertyTypeException(modelType, beanPropertyType);
      }

      Key<T> key = new Key<T>(modelType, propertyName);
      BeanPropertyValueModel<B, T> valueModel = (BeanPropertyValueModel<B, T>) valueModels.get(key);

      if (valueModel == null)
      {
         valueModel = createValueModel(propertyName);
         valueModels.put(key, valueModel);
         dirtyModel.addSourceModel(valueModel.getDirtyModel());
      }

      return valueModel;
   }

   protected <T> BeanPropertyValueModel<B, T> createValueModel(String propertyName)
   {
      return new BeanPropertyValueModel<B, T>(this, propertyName);
   }


   /**
    * Registers a new converter for converting between collection based bean properties and the ListModel.
    * This allows uses to support collection types other than the generic interface types on their beans.
    *
    * @param collectionClass the collection type of the bean property.
    * @param converter       the converter to use for bean properties of the specified collection type.
    */
   public <T> void registerCollectionConverter(Class<T> collectionClass, CollectionConverter<T> converter)
   {
      collectionConverters.register(collectionClass, converter);
   }


   private static class Key<T>
   {
      private Class<T> type;
      private String propertyName;

      private Key(Class<T> type, String propertyName)
      {
         this.type = type;
         this.propertyName = propertyName;
      }

      @SuppressWarnings("unchecked")
      public boolean equals(Object o)
      {
         if (this == o)
         {
            return true;
         }
         if (o == null || getClass() != o.getClass())
         {
            return false;
         }

         Key<T> key = (Key<T>) o;

         if (propertyName != null ? !propertyName.equals(key.propertyName) : key.propertyName != null)
         {
            return false;
         }

         if (type != null ? !type.equals(key.type) : key.type != null)
         {
            return false;
         }

         return true;
      }

      public int hashCode()
      {
         int result;
         result = (type != null ? type.hashCode() : 0);
         result = 31 * result + (propertyName != null ? propertyName.hashCode() : 0);
         return result;
      }
   }

   static interface ModelVisitor<B>
   {
      public void visit(BeanPropertyValueModel<B, ?> model);

      public void visit(BeanPropertyListModel<B, ?> model);
   }
}

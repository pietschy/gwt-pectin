package com.pietschy.gwt.pectin.client.bean;

import com.pietschy.gwt.pectin.client.ListModelProvider;
import com.pietschy.gwt.pectin.client.ValueModelProvider;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * AbstractBeanModelProvider provides common behaviour for all providers that provide
 * models from bean properties.
 */
public abstract class AbstractBeanModelProvider<B> implements BeanPropertyAdapter<B>, ValueModelProvider<String>, ListModelProvider<String>
{
   PropertyModelRegistry<B> registry = new PropertyModelRegistry<B>();

   private CollectionConverters collectionConverters = new CollectionConverters();

   protected AbstractBeanModelProvider()
   {
   }

   /**
    * Copies the current state of the provider to the specified bean.
    *
    * @param bean            the bean to update
    * @param clearDirtyState <code>true</code> to reset the dirty state to <code>false</code> to
    *                        leave the dirty state unchanged.
    */
   protected void copyTo(final B bean, final boolean clearDirtyState)
   {
      // performance could be improved here if the dirty model went deaf for a bit.
      registry.withEachModel(new PropertyModelVisitor<B>()
      {
         public void visit(BeanPropertyModelBase<B> model)
         {
            if (model.isMutable())
            {
               model.copyTo(bean, clearDirtyState);
            }
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
      registry.withEachModel(new PropertyModelVisitor<B>()
      {
         public void visit(BeanPropertyModelBase<B> model)
         {
            model.readFrom(bean);
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
      registry.withEachModel(new PropertyModelVisitor<B>()
      {
         public void visit(BeanPropertyModelBase<B> model)
         {
            model.checkpoint();
         }
      });
   }

   /**
    * Resets all the models back to their last check pointed state.  If checkpoint hasn't been called then
    * it will revert to the last call to readFrom.
    */
   protected void revertToCheckpoint()
   {
      registry.withEachModel(new PropertyModelVisitor<B>()
      {
         public void visit(BeanPropertyModelBase<B> model)
         {
            model.revertToCheckpoint();
         }
      });
   }

   public ValueModel<Boolean> getDirtyModel()
   {
      return registry.getDirtyModel();
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
      PropertyKey<T> key = new PropertyKey<T>(valueType, propertyName);
      BeanPropertyListModel<B, T> listModel = registry.getListModel(key);

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

         registry.add(key, listModel);
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

      PropertyKey<T> key = new PropertyKey<T>(modelType, propertyName);

      BeanPropertyValueModel<B, T> valueModel = registry.getValueModel(key);

      if (valueModel == null)
      {
         valueModel = createValueModel(propertyName);

         registry.add(key, valueModel);
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


}

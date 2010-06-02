package com.pietschy.gwt.pectin.client.bean;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.ListModelProvider;
import com.pietschy.gwt.pectin.client.ValueModelProvider;
import com.pietschy.gwt.pectin.client.value.AbstractMutableValueModel;
import com.pietschy.gwt.pectin.client.value.DelegatingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * AbstractBeanModelProvider provides common behaviour for all providers that provide
 * models from bean properties.
 */
public abstract class BeanModelProvider<B> extends AbstractMutableValueModel<B> implements ValueModelProvider<String>, ListModelProvider<String>, BeanIntrospector
{
   private DelegatingValueModel<B> source = new DelegatingValueModel<B>(new ValueHolder<B>());
   private PropertyModelRegistry registry = new PropertyModelRegistry();
   private CollectionConverters collectionConverters = new CollectionConverters();

   private ValueHolder<Boolean> autoCommit = new ValueHolder<Boolean>(false);

   protected BeanModelProvider()
   {
      // our actual value is held in our delegating model so we can swap it out.
      source.addValueChangeHandler(new ValueChangeHandler<B>()
      {
         public void onValueChange(ValueChangeEvent<B> event)
         {
            fireValueChangeEvent(event.getValue());
         }
      });
   }


   public B getValue()
   {
      return source.getValue();
   }

   /**
    * Sets the bean to use as the root for all models created by this provider.  All value models will update after this
    * method has been called.
    * <p/>
    * <b>Please note<b/> if {@link #setBeanSource(com.pietschy.gwt.pectin.client.value.ValueModel)} has been called
    * with a model that isn't an instance of {@link com.pietschy.gwt.pectin.client.value.MutableValueModel}
    * then this method will fail.
    *
    * @param bean the bean
    */
   public void setValue(B value)
   {
      source.setValue(value);
   }

   /**
    * Sets the {@link ValueModel} to be used as the source of this provider.  All changes to the source
    * model will be tracked.
    * <p/>
    * <b>Please note<b/> that if the source is not an instance of {@link com.pietschy.gwt.pectin.client.value.MutableValueModel}
    * then any subsequent calls to {@link #setBean(Object)} will fail.
    *
    * @param beanSource the {@link com.pietschy.gwt.pectin.client.value.ValueModel} containing the source bean.
    */
   public void setBeanSource(ValueModel<B> beanSource)
   {
      source.setDelegate(beanSource);
   }


   public void setAutoCommit(boolean autoCommit)
   {
      this.autoCommit.setValue(autoCommit);
   }

   public boolean isAutoCommit()
   {
      return this.autoCommit.getValue();
   }

   /**
    * Checkpoints the current state of all value models and clears the dirty state of all models.
    */
   public void checkpoint()
   {
      registry.withEachModel(new PropertyModelVisitor()
      {
         public void visit(BeanPropertyModelBase model)
         {
            model.checkpoint();
         }
      });
   }

   /**
    * Resets all the models back to their last check pointed state.  If checkpoint hasn't been called then
    * it will revert to the state when the source bean was last configured.
    */
   public void revertToCheckpoint()
   {
      registry.withEachModel(new PropertyModelVisitor()
      {
         public void visit(BeanPropertyModelBase model)
         {
            model.revertToCheckpoint();
         }
      });
   }

   /**
    * Writes all outstanding changes to the underlying bean graph and clears all the dirty state.
    */
   public void commit()
   {
      commit(true);
   }

   /**
    * Writes all outstanding changes to the underlying bean graph.  If <code>checkpoint</code>
    * is <code>true</code> then the provider will be check pointed and the dirty state cleared.
    * If <code>false</code> then only the changes are written to the bean graph and the dirty state
    * remains as is.
    * <p/>
    * This method is useful if you don't want to clear the dirty state until some time later, e.g. after
    * a RPC call has succeeded.
    *
    * @param checkpoint <code>true</code> to checkpoing the provider and clear the dirty state, <code>false</code>
    *                   to leave the dirty state as is.
    */
   public void commit(final boolean checkpoint)
   {
      registry.withEachModel(new PropertyModelVisitor()
      {
         public void visit(BeanPropertyModelBase model)
         {
            if (model.isMutableProperty())
            {
               model.writeToSource(checkpoint);
            }
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
    * out of the box.  Additional types can be supported by registering a suitable {@link com.pietschy.gwt.pectin.client.bean.CollectionConverter}.
    * <p/>
    * Multiple calls to this method will return the same model.
    *
    * @param propertyPath the name of the property.
    * @param valueType    the type contained by the collection
    * @return the {@link com.pietschy.gwt.pectin.client.list.ListModel} for the specified property.  Multiple calls to this method will return the same
    *         model.
    * @throws com.pietschy.gwt.pectin.client.bean.UnknownPropertyException
    *          if the bean doesn't define the specified property.
    * @throws com.pietschy.gwt.pectin.client.bean.UnsupportedCollectionTypeException
    *          if a suitable {@link com.pietschy.gwt.pectin.client.bean.CollectionConverter} hasn't been registered
    *          for the bean property collection type.
    * @see #registerCollectionConverter(Class, com.pietschy.gwt.pectin.client.bean.CollectionConverter)
    */
   @SuppressWarnings("unchecked")
   public <T> BeanPropertyListModel<T> getListModel(String propertyPath, Class<T> valueType)
      throws UnknownPropertyException, UnsupportedCollectionTypeException,
             IncorrectElementTypeException, NotCollectionPropertyException
   {
      PropertyKey<T> key = new PropertyKey<T>(valueType, propertyPath);
      BeanPropertyListModel<T> listModel = registry.getListModel(key);

      if (listModel == null)
      {
         Class collectionType = getPropertyType(propertyPath);
         Class elementType = getElementType(propertyPath);

         if (!valueType.equals(elementType))
         {
            throw new IncorrectElementTypeException(valueType, elementType);
         }

         CollectionConverter converter = collectionConverters.getConverter(collectionType);

         if (converter == null)
         {
            throw new UnsupportedCollectionTypeException(collectionType);
         }

         ValueModel<?> sourceModel = getSourceModel(key);
         BeanPropertyAccessor accessor = getBeanAccessorForPropertyPath(propertyPath);
         listModel = createListValueModel(sourceModel, key, accessor, converter, autoCommit);

         registry.add(key, listModel);
      }

      return listModel;
   }

   protected <T> BeanPropertyListModel<T> createListValueModel(ValueModel<?> sourceModel, PropertyKey<T> propertyKey, BeanPropertyAccessor accessor, CollectionConverter converter, ValueModel<Boolean> autoCommit)
   {
      return new BeanPropertyListModel<T>(sourceModel, propertyKey, accessor, converter, autoCommit);
   }

   /**
    * Gets a {@link com.pietschy.gwt.pectin.client.value.ValueModel} for the specified bean property and the specified type.  Multiple calls to this method
    * will return the same model.
    *
    * @param propertyPath the name of the property.
    * @param modelType    the type of the property.
    * @return a {@link com.pietschy.gwt.pectin.client.value.ValueModel} for the specified bean property.  Multiple calls to this method
    *         will return the same model.
    * @throws com.pietschy.gwt.pectin.client.bean.UnknownPropertyException
    *          if the property isn't defined by the bean.
    * @throws com.pietschy.gwt.pectin.client.bean.IncorrectPropertyTypeException
    *          if the type of the property doesn't match the model type.
    */
   @SuppressWarnings("unchecked")
   public <T> BeanPropertyValueModel<T> getValueModel(String propertyPath, Class<T> modelType) throws UnknownPropertyException, IncorrectPropertyTypeException
   {
      Class beanPropertyType = getPropertyType(propertyPath);

      if (!modelType.equals(beanPropertyType))
      {
         throw new IncorrectPropertyTypeException(modelType, beanPropertyType);
      }

      PropertyKey<T> key = new PropertyKey<T>(modelType, propertyPath);

      BeanPropertyValueModel<T> valueModel = registry.getValueModel(key);

      if (valueModel == null)
      {
         BeanPropertyAccessor accessor = getBeanAccessorForPropertyPath(propertyPath);
         ValueModel<?> sourceModel = getSourceModel(key);

         valueModel = createValueModel(sourceModel, key, accessor, autoCommit);

         registry.add(key, valueModel);
      }

      return valueModel;
   }

   protected <T> BeanPropertyValueModel<T> createValueModel(ValueModel<?> sourceModel, PropertyKey<T> propertyKey, BeanPropertyAccessor accessor, ValueModel<Boolean> autoCommit)
   {
      return new BeanPropertyValueModel<T>(sourceModel, propertyKey, accessor, autoCommit);
   }

   @SuppressWarnings("unchecked")
   ValueModel<?> getSourceModel(PropertyKey key)
   {
      if (key.isRootProperty())
      {
         return this;
      }
      else
      {
         String parentPath = key.getParentPath();
         return getValueModel(parentPath, getPropertyType(parentPath));
      }
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
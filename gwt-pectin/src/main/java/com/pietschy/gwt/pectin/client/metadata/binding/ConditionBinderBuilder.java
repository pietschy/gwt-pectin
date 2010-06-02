package com.pietschy.gwt.pectin.client.metadata.binding;

import com.pietschy.gwt.pectin.client.Field;
import com.pietschy.gwt.pectin.client.binding.AbstractValueBinding;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.getMetadata;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 24, 2010
 * Time: 1:23:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConditionBinderBuilder<T>
{
   private MetadataBinder binder;
   private T target;
   private ConditionBinderWidgetAction<T> action;
   private ConditionBinderMetadataAction<T> metadataAction;

   public ConditionBinderBuilder(MetadataBinder metadataBinder, T target, ConditionBinderMetadataAction<T> metadataAction, ConditionBinderWidgetAction<T> action)
   {
      binder = metadataBinder;
      this.target = target;
      this.metadataAction = metadataAction;
      this.action = action;
   }

   public void when(ValueModel<Boolean> condition)
   {
      binder.registerDisposableAndUpdateTarget(new ConditionBinding<T>(target, condition, action));
   }

   /**
    * This method is deprecated, use {@link com.pietschy.gwt.pectin.client.metadata.MetadataPlugin#metadataOf(com.pietschy.gwt.pectin.client.Field)} instead.
    * E.g. <pre>binder.show(someWidget).when(metadataOf(someField).isVisible());</pre>
    * @deprecated use {@link com.pietschy.gwt.pectin.client.metadata.MetadataPlugin#metadataOf(com.pietschy.gwt.pectin.client.Field)} instead.
    * E.g. <pre>binder.show(someWidget).when(metadataOf(someField).isVisible());</pre>
    * @param field the field of interest.
    */
   @Deprecated
   public void usingMetadataOf(Field field)
   {
      ValueModel<Boolean> condition = metadataAction.getModel(getMetadata(field));
      binder.registerDisposableAndUpdateTarget(new ConditionBinding<T>(target, condition, metadataAction));
   }

   private class ConditionBinding<T> extends AbstractValueBinding<Boolean>
   {
      private T target;
      private ConditionBinderWidgetAction<T> action;

      private ConditionBinding(T target, ValueModel<Boolean> condition, ConditionBinderWidgetAction<T> action)
      {
         super(condition);
         this.target = target;
         this.action = action;
      }

      @Override
      protected void updateTarget(Boolean value)
      {
         action.apply(target, value != null ? value : false);
      }

      @Override
      public T getTarget()
      {
         return target;
      }
   }

}

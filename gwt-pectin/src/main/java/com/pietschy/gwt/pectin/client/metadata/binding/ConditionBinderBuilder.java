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
   protected MetadataBinder binder;
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
      binder.registerBindingAndUpdateTarget(new ConditionBinding<T>(target, condition, action));
   }

   public void usingMetadataOf(Field field)
   {
      ValueModel<Boolean> condition = metadataAction.getModel(getMetadata(field));
      binder.registerBindingAndUpdateTarget(new ConditionBinding<T>(target, condition, metadataAction));
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
         action.apply(target, value);
      }

      @Override
      public T getTarget()
      {
         return target;
      }
   }

}

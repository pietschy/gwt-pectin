package com.pietschy.gwt.pectin.client.metadata.binding;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.Field;
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
   private T target;
   private ConditionBinderWidgetAction<T> action;
   private ConditionBinderMetadateAction<T> metadataAction;
   private ValueModel<Boolean> condition;

   public ConditionBinderBuilder(T target, ConditionBinderMetadateAction<T> metadateAction, ConditionBinderWidgetAction<T> action)
   {
      this.target = target;
      this.metadataAction = metadateAction;
      this.action = action;
   }

   public void when(ValueModel<Boolean> condition)
   {
      condition.addValueChangeHandler(new ValueChangeHandler<Boolean>()
      {
         public void onValueChange(ValueChangeEvent<Boolean> event)
         {
            action.apply(target, event.getValue());
         }
      });
      action.apply(target, condition.getValue());
   }

   public void usingMetadataOf(Field field)
   {
      ValueModel<Boolean> condition = metadataAction.getModel(getMetadata(field));
      condition.addValueChangeHandler(new ValueChangeHandler<Boolean>()
      {
         public void onValueChange(ValueChangeEvent<Boolean> event)
         {
            metadataAction.apply(target, event.getValue());
         }
      });
   }


}

package com.pietschy.gwt.pectin.client.binding;

import com.pietschy.gwt.pectin.client.*;

/**
 * FormBinder provides a builders to bind widgets to field models.  During the binding process
 * any {@link BindingBuilderCallback}s installed by plugins will be automatically invoked.
 */
public class FormBinder extends Binder
{
   public <T> MutableValueBindingBuilder<T> bind(final FieldModel<T> field)
   {
      return new MutableValueBindingBuilder<T>(field, new BindingBuilderCallback()
      {
         public void onBindingCreated(AbstractBinding binding, Object target)
         {
            registerDisposableAndUpdateTarget(binding);
            // now notify plugins..
            for (BindingCallback callback : field.getFormModel().getBindingCallbacks())
            {
               callback.onWidgetBinding(binding, field, target);
            }
         }
      });
   }

   public <T> MutableListBindingBuilder<T> bind(final ListFieldModel<T> field)
   {
      return new MutableListBindingBuilder<T>(field, new BindingBuilderCallback()
      {
         public void onBindingCreated(AbstractBinding binding, Object target)
         {
            registerDisposableAndUpdateTarget(binding);
            for (BindingCallback callback : field.getFormModel().getBindingCallbacks())
            {
               callback.onWidgetBinding(binding, field, target);
            }
         }
      });
   }

   public <T> FormattedFieldBindingBuilder<T> bind(final FormattedFieldModel<T> field)
   {
      return new FormattedFieldBindingBuilder<T>(field, new BindingBuilderCallback()
      {
         public void onBindingCreated(AbstractBinding binding, Object target)
         {
            registerDisposableAndUpdateTarget(binding);
            for (BindingCallback callback : field.getFormModel().getBindingCallbacks())
            {
               callback.onWidgetBinding(binding, field, target);
            }
         }
      });
   }

   public <T> FormattedListFieldBindingBuilder<T> bind(final FormattedListFieldModel<T> formattedList)
   {
      return new FormattedListFieldBindingBuilder<T>(formattedList, new BindingBuilderCallback()
      {
         public void onBindingCreated(AbstractBinding binding, Object target)
         {
            registerDisposableAndUpdateTarget(binding);
            for (BindingCallback callback : formattedList.getFormModel().getBindingCallbacks())
            {
               callback.onWidgetBinding(binding, formattedList, target);
            }
         }
      });
   }
}

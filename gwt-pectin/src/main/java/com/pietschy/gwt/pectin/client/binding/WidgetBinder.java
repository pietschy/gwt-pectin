/*
 * Copyright 2009 Andrew Pietsch 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you 
 * may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at 
 *      
 *      http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing permissions 
 * and limitations under the License. 
 */

package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.pietschy.gwt.pectin.client.*;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.command.AsyncUiCommand;
import com.pietschy.gwt.pectin.client.command.UiCommand;
import com.pietschy.gwt.pectin.client.metadata.HasEnabled;
import com.pietschy.gwt.pectin.client.metadata.HasVisible;
import com.pietschy.gwt.pectin.client.metadata.binding.ConditionBinderBuilder;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBinder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * WidgetBinder provides a builders to bind widgets to field models.  During the binding process
 * any {@link BindingCallback}s installed by plugins will be automatically invoked.
 */
public class WidgetBinder extends AbstractBinder
{
   // moving metadata enable/show methods to here, will eventually move the Metadata binder
   // class from the metadata package to here..
   private MetadataBinder metadataBinder = new MetadataBinder();

   public WidgetBinder()
   {
      registerDisposable(metadataBinder);
   }

   // Field Bindings
   public <T> ValueBindingBuilder<T> bind(FieldModel<T> field)
   {
      return new ValueBindingBuilder<T>(this, field);
   }

   public <T> ListBindingBuilder<T> bind(ListFieldModel<T> field)
   {
      return new ListBindingBuilder<T>(this, field);
   }

   public <T> FormattedFieldBindingBuilder<T> bind(FormattedFieldModel<T> field)
   {
      return new FormattedFieldBindingBuilder<T>(this, field);
   }

   public <T> FormattedListFieldBindingBuilder<T> bind(FormattedListFieldModel<T> formattedList)
   {
      return new FormattedListFieldBindingBuilder<T>(this, formattedList);
   }


   // Metadata style binding (visible and enabled).
   public ConditionBinderBuilder<?> show(HasVisible uiObject)
   {
      return metadataBinder.show(uiObject);
   }

   public ConditionBinderBuilder<?> hide(HasVisible uiObject)
   {
      return metadataBinder.hide(uiObject);
   }

   public ConditionBinderBuilder<?> show(UIObject uiObject)
   {
      return metadataBinder.show(uiObject);
   }

   public ConditionBinderBuilder<?> hide(UIObject uiObject)
   {
      return metadataBinder.hide(uiObject);
   }

   public ConditionBinderBuilder<?> show(Element element)
   {
      return metadataBinder.show(element);
   }

   public ConditionBinderBuilder<?> hide(Element element)
   {
      return metadataBinder.hide(element);
   }

   public ConditionBinderBuilder<?> enable(FocusWidget widget)
   {
      return metadataBinder.enable(widget);
   }

   public ConditionBinderBuilder<?> enable(HasEnabled widget)
   {
      return metadataBinder.enable(widget);
   }

   public ConditionBinderBuilder<?> disable(HasEnabled widget)
   {
      return metadataBinder.disable(widget);
   }

   public ConditionBinderBuilder<?> disable(FocusWidget widget)
   {
      return metadataBinder.disable(widget);
   }


   // Transition Bindings
   public <T> TransitionBindingBuilder<T> onTransitionOf(ValueModel<T> model)
   {
      return new TransitionBindingBuilder<T>(this, model);
   }


   // UiCommand Bindings

   public UiCommandBindingBuilder bind(UiCommand uiCommand)
   {
      return new UiCommandBindingBuilder(this, uiCommand);
   }

   public <R> UiCommandChanelBindingBuilder<R> displayResultOf(AsyncUiCommand<R, ?> command)
   {
      return new UiCommandChanelBindingBuilder<R>(this, command.getResults());
   }

   public <E> UiCommandChanelBindingBuilder<E> displayErrorOf(AsyncUiCommand<?, E> command)
   {
      return new UiCommandChanelBindingBuilder<E>(this, command.getErrors());
   }

   public <T> ChanelBindingBuilder<T> bind(Channel<T> channel)
   {
      return new ChanelBindingBuilder<T>(this, channel);
   }

   public UiCommandEventsBindingBuilder bindEventsOf(UiCommand command)
   {
      return new UiCommandEventsBindingBuilder(this, command);
   }

   public <R,E> UiCommandAsyncEventsBindingBuilder bindEventsOf(AsyncUiCommand<R, E> command)
   {
      return new UiCommandAsyncEventsBindingBuilder<R,E>(this, command);
   }


   // Callback methods for notifying plugins.
   protected <T> void registerBinding(AbstractBinding binding, FieldModel<T> fieldModel, Object target)
   {
      super.registerBindingAndUpdateTarget(binding);

      for (BindingCallback callback : fieldModel.getFormModel().getBindingCallbacks())
      {
         callback.onWidgetBinding(binding, fieldModel, target);
      }
   }

   protected <T> void registerBinding(AbstractBinding binding, FormattedFieldModel<T> fieldModel, Object target)
   {
      super.registerBindingAndUpdateTarget(binding);

      for (BindingCallback callback : fieldModel.getFormModel().getBindingCallbacks())
      {
         callback.onWidgetBinding(binding, fieldModel, target);
      }
   }

   protected <T> void registerBinding(AbstractBinding binding, ListFieldModel<T> fieldModel, Object target)
   {
      super.registerBindingAndUpdateTarget(binding);

      for (BindingCallback callback : fieldModel.getFormModel().getBindingCallbacks())
      {
         callback.onWidgetBinding(binding, fieldModel, target);
      }
   }

   protected <T> void registerBinding(AbstractBinding binding, FormattedListFieldModel<T> fieldModel, Object target)
   {
      super.registerBindingAndUpdateTarget(binding);

      for (BindingCallback callback : fieldModel.getFormModel().getBindingCallbacks())
      {
         callback.onWidgetBinding(binding, fieldModel, target);
      }
   }

}

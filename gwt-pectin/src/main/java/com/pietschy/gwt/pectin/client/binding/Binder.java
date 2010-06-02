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
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.command.AsyncUiCommand;
import com.pietschy.gwt.pectin.client.command.UiCommand;
import com.pietschy.gwt.pectin.client.list.ListModel;
import com.pietschy.gwt.pectin.client.list.MutableListModel;
import com.pietschy.gwt.pectin.client.metadata.HasEnabled;
import com.pietschy.gwt.pectin.client.metadata.HasVisible;
import com.pietschy.gwt.pectin.client.metadata.binding.ConditionBinderBuilder;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBinder;
import com.pietschy.gwt.pectin.client.value.MutableValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Base class for binders that provides the common registration and dispose methods.
 */
public class Binder extends AbstractBindingContainer
{
   // moving metadata enable/show methods to here, will eventually move all the Metadata binder
   // functionality from the MetadataBinder to here..
   protected MetadataBinder metadataBinder = new MetadataBinder();

   private BindingBuilderCallback bindingCallback = new BindingBuilderCallback()
   {
      public void onBindingCreated(AbstractBinding binding, Object target)
      {
         registerDisposableAndUpdateTarget(binding);
      }
   };

   public Binder()
   {
      registerDisposable(metadataBinder);
   }


   // Value Model Bindings
   public <T> ValueBindingBuilder<T> bind(ValueModel<T> field)
   {
      return new ValueBindingBuilder<T>(field, bindingCallback);
   }

   public <T> MutableValueBindingBuilder<T> bind(MutableValueModel<T> field)
   {
      return new MutableValueBindingBuilder<T>(field, bindingCallback);
   }

   public <T> ListBindingBuilder<T> bind(ListModel<T> field)
   {
      return new ListBindingBuilder<T>(field, bindingCallback);
   }

   public <T> MutableListBindingBuilder<T> bind(final MutableListModel<T> field)
   {
      return new MutableListBindingBuilder<T>(field, bindingCallback);
   }

   public <T> TransitionBindingBuilder<T> onTransitionOf(ValueModel<T> model)
   {
      return new TransitionBindingBuilder<T>(this, model);
   }

   // Metadata Bindings
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


   // Command and Channel bindings
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
}

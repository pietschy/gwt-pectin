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

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Base class for binders that provides the common registration and dispose methods.
 */
public class AbstractBinder implements BindingContainer
{
   private GarbageCollector gc = new GarbageCollector();

   /**
    * Registers a binding with this binder.  The binding will be disposed when this binder
    * is disposed.
    * 
    * @param binding the binding to register.
    */
   public void registerBindingAndUpdateTarget(AbstractBinding binding)
   {
      binding.updateTarget();
      gc.add(binding);
   }

   /**
    * Registers a HandlerRegistration with this container.  The handler will be unregistered
    * when this binder is disposed.
    *
    * @param handlerRegistration the handler registration to register.
    */
   public void registerHandler(HandlerRegistration handlerRegistration)
   {
      gc.add(handlerRegistration);
   }

   public void registerDisposable(Disposable disposable)
   {
      gc.add(disposable);
   }

   /**
    * Disposes all bindings created by the binder.  After this methods has finished 
    * listeners created by the bindings will be removed from all widgets and models.
    */
   public void dispose()
   {
      gc.dispose();
   }
}

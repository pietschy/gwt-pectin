package com.pietschy.gwt.pectin.client.bean;

import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * BeanModelProvider acts as a factory for {@link ValueModel} and {@link ListModel} instances
 * backed by the properties of Java beans.
 */
public abstract class BeanModelProvider<B> extends AbstractBeanModelProvider<B>
{

   protected BeanModelProvider()
   {
      // This class exists as a hook for the rebind process.
      //
      // The generator will subclass and implement the getPropertyDescriptor(...)
      // method.
      //
      // Other implementations should subclass AbstractBeanModelProvider.
   }
}
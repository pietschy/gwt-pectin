package com.pietschy.gwt.pectin.client.bean.runner;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.bean.*;
import com.pietschy.gwt.pectin.client.bean.data.AnotherBean;
import com.pietschy.gwt.pectin.client.bean.data.TestBean;

import java.util.ArrayList;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanModelProviderValueModelTestRunner extends AbstractRunner<TestBean>
{
   public BeanModelProviderValueModelTestRunner(AbstractBeanModelProvider<TestBean> provider, ResultCallback callback)
   {
      super(provider, callback);
   }

   public void a_getNestedValueModel()
   {
      // in this test we get a nested property and then gradually set the
      // values that are it's parents.  Only when the the whole path is initialised
      // will the value actually update.
      final ArrayList<String> events = new ArrayList<String>();

      // this model comes from a nested value.  This will actually create the model for
      // the parent bean behind the scenes.
      BeanPropertyValueModel<String> vm = provider.getValueModel("nestedBean.string", String.class);
      vm.addValueChangeHandler(new ValueChangeHandler<String>()
      {
         public void onValueChange(ValueChangeEvent<String> event)
         {
            events.add(event.getValue());
         }
      });

      // everything is null
      callback.assertNull(vm.getValue());
      callback.assertFalse(vm.isMutable());
      callback.assertEquals(events.size(), 0);

      // nested bean is still null
      provider.setValue(new TestBean());

      callback.assertNull(vm.getValue());
      callback.assertFalse(vm.isMutable());
      callback.assertEquals(events.size(), 0);

      BeanPropertyValueModel<AnotherBean> bean = provider.getValueModel("nestedBean", AnotherBean.class);
      AnotherBean nested = new AnotherBean();
      nested.setString("abc");
      bean.setValue(nested);

      callback.assertEquals(vm.getValue(), "abc");
      // there should be one value change and it should be the value of the bean.
      callback.assertEquals(events.size(), 1);
      callback.assertEquals(events.get(0), "abc");
   }



   public void b_getValueModelForUnknownProperty()
   {
      try
      {
         provider.getValueModel("blah", String.class);
         callback.fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   public void c_getValueModelWithWrongType()
   {
      try
      {
         provider.getValueModel("object", String.class);
         callback.fail("expected to throw IncorrectPropertyTypeException");
      }
      catch (IncorrectPropertyTypeException e)
      {
      }
   }

}
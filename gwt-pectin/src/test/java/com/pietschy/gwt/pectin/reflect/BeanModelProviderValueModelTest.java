package com.pietschy.gwt.pectin.reflect;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.BeanPropertyValueModel;
import com.pietschy.gwt.pectin.client.bean.IncorrectPropertyTypeException;
import com.pietschy.gwt.pectin.client.bean.UnknownPropertyException;
import com.pietschy.gwt.pectin.reflect.test.AnotherBean;
import com.pietschy.gwt.pectin.reflect.test.TestBean;
import org.junit.Test;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;

import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.*;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanModelProviderValueModelTest
{
   private BeanModelProvider<TestBean> provider;
   private TestBean bean;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      provider = new ReflectionBeanModelProvider<TestBean>(TestBean.class);
      bean = new TestBean();
   }

   @Test
   public void testGetNestedValueModel()
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
      assertNull("a1", vm.getValue());
      assertFalse("a2", vm.isMutable());
      assertEquals("a3", events.size(), 0);

      // nested bean is still null
      provider.setValue(new TestBean());

      assertNull("b1", vm.getValue());
      assertFalse("b2", vm.isMutable());
      assertEquals("b3", events.size(), 0);

      BeanPropertyValueModel<AnotherBean> bean = provider.getValueModel("nestedBean", AnotherBean.class);
      AnotherBean nested = new AnotherBean();
      nested.setString("abc");
      bean.setValue(nested);

      assertEquals("c1", vm.getValue(), "abc");
      // there should be one value change and it should be the value of the bean.
      assertEquals("c2", events.size(), 1);
      assertEquals("c3", events.get(0), "abc");
   }



   @Test
   public void testGetValueModelForUnknownProperty()
   {
      try
      {
         provider.getValueModel("blah", String.class);
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testGetValueModelWithWrongType()
   {
      try
      {
         provider.getValueModel("object", String.class);
         fail("expected to throw IncorrectPropertyTypeException");
      }
      catch (IncorrectPropertyTypeException e)
      {
      }
   }

}
package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.test.TestAutoCommitBeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.test.TestBean;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import org.junit.Test;

import java.util.Arrays;

import static com.pietschy.gwt.pectin.client.bean.test.AssertUtil.assertContentEquals;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class AutoCommitBeanModelProviderTest extends GWTTestCase
{
   private TestAutoCommitBeanModelProvider provider;
   private TestBean bean;

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestAutoCommitBeanModelProvider.class);
      bean = new TestBean();
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Test
   public void testMutationIsWrittenToBeanWithoutAffectingDirtyState()
   {
      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimitiveInt(5);
      bean.setList(Arrays.asList("abc", "def", "ghi"));
      provider.setBean(bean);
      ValueModel<Boolean> dirtyModel = provider.getDirtyModel();

      dirtyModel.addValueChangeHandler(new ValueChangeHandler<Boolean>()
      {
         public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent)
         {
            fail("dirty model should never fire events.");
         }
      });
      assertFalse(dirtyModel.getValue());

      // all models should be initialised event though they were created after the bean was loaded.
      BeanPropertyValueModel<TestBean, String> stringValue = provider.getValueModel("string", String.class);
      BeanPropertyValueModel<TestBean, Integer> primitiveInt = provider.getValueModel("primitiveInt", Integer.class);
      BeanPropertyListModel<TestBean, String> stringList = provider.getListModel("list", String.class);

      assertEquals(stringValue.getValue(), "abc");
      assertEquals(primitiveInt.getValue(), new Integer(5));
      assertContentEquals(stringList.asUnmodifiableList(), "abc", "def", "ghi");

      stringValue.setValue("def");
      assertEquals(stringValue.getValue(), "def");
      assertFalse(dirtyModel.getValue());

      primitiveInt.setValue(10);
      assertEquals(primitiveInt.getValue(), new Integer(10));
      assertFalse(dirtyModel.getValue());

      stringList.setElements(Arrays.asList("uvw", "xyz"));
      assertContentEquals(stringList.asUnmodifiableList(), "uvw", "xyz");
      assertFalse(dirtyModel.getValue());

   }

   @Test
   public void testModelsInitialisedWhenCreatedAfterBeanConfigured()
   {
      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimitiveInt(5);
      bean.setList(Arrays.asList("abc", "def", "ghi"));
      provider.setBean(bean);

      ValueModel<Boolean> providerDirty = provider.getDirtyModel();
      assertFalse("assert 0a failed", providerDirty.getValue());

      // all models should be initialised event though they were created after the bean was loaded.
      assertEquals(provider.getValueModel("string", String.class).getValue(), "abc");
      assertEquals(provider.getValueModel("primitiveInt", Integer.class).getValue(), new Integer(5));
      assertContentEquals(provider.getListModel("list", String.class).asUnmodifiableList(), "abc", "def", "ghi");
   }
}
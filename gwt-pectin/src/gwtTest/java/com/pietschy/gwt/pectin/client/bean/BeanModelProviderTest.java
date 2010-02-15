package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.test.TestBean;
import com.pietschy.gwt.pectin.client.bean.test.TestBeanModelProvider;
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
public class BeanModelProviderTest extends GWTTestCase
{
   private TestBeanModelProvider provider;
   private TestBean bean;

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestBeanModelProvider.class);
      bean = new TestBean();
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Test
   public void testCommitRevertAndDirty()
   {
      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimitiveInt(5);
      bean.setList(Arrays.asList("abc", "def", "ghi"));
      provider.setBean(bean);

      BeanPropertyValueModel<TestBean, String> stringValue = provider.getValueModel("string", String.class);
      BeanPropertyValueModel<TestBean, Integer> intValue = provider.getValueModel("primitiveInt", Integer.class);
      BeanPropertyListModel<TestBean, String> stringList = provider.getListModel("list", String.class);

      assertEquals(stringValue.getValue(), "abc");
      assertEquals(intValue.getValue(), new Integer(5));
      assertContentEquals(stringList.asUnmodifiableList(), "abc", "def", "ghi");

      stringValue.setValue("blah");
      assertTrue(stringValue.getDirtyModel().getValue());

      intValue.setValue(42);
      assertTrue(stringValue.getDirtyModel().getValue());

      stringList.add("jkl");
      assertTrue(stringValue.getDirtyModel().getValue());

      provider.commit();
      assertEquals(bean.getString(), "blah");
      assertFalse(stringValue.getDirtyModel().getValue());

      assertEquals(bean.getPrimitiveInt(), 42);
      assertFalse(stringValue.getDirtyModel().getValue());

      assertContentEquals(stringList.asUnmodifiableList(), "abc", "def", "ghi", "jkl");
      assertFalse(stringValue.getDirtyModel().getValue());


      stringValue.setValue("xyz");
      intValue.setValue(100);
      stringList.setElements(Arrays.asList("abc"));

      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(stringValue.getDirtyModel().getValue());

      provider.revert();
      
      assertEquals(bean.getString(), "blah");
      assertFalse(stringValue.getDirtyModel().getValue());

      assertEquals(bean.getPrimitiveInt(), 42);
      assertFalse(stringValue.getDirtyModel().getValue());

      assertContentEquals(stringList.asUnmodifiableList(), "abc", "def", "ghi", "jkl");
      assertFalse(stringValue.getDirtyModel().getValue());


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

   @Test
   public void testProviderDirtyModel()
   {
      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimitiveInt(5);
      provider.setBean(bean);

      ValueModel<Boolean> providerDirty = provider.getDirtyModel();

      assertFalse("assert 0a failed", providerDirty.getValue());

      BeanPropertyValueModel<TestBean, String> vm1 = provider.getValueModel("string", String.class);
      BeanPropertyValueModel<TestBean, Integer> vm2 = provider.getValueModel("primitiveInt", Integer.class);

      assertEquals("assert 0b failed", "abc", vm1.getValue());
      assertEquals("assert 0c failed", new Integer(5), vm2.getValue());

      vm1.setValue("def");
      assertTrue("assert 1 failed", providerDirty.getValue());

      vm2.setValue(42);
      assertTrue("assert 2 failed", providerDirty.getValue());

      vm1.setValue("abc");
      assertTrue("assert 3 failed", providerDirty.getValue());

      vm2.setValue(5);
      assertFalse("assert 4a failed:", vm2.computeDirty());
      assertFalse("assert 4b failed:", providerDirty.getValue());

      vm1.setValue("def");
      assertTrue("assert 5 failed", providerDirty.getValue());
      vm2.setValue(42);
      assertTrue("assert 6 failed", providerDirty.getValue());

      provider.commit();
      assertFalse("assert 7 failed", providerDirty.getValue());
   }


}

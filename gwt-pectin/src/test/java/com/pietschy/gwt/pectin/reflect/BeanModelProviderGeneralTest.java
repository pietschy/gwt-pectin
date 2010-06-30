package com.pietschy.gwt.pectin.reflect;


import com.pietschy.gwt.pectin.client.bean.AbstractBeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.BeanPropertyListModel;
import com.pietschy.gwt.pectin.client.bean.BeanPropertyValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.pietschy.gwt.pectin.reflect.test.AnotherBean;
import com.pietschy.gwt.pectin.reflect.test.BeanWithCollections;
import com.pietschy.gwt.pectin.reflect.test.TestBean;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.pietschy.gwt.pectin.reflect.AssertUtil.assertContentEquals;
import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderGeneralTest
{
   private AbstractBeanModelProvider<TestBean> provider;
   private TestBean bean;


   @BeforeMethod
   protected void setUp() throws Exception
   {
      provider = new ReflectionBeanModelProvider<TestBean>(TestBean.class);
      bean = new TestBean();
   }

   @Test
   public void testModelsUpdateWhenBeanChanges()
   {
      // models are created before the bean comes along
      BeanPropertyValueModel<String> modelOne = provider.getValueModel("string", String.class);
      BeanPropertyListModel<String> modelTwo = provider.getListModel("nestedBean.stringList", String.class);
      // and should be null and empty
      assertNull(modelOne.getValue());
      assertEquals(modelTwo.size(), 0);

      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setNestedBean(new AnotherBean());
      bean.getNestedBean().setStringList(Arrays.asList("abc", "def"));

      provider.setValue(bean);
      assertEquals("abc", modelOne.getValue());
      assertContentEquals(modelTwo.asUnmodifiableList(), "abc", "def");

      TestBean bean2 = new TestBean();
      bean2.setString("def");
      bean2.setNestedBean(new AnotherBean());
      bean2.getNestedBean().setStringList(Arrays.asList("ghi", "jkl"));

      provider.setValue(bean2);
      assertEquals("def", modelOne.getValue());
      assertContentEquals(modelTwo.asUnmodifiableList(), "ghi", "jkl");
   }

   @Test
   public void testModelsInitialisedWhenCreatedAfterBeanConfigured()
   {
      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimitiveInt(5);
      BeanWithCollections collections = new BeanWithCollections();
      collections.setStringCollection(Arrays.asList("abc", "def"));
      bean.setCollections(collections);

      provider.setValue(bean);

      ValueModel<Boolean> providerDirty = provider.getDirtyModel();

      assertFalse("assert 0a failed", providerDirty.getValue());

      // all models should be initialised event though they were created after the bean was loaded.
      assertEquals("abc", provider.getValueModel("string", String.class).getValue());
      assertEquals(new Integer(5), provider.getValueModel("primitiveInt", Integer.class).getValue());
      assertContentEquals(provider.getListModel("collections.stringCollection", String.class).asUnmodifiableList(), "abc", "def");
   }

   @Test
   public void testModelCommit()
   {
      BeanPropertyValueModel<String> stringModel = provider.getValueModel("string", String.class);
      BeanPropertyValueModel<String> nestedString = provider.getValueModel("nestedBean.string", String.class);
      // this ensures the commit works when the provider contains read-only models
      BeanPropertyValueModel<Object> readOnlyValue = provider.getValueModel("readOnlyObject", Object.class);
      BeanPropertyListModel<String> stringList = provider.getListModel("collections.stringCollection", String.class);

      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimitiveInt(5);
      bean.setNestedBean(new AnotherBean());
      bean.setCollections(new BeanWithCollections());
      bean.getCollections().setStringCollection(Arrays.asList("abc", "def"));

      provider.setValue(bean);
      stringModel.setValue("def");
      nestedString.setValue("hij");
      stringList.setElements(Arrays.asList("hij", "klm"));

      // should write all but the readonly models
      provider.commit();

      assertEquals(bean.getString(), "def");
      assertEquals(bean.getNestedBean().getString(), "hij");
      assertContentEquals(stringList.asUnmodifiableList(), "hij", "klm");
   }

}
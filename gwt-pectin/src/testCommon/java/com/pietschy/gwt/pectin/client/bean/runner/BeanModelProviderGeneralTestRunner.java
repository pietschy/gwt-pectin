package com.pietschy.gwt.pectin.client.bean.runner;


import com.pietschy.gwt.pectin.client.bean.AbstractBeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.BeanPropertyListModel;
import com.pietschy.gwt.pectin.client.bean.BeanPropertyValueModel;
import com.pietschy.gwt.pectin.client.bean.ResultCallback;
import com.pietschy.gwt.pectin.client.bean.data.AnotherBean;
import com.pietschy.gwt.pectin.client.bean.data.BeanWithCollections;
import com.pietschy.gwt.pectin.client.bean.data.TestBean;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.Arrays;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderGeneralTestRunner extends AbstractRunner<TestBean>
{
   public BeanModelProviderGeneralTestRunner(AbstractBeanModelProvider<TestBean> provider, ResultCallback callback)
   {
      super(provider, callback);
   }

   public void a_modelsUpdateWhenBeanChanges()
   {
      // models are created before the bean comes along
      BeanPropertyValueModel<String> modelOne = provider.getValueModel("string", String.class);
      BeanPropertyListModel<String> modelTwo = provider.getListModel("nestedBean.stringList", String.class);
      // and should be null and empty
      callback.assertNull(modelOne.getValue());
      callback.assertEquals(modelTwo.size(), 0);

      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setNestedBean(new AnotherBean());
      bean.getNestedBean().setStringList(Arrays.asList("abc", "def"));

      provider.setValue(bean);
      callback.assertEquals("abc", modelOne.getValue());
      callback.assertContentEquals(modelTwo.asUnmodifiableList(), "abc", "def");

      TestBean bean2 = new TestBean();
      bean2.setString("def");
      bean2.setNestedBean(new AnotherBean());
      bean2.getNestedBean().setStringList(Arrays.asList("ghi", "jkl"));

      provider.setValue(bean2);
      callback.assertEquals("def", modelOne.getValue());
      callback.assertContentEquals(modelTwo.asUnmodifiableList(), "ghi", "jkl");
   }

   public void b_modelsInitialisedWhenCreatedAfterBeanConfigured()
   {
      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimitiveInt(5);
      BeanWithCollections collections = new BeanWithCollections();
      collections.setStringCollection(Arrays.asList("abc", "def"));
      bean.setCollections(collections);

      provider.setValue(bean);

      ValueModel<Boolean> providerDirty = provider.dirty();

      callback.assertFalse(providerDirty.getValue(), "assert 0a failed");

      // all models should be initialised event though they were created after the bean was loaded.
      callback.assertEquals("abc", provider.getValueModel("string", String.class).getValue());
      callback.assertEquals(new Integer(5), provider.getValueModel("primitiveInt", Integer.class).getValue());
      callback.assertContentEquals(provider.getListModel("collections.stringCollection", String.class).asUnmodifiableList(), "abc", "def");
   }

   public void c_modelCommit()
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

      callback.assertEquals(bean.getString(), "def");
      callback.assertEquals(bean.getNestedBean().getString(), "hij");
      callback.assertContentEquals(stringList.asUnmodifiableList(), "hij", "klm");
   }

}
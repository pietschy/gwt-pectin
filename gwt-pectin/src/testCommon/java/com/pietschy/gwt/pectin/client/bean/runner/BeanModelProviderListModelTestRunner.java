package com.pietschy.gwt.pectin.client.bean.runner;


import com.pietschy.gwt.pectin.client.bean.*;
import com.pietschy.gwt.pectin.client.bean.data.BeanWithCollections;
import com.pietschy.gwt.pectin.client.bean.data.TestBean;
import com.pietschy.gwt.pectin.client.list.ListModelChangedEvent;
import com.pietschy.gwt.pectin.client.list.ListModelChangedHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanModelProviderListModelTestRunner extends AbstractRunner<TestBean>
{

   public BeanModelProviderListModelTestRunner(AbstractBeanModelProvider<TestBean> testBeanAbstractBeanModelProvider, ResultCallback callback)
   {
      super(testBeanAbstractBeanModelProvider, callback);
   }

   public void a_getNestedListModel()
   {
      final ArrayList<List<String>> events = new ArrayList<List<String>>();

      // this model comes from a nested value.
      final BeanPropertyListModel<String> vm = provider.getListModel("collections.stringList", String.class);
      vm.addListModelChangedHandler(new ListModelChangedHandler<String>()
      {
         public void onListDataChanged(ListModelChangedEvent<String> event)
         {
            events.add(new ArrayList<String>(vm.asUnmodifiableList()));
         }
      });

      // everything is empty
      callback.assertEquals(vm.size(), 0);
      callback.assertFalse(vm.isMutable());
      callback.assertEquals(events.size(), 0);

      // nested bean is still empty
      provider.setValue(new TestBean());

      callback.assertEquals(vm.size(), 0);
      callback.assertFalse(vm.isMutable());
      callback.assertEquals(events.size(), 0);

      BeanPropertyValueModel<BeanWithCollections> bean = provider.getValueModel("collections", BeanWithCollections.class);
      BeanWithCollections nested = new BeanWithCollections();
      nested.setStringList(Arrays.asList("abc", "def"));
      bean.setValue(nested);

      callback.assertEquals(vm.size(), 2);
      // there should be one value change and it should be the value of the bean.
      callback.assertEquals(events.size(), 1);
      callback.assertEquals(events.get(0).get(0), "abc");
      callback.assertEquals(events.get(0).get(1), "def");
   }

   public void b_getListModelWithUnknownProperty()
   {
      try
      {
         provider.getListModel("blah", String.class);
         callback.fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   public void c_getListModelWithNonCollectionProperty()
   {
      try
      {
         provider.getListModel("object", String.class);
         callback.fail("expected to throw NotCollectionPropertyException");
      }
      catch (NotCollectionPropertyException e)
      {
      }
   }

   public void d_getListModelWithWrongElementType()
   {
      try
      {
         provider.getListModel("collections.stringList", Integer.class);
         callback.fail("expected to throw IncorrectElementTypeException");
      }
      catch (IncorrectElementTypeException e)
      {
      }
   }

   public void e_getListModelWithUnsupportedCollectionType()
   {
      try
      {
         provider.getListModel("collections.unsupportedCollection", String.class);
         callback.fail("expected to throw UnsupportedCollectionTypeException");
      }
      catch (UnsupportedCollectionTypeException e)
      {
      }
   }
}
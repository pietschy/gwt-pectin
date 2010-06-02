package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.test.BeanWithCollections;
import com.pietschy.gwt.pectin.client.bean.test.TestBean;
import com.pietschy.gwt.pectin.client.bean.test.TestProvider;
import com.pietschy.gwt.pectin.client.list.ListModelChangedEvent;
import com.pietschy.gwt.pectin.client.list.ListModelChangedHandler;
import org.junit.Test;

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
public class BeanModelProviderListModelTest extends GWTTestCase
{
   private TestProvider provider;
   private TestBean bean;

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestProvider.class);
      bean = new TestBean();
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Test
   public void testGetNestedListModel()
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
      assertEquals("a1", vm.size(), 0);
      assertFalse("a2", vm.isMutable());
      assertEquals("a3", events.size(), 0);

      // nested bean is still empty
      provider.setValue(new TestBean());

      assertEquals("b1", vm.size(), 0);
      assertFalse("b2", vm.isMutable());
      assertEquals("b3", events.size(), 0);

      BeanPropertyValueModel<BeanWithCollections> bean = provider.getValueModel("collections", BeanWithCollections.class);
      BeanWithCollections nested = new BeanWithCollections();
      nested.setStringList(Arrays.asList("abc", "def"));
      bean.setValue(nested);

      assertEquals("c1", vm.size(), 2);
      // there should be one value change and it should be the value of the bean.
      assertEquals("c2", events.size(), 1);
      assertEquals("c3", events.get(0).get(0), "abc");
      assertEquals("c4", events.get(0).get(1), "def");
   }

   @Test
   public void testGetListModelWithUnknownProperty()
   {
      try
      {
         provider.getListModel("blah", String.class);
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testGetListModelWithNonCollectionProperty()
   {
      try
      {
         provider.getListModel("object", String.class);
         fail("expected to throw NotCollectionPropertyException");
      }
      catch (NotCollectionPropertyException e)
      {
      }
   }

   @Test
   public void testGetListModelWithWrongElementType()
   {
      try
      {
         provider.getListModel("collections.stringList", Integer.class);
         fail("expected to throw IncorrectElementTypeException");
      }
      catch (IncorrectElementTypeException e)
      {
      }
   }
   @Test
   public void testGetListModelWithUnsupportedCollectionType()
   {
      try
      {
         provider.getListModel("collections.unsupportedCollection", String.class);
         fail("expected to throw UnsupportedCollectionTypeException");
      }
      catch (UnsupportedCollectionTypeException e)
      {
      }
   }
}
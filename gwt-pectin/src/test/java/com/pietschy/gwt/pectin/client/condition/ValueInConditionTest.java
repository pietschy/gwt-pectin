package com.pietschy.gwt.pectin.client.condition;

import com.pietschy.gwt.pectin.client.list.ArrayListModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Apr 14, 2010
 * Time: 4:45:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValueInConditionTest
{
   private ValueHolder<String> source;

   @BeforeTest
   public void setUp()
   {
      source = new ValueHolder<String>(null);
   }

   @Test
   public void inWorksOkIncludingWithNullSourceValue()
   {
      source.setValue(null);

      ValueInCondition<String> condition = new ValueInCondition<String>(source, "a", "b");

      assertFalse(condition.getValue());

      source.setValue("a");
      assertTrue(condition.getValue());

      source.setValue("b");
      assertTrue(condition.getValue());

      source.setValue("c");
      assertFalse(condition.getValue());
   }

   @Test
   public void nullsOkInList()
   {
      source.setValue("something not included");
      ValueInCondition<String> condition = new ValueInCondition<String>(source, null, "a", "b");

      assertFalse(condition.getValue());

      source.setValue(null);
      assertTrue(condition.getValue());

      source.setValue("a");
      assertTrue(condition.getValue());

      source.setValue("b");
      assertTrue(condition.getValue());

      source.setValue("c");
      assertFalse(condition.getValue());
   }

   @Test
   public void updatesOnListModeChangeEvents()
   {
      source.setValue("abc");
      ArrayListModel<String> list = new ArrayListModel<String>();

      ValueInCondition<String> condition = new ValueInCondition<String>(source, list);

      assertFalse(condition.getValue());

      list.add("def");
      assertFalse(condition.getValue());

      list.add("abc");
      assertTrue(condition.getValue());

      list.remove("def");
      assertTrue(condition.getValue());

      list.remove("abc");
      assertFalse(condition.getValue());
   }


   @Test
   public void conditionFactoryMethodsWork()
   {
      Condition inList = Conditions.valueOf(source).isIn("a", "b", "c");
      Condition notInList = Conditions.valueOf(source).isNotIn("a", "b", "c");
      doInABCNotInABCTest(inList, notInList);

      List<String> iterable = Arrays.asList("a", "b", "c");
      inList = Conditions.valueOf(source).isIn(iterable);
      notInList = Conditions.valueOf(source).isNotIn(iterable);
      doInABCNotInABCTest(inList, notInList);

      ArrayListModel<String> listModel = new ArrayListModel<String>("a", "b", "c");
      inList = Conditions.valueOf(source).isIn(listModel);
      notInList = Conditions.valueOf(source).isNotIn(listModel);
      doInABCNotInABCTest(inList, notInList);

      // we'll also make sure that the building is correctly handling the list model type.
      source.setValue("a");
      assertTrue(inList.getValue());
      assertFalse(notInList.getValue());
      listModel.setElements(new String[0]);
      assertFalse(inList.getValue());
      assertTrue(notInList.getValue());
   }

   private void doInABCNotInABCTest(Condition inList, Condition notInList)
   {
      source.setValue("blah");
      assertFalse(inList.getValue());
      assertTrue(notInList.getValue());

      source.setValue("a");
      assertTrue(inList.getValue());
      assertFalse(notInList.getValue());
   }
}

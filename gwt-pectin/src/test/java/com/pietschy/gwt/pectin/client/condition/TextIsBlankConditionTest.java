package com.pietschy.gwt.pectin.client.condition;

import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 12, 2010
 * Time: 10:52:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class TextIsBlankConditionTest
{
   private ValueHolder<String> source;

   @Test(dataProvider = "computeData")
   public void computeTest(String value, boolean result)
   {
      source.setValue(value);
      Condition condition = Conditions.textOf(source).isBlank();
      Condition notCondition = Conditions.textOf(source).isNotBlank();

      assertEquals(condition.getValue().booleanValue(), result);
      assertEquals(notCondition.getValue().booleanValue(), !result);
   }

   @DataProvider
   public Object[][] computeData()
   {
      return new Object[][]
         {
            new Object[] {null, true},
            new Object[] {"", true},
            new Object[] {" ", true},
            new Object[] {"       ", true},
            new Object[] {"a", false},
            new Object[] {" a ", false},
         };
   }

   @BeforeMethod
   protected void setUp() throws Exception
   {
      source = new ValueHolder<String>();
   }
}
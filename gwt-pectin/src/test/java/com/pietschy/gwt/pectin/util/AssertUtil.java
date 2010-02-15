package com.pietschy.gwt.pectin.util;

import java.util.Arrays;
import java.util.Collection;

import static org.testng.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 11, 2010
 * Time: 11:15:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class AssertUtil
{
   public static void assertContentEquals(Collection<?> list, Object... values)
   {
      assertContentEquals(list, Arrays.asList(values));
   }

   public static void assertContentEquals(Collection<?> list, Collection<?> values)
   {
      if (list.size() != values.size())
      {
         fail("collections are different sizes: list=" + list.toString() + ", values= " + values.toString());
      }

      if (!list.containsAll(values))
      {
         fail("contents are different, list=" + list.toString() + ", values= " + values.toString());
      }
   }
}

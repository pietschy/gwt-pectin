package com.pietschy.gwt.pectin.client.bean.test;

import junit.framework.Assert;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 11, 2010
 * Time: 12:01:07 PM
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
         Assert.fail("collections are different sizes: list=" + list.toString() + ", values= " + values.toString());
      }

      if (!list.containsAll(values))
      {
         Assert.fail("contents are different, list=" + list.toString() + ", values= " + values.toString());
      }
   }
}

package com.pietschy.gwt.pectin.client.bean;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 29, 2010
 * Time: 11:03:28 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractResultCallback implements ResultCallback
{
   public <T> void assertContentEquals(Collection<T> actual, T... expected)
   {
      assertContentEquals(actual, Arrays.asList(expected));
   }

   public <T> void assertContentEquals(Collection<T> actual, Collection<T> expected)
   {
      if (actual.size() != expected.size())
      {
         fail("collections are different sizes: list=" + actual.toString() + ", values= " + expected.toString());
      }

      if (!actual.containsAll(expected))
      {
         fail("contents are different, list=" + actual.toString() + ", values= " + expected.toString());
      }
   }
}

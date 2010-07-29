package com.pietschy.gwt.pectin.client.bean;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 29, 2010
 * Time: 10:39:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ResultCallback
{
   void fail(String message);
   void assertNull(Object value);
   void assertEquals(int i, int i1);
   void assertEquals(String actual, String expected);

   void assertFalse(boolean value);
   void assertFalse(boolean value, String message);

   <T> void assertContentEquals(Collection<T> actual, T... expected);

   <T> void assertContentEquals(Collection<T> actual, Collection<T> expected);

   void assertEquals(boolean actual, boolean expected);

   void assertEquals(Object actual, Object expected);
}

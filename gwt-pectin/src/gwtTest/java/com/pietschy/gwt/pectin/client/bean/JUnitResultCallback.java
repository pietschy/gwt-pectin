package com.pietschy.gwt.pectin.client.bean;

import junit.framework.TestCase;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 29, 2010
* Time: 10:58:08 AM
* To change this template use File | Settings | File Templates.
*/
class JUnitResultCallback extends AbstractResultCallback
{
   public void fail(String message)
   {
      TestCase.fail(message);
   }

   public void assertNull(Object value)
   {
      TestCase.assertNull(value);
   }

   public void assertEquals(int actual, int expected)
   {
      TestCase.assertEquals(expected, actual);
   }

   public void assertEquals(String actual, String expected)
   {
      TestCase.assertEquals(expected, actual);
   }

   public void assertFalse(boolean actual)
   {
      TestCase.assertFalse(actual);
   }

   public void assertFalse(boolean actual, String message)
   {
      TestCase.assertFalse(message, actual);
   }

   public void assertEquals(boolean actual, boolean expected)
   {
      TestCase.assertEquals(expected, actual);
   }

   public void assertEquals(Object actual, Object expected)
   {
      TestCase.assertEquals(expected, actual);
   }

}

package com.pietschy.gwt.pectin.reflect;

import com.pietschy.gwt.pectin.client.bean.AbstractResultCallback;
import org.testng.Assert;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 29, 2010
* Time: 12:34:38 PM
* To change this template use File | Settings | File Templates.
*/
class TestNgResultCallback extends AbstractResultCallback
{
   public void fail(String message)
   {
      Assert.fail(message);
   }

   public void assertNull(Object value)
   {
      Assert.assertNull(value);
   }

   public void assertEquals(int i, int i1)
   {
      Assert.assertEquals(i, i1);
   }

   public void assertEquals(String actual, String expected)
   {
      Assert.assertEquals(actual, expected);
   }

   public void assertFalse(boolean value)
   {
      Assert.assertFalse(value);
   }

   public void assertFalse(boolean value, String message)
   {
      Assert.assertFalse(value, message);
   }

   public void assertEquals(boolean actual, boolean expected)
   {
      Assert.assertEquals(actual, expected);
   }

   public void assertEquals(Object actual, Object expected)
   {
      Assert.assertEquals(actual, expected);
   }
}

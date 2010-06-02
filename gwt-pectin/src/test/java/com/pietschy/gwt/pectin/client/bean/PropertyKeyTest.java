package com.pietschy.gwt.pectin.client.bean;

import junit.framework.TestCase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 12, 2010
 * Time: 4:50:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyKeyTest extends TestCase
{
   @Test(dataProvider = "propertyNameTestData")
   public void propertyNameIsExtractedCorrectly(String path, String propertyName)
   {
      PropertyKey<Object> key = new PropertyKey<Object>(Object.class, path);

      Assert.assertEquals(key.getFullPath(), path);
      Assert.assertEquals(key.getPropertyName(), propertyName);
   }

   @DataProvider
   public Object[][] propertyNameTestData()
   {
      return new Object[][]
         {
            new Object[]{"abc","abc"},
            new Object[]{"abc.def","def"},
            new Object[]{"abc.def.hij","hij"},
         };
   }


   @Test(dataProvider = "invalidPropertyData", expectedExceptions = IllegalArgumentException.class)
   public void invalidPathsBarf(String path, String propertyName)
   {
      PropertyKey<Object> key = new PropertyKey<Object>(Object.class, path);
   }

   @DataProvider
   public Object[][] invalidPropertyData()
   {
      return new Object[][]
         {
            new Object[]{".abc"},
            new Object[]{"abc."},
            new Object[]{""},
            new Object[]{"."},
         };
   }

}

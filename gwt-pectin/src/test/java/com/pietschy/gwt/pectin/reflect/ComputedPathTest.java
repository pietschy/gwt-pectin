package com.pietschy.gwt.pectin.reflect;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 27, 2010
 * Time: 6:55:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComputedPathTest
{
   @Test(dataProvider = "pathData")
   public void pathsParsingWorks(String fullPath, String parentPath, String propertyName, boolean topLevel) throws Exception
   {
      ComputedPath path = new ComputedPath(fullPath);

      Assert.assertEquals(path.getFullPath(), fullPath);
      Assert.assertEquals(path.getParentPath(), parentPath);
      Assert.assertEquals(path.getPropertyName(), propertyName);
      Assert.assertEquals(path.isTopLevel(), topLevel);
   }


   @DataProvider
   public Object[][] pathData()
   {
      return new Object[][]{
         new Object[]{"simple", null, "simple", true},
         new Object[]{"nested.path", "nested", "path", false},
         new Object[]{"multi.nested.path", "multi.nested", "path", false},
      };
   }

   @Test(dataProvider = "invalidPathData", expectedExceptions = Exception.class)
   public void invalidPathDataBarfs(String fullPath)
   {
      ComputedPath path = new ComputedPath(fullPath);
   }


   @DataProvider
   public Object[][] invalidPathData()
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


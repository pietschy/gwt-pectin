package com.pietschy.gwt.pectin.client.bean;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 27, 2010
 * Time: 8:18:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractPropertyDescriptorTest
{
   @Test
   public void propertiesSetCorrectly() throws Exception
   {
      AbstractPropertyDescriptor path = createPath("a.b", "a", "b");

      Assert.assertEquals(path.getFullPath(), "a.b");
      Assert.assertEquals(path.getParentPath(), "a");
      Assert.assertEquals(path.getPropertyName(), "b");
   }

//   @Test(expectedExceptions = Exception.class)
//   public void emptyParentPathBarfs() throws Exception
//   {
//      createPath("a", "", "b");
//   }

   @Test
   public void nullParentPathTriggersTopLevel() throws Exception
   {
      Assert.assertTrue(createPath("a", null, "a").isTopLevel());
      Assert.assertFalse(createPath("a.b", "a", "b").isTopLevel());
   }

   private AbstractPropertyDescriptor createPath(String fullPath, String parentPath, String propertyName)
   {
      return new AbstractPropertyDescriptor(fullPath, parentPath, propertyName, Object.class, false)
      {
         public Class getValueType()
         {
            return null;
         }

         public Class getElementType() throws NotCollectionPropertyException
         {
            return null;
         }

         public boolean isCollection()
         {
            return false;
         }

         public Object readProperty(Object bean)
         {
            return null;
         }

         public void writeProperty(Object bean, Object value) throws ReadOnlyPropertyException, TargetBeanIsNullException
         {
         }
      };
   }
}

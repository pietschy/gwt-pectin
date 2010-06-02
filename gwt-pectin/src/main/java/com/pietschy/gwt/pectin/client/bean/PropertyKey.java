package com.pietschy.gwt.pectin.client.bean;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Mar 26, 2010
* Time: 11:32:38 AM
* To change this template use File | Settings | File Templates.
*/
public class PropertyKey<T>
{
   private Class<T> type;
   private String fullPath;
   private String propertyName;
   private String parentPath;


   public PropertyKey(Class<T> type, String propertyPath)
   {
      this.type = type;
      this.fullPath = propertyPath;
      parsePath(propertyPath);
   }

   public String getPropertyName()
   {
      return propertyName;
   }

   public String getFullPath()
   {
      return fullPath;
   }

   public boolean isRootProperty()
   {
      return parentPath == null;
   }

   public String getParentPath()
   {
      return parentPath;
   }

   void parsePath(String propertyPath)
   {
      if (propertyPath.startsWith("."))
      {
         throw new IllegalArgumentException("Property path can't start with a '.': " + propertyPath);
      }
      if (propertyPath.endsWith("."))
      {
         throw new IllegalArgumentException("Property path can't end with a '.': " + propertyPath);
      }

      int lastDotIndex = propertyPath.lastIndexOf('.');

      if (lastDotIndex > 0)
      {
         parentPath = propertyPath.substring(0, lastDotIndex);
         propertyName = propertyPath.substring(lastDotIndex + 1);
      }
      else
      {
         parentPath = null;
         propertyName = propertyPath;
      }
   }

   @SuppressWarnings("unchecked")
   public boolean equals(Object o)
   {
      if (this == o)
      {
         return true;
      }
      if (o == null || getClass() != o.getClass())
      {
         return false;
      }

      PropertyKey<T> key = (PropertyKey<T>) o;

      if (fullPath != null ? !fullPath.equals(key.fullPath) : key.fullPath != null)
      {
         return false;
      }

      if (type != null ? !type.equals(key.type) : key.type != null)
      {
         return false;
      }

      return true;
   }

   public int hashCode()
   {
      int result;
      result = (type != null ? type.hashCode() : 0);
      result = 31 * result + (fullPath != null ? fullPath.hashCode() : 0);
      return result;
   }
}
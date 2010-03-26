package com.pietschy.gwt.pectin.client.bean;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Mar 26, 2010
* Time: 11:32:38 AM
* To change this template use File | Settings | File Templates.
*/
class PropertyKey<T>
{
   private Class<T> type;
   private String propertyName;

   PropertyKey(Class<T> type, String propertyName)
   {
      this.type = type;
      this.propertyName = propertyName;
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

      if (propertyName != null ? !propertyName.equals(key.propertyName) : key.propertyName != null)
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
      result = 31 * result + (propertyName != null ? propertyName.hashCode() : 0);
      return result;
   }
}

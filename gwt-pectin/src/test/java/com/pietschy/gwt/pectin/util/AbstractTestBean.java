package com.pietschy.gwt.pectin.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 9, 2010
 * Time: 3:29:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractTestBean
{
   private Object object;
   private String string;
   private int primitiveInt;
   private boolean primitiveBoolean;
   private Set<String> set;
   private Collection<String> readOnlyCollection = Arrays.asList("abc", "def", "ghi");

   public Object getObject()
   {
      return object;
   }

   public void setObject(Object object)
   {
      this.object = object;
   }

   public String getString()
   {
      return string;
   }

   public void setString(String string)
   {
      this.string = string;
   }

   public int getPrimitiveInt()
   {
      return primitiveInt;
   }

   public void setPrimitiveInt(int primitiveInt)
   {
      this.primitiveInt = primitiveInt;
   }

   public boolean isPrimitiveBoolean()
   {
      return primitiveBoolean;
   }

   public void setPrimitiveBoolean(boolean primitiveBoolean)
   {
      this.primitiveBoolean = primitiveBoolean;
   }

   public Set<String> getSet()
   {
      return set;
   }

   public void setSet(Set<String> set)
   {
      this.set = set;
   }

   public Collection<String> getReadOnlyCollection()
   {
      return readOnlyCollection;
   }
}

package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 15, 2010
 * Time: 10:03:16 AM
 * To change this template use File | Settings | File Templates.
 */
class MethodInfo
{
   private JMethod method;

   MethodInfo(JMethod method)
   {
      this.method = method;
   }

   public boolean isPropertyAccessor()
   {
      return isGetter() || isSetter();
   }

   public boolean isGetter()
   {
      String name = method.getName();
      return method.isPublic() && !method.isStatic()
             && (name.startsWith("is") || name.startsWith("get"))
             && method.getParameters().length == 0
             && method.getReturnType() != JPrimitiveType.VOID;

   }

   public boolean isSetter()
   {
      return method.isPublic() && !method.isStatic()
             && method.getName().startsWith("set")
             && method.getParameters().length == 1
             && method.getReturnType() == JPrimitiveType.VOID;

   }

   public String getPropertyName()
   {
      String name = method.getName();

      if (isSetter())
      {
         if (name.startsWith("set"))
         {
            name = name.substring(3);
         }
         else
         {
            throw new IllegalStateException("method is not a property accessor:" + name);
         }
      }
      else if (isGetter())
      {
         if (name.startsWith("get"))
         {
            name = name.substring(3);
         }
         else if (name.startsWith("is"))
         {
            name = name.substring(2);
         }
         else
         {
            throw new IllegalStateException("method is not a property accessor:" + name);
         }
      }

      return name.substring(0, 1).toLowerCase() + name.substring(1);
   }

   public JType getReturnType()
   {
      return method.getReturnType();
   }

   public boolean hasSingleParameterOfType(JType type)
   {
      JParameter[] params = method.getParameters();
      return params.length == 1 && params[0].getType().equals(type);
   }

   public String getName()
   {
      return method.getName();
   }
}

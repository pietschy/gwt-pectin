package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.pietschy.gwt.pectin.client.bean.NestedBean;

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
   private boolean getter;
   private boolean setter;
   private String propertyName;

   MethodInfo(JMethod method)
   {
      this.method = method;

      getter = computeGetter(method);
      setter = computeSetter(method);
      propertyName = computePropertyName(method);
   }

   private boolean computeSetter(JMethod method)
   {
      return method.isPublic() && !method.isStatic()
             && method.getName().startsWith("set")
             && method.getParameters().length == 1
             && method.getReturnType() == JPrimitiveType.VOID;
   }

   private boolean computeGetter(JMethod method)
   {
      String name = method.getName();
      return method.isPublic() && !method.isStatic()
          && (name.startsWith("is") || name.startsWith("get"))
          && method.getParameters().length == 0
          && method.getReturnType() != JPrimitiveType.VOID;
   }

   private String computePropertyName(JMethod method)
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

   public boolean isGetter()
   {
      return getter;
   }

   public boolean isSetter()
   {
      return setter;

   }

   public boolean isAnnotatedAsNestedBean()
   {
      return isGetter() && method.getAnnotation(NestedBean.class) != null;
   }

   public String getPropertyName()
   {
      return propertyName;
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

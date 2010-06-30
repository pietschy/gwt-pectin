package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.typeinfo.*;
import com.pietschy.gwt.pectin.client.bean.NestedBean;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 15, 2010
 * Time: 9:58:51 AM
 * To change this template use File | Settings | File Templates.
 */
class PropertyInfo
{
   private TypeOracle typeOracle;
   private BeanInfo parentType;
   private String parentPath;
   private String name;
   private JType type;
   private String getterMethodName;
   private String setterMethodName = null;
   private boolean nestedBean;

   PropertyInfo(TypeOracle typeOracle, BeanInfo parentType, String parentPath, String name, JType type, String getterMethodName, boolean hasNestedAnnotation)
   {
      this.typeOracle = typeOracle;
      this.parentType = parentType;
      this.parentPath = parentPath;
      this.name = name;
      this.type = type;
      this.getterMethodName = getterMethodName;
      this.nestedBean = computeNestedBean(hasNestedAnnotation, parentType.getNestedBeanTypes());
   }

   private boolean computeNestedBean(boolean hasNestedAnnotation, Set<Class> nestedBeanTypes)
   {
      if (hasNestedAnnotation)
      {
         return true;
      }
      else
      {
         for (Class nestedBeanType : nestedBeanTypes)
         {
            if (getTypeName().equals(nestedBeanType.getName()))
            {
               return true;
            }
         }
         return false;
      }
   }

   public String getName()
   {
      return name;
   }

   public JType getType()
   {
      return type;
   }

   public String getFullPropertyPath()
   {
      return isTopLevel() ? getName() : getParentPath() + "." + getName();
   }

   public boolean isTopLevel()
   {
      return getParentPath() == null;
   }

   public BeanInfo getParentBeanInfo()
   {
      return parentType;
   }

   public String getParentPath()
   {
      return parentPath;
   }

   public boolean isNestedBean()
   {
      return nestedBean;
   }

   public BeanInfo getNestedBeanInfo()
   {
      JClassType beanType = getAsClassType();

      if (beanType == null)
      {
         throw new IllegalStateException("property " + getName() +
                                         " on type " + parentType.getTypeName() +
                                         " is marked as @" + NestedBean.class.getSimpleName() +
                                         " but doesn't return a class or interface types.");
      }

      if (isCollectionProperty())
      {
         throw new IllegalStateException("property " + getName() +
                                         " on type " + parentType.getTypeName() +
                                         " is marked as @" + NestedBean.class.getSimpleName() +
                                         " but returns an instanceOf Collection");
      }

      return new BeanInfo(typeOracle, beanType, parentType.getNestedBeanTypes(), getFullPropertyPath());
   }

   public boolean isCollectionProperty()
   {
      JClassType classType = getAsClassType();

      return classType != null && classType.isAssignableTo(typeOracle.findType("java.util.Collection"));
   }

   public String getCollectionElementTypeName()
   {
      JClassType type = getCollectionElementType();
      return type != null ? type.getQualifiedSourceName() : "java.lang.Object";
   }

   public JClassType getCollectionElementType()
   {
      if (!isCollectionProperty())
      {
         throw new IllegalStateException("Property isn't a collection type.");
      }

      JParameterizedType parameterisedType = getAsParameterisedType();
      return parameterisedType != null ? parameterisedType.getTypeArgs()[0] : null;
   }

   private JClassType getAsClassType()
   {
      return type.isClassOrInterface();
   }

   private JParameterizedType getAsParameterisedType()
   {
      return type.isParameterized();
   }

   public String getTypeName()
   {
      final JPrimitiveType primitive = type.isPrimitive();
      return primitive != null ? primitive.getQualifiedBoxedSourceName() : type.getQualifiedSourceName();
   }

   public boolean isMutable()
   {
      return setterMethodName != null;
   }

   public String getGetterMethodName()
   {
      return getterMethodName;
   }

   public String getSetterMethodName()
   {
      return setterMethodName;
   }

   public void setSetterMethodName(String setterMethodName)
   {
      this.setterMethodName = setterMethodName;
   }
}

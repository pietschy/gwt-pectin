package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.*;
import com.pietschy.gwt.pectin.client.bean.NestedBean;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 15, 2010
 * Time: 9:58:51 AM
 * To change this template use File | Settings | File Templates.
 */
class PropertyInfo
{
   private BeanInfo parentType;
   private BeanInfo myNestedBeanType;
   private String parentPath;
   private String name;
   private JType type;
   private String getterMethodName;
   private String setterMethodName = null;
   private boolean annotatedWithNestedBean;


   PropertyInfo(BeanInfo parentType, String parentPath, String name, JType type, String getterMethodName, boolean hasNestedAnnotation)
   {
      this.parentType = parentType;
      this.parentPath = parentPath;
      this.name = name;
      this.type = type;
      this.getterMethodName = getterMethodName;
      this.annotatedWithNestedBean = hasNestedAnnotation;
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
      return parentType.isRootBean();
   }

   public int getPropertyDepth()
   {
      return parentType.getPropertyDepth() + 1;
   }

   public BeanInfo getParentBeanInfo()
   {
      return parentType;
   }

   public String getParentPath()
   {
      return parentPath;
   }

   public boolean isAnnotatedWithNestedBean()
   {
      return annotatedWithNestedBean;
   }

   public BeanInfo getNestedBeanInfo()
   {
      return myNestedBeanType;
   }

   public void createNestedBeanInfo() throws UnableToCompleteException
   {
      JClassType beanType = isClassOrInterface();

      if (beanType == null)
      {
         throw new IllegalStateException("property " + getName() +
                                         " on type " + parentType.getTypeName() +
                                         " is marked as @" + NestedBean.class.getSimpleName() +
                                         " but doesn't return a class or interface type.");
      }

      if (isCollectionProperty())
      {
         throw new IllegalStateException("property " + getName() +
                                         " on type " + parentType.getTypeName() +
                                         " is marked as @" + NestedBean.class.getSimpleName() +
                                         " but returns an instanceOf Collection");
      }

      myNestedBeanType = new BeanInfo(this);
   }

   public boolean hasNestedBeanInfo()
   {
      return getNestedBeanInfo() != null;
   }

   public boolean isCollectionProperty()
   {
      JClassType classType = isClassOrInterface();

      return classType != null && classType.isAssignableTo(getTypeOracle().findType("java.util.Collection"));
   }

   private TypeOracle getTypeOracle()
   {
      return parentType.getContext().getTypeOracle();
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

      JParameterizedType parameterisedType = isParameterised();
      return parameterisedType != null ? parameterisedType.getTypeArgs()[0] : null;
   }

   private JClassType isClassOrInterface()
   {
      return type.isClassOrInterface();
   }

   private JParameterizedType isParameterised()
   {
      return type.isParameterized();
   }

   public JClassType getAsClassType()
   {
      JClassType classType = isClassOrInterface();
      if (classType == null)
      {
         throw new IllegalStateException("Type isn't an interface or class:" + type);
      }
      return classType;
   }

   public String getTypeName()
   {
      final JPrimitiveType primitive = type.isPrimitive();
      return primitive != null ? primitive.getQualifiedBoxedSourceName() : type.getQualifiedSourceName();
   }

   public boolean isPrimitive()
   {
      return type.isPrimitive() != null;
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

   public PropertyInfo getParentProperty()
   {
      return getParentBeanInfo().asProperty();
   }

   public boolean isRecursive()
   {
      PropertyInfo parent = getParentProperty();
      while (parent != null)
      {
         // if my type name is the same as any parent then we're 
         // recursive.
         if (getTypeName().equals(parent.getTypeName()))
         {
            return true;
         }

         parent = parent.getParentProperty();
      }
      return false;
   }
}

package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.typeinfo.*;

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
   private String name;
   private JType type;
   private String getterMethodName;
   private String setterMethodName = null;

   PropertyInfo(TypeOracle typeOracle, String name, JType type, String getterMethodName)
   {
      this.typeOracle = typeOracle;
      this.name = name;
      this.type = type;
      this.getterMethodName = getterMethodName;
   }

   public String getName()
   {
      return name;
   }

   public JType getType()
   {
      return type;
   }

   public boolean isCollectionProperty()
   {
      JClassType classType = getAsClassType();

      return classType != null && classType.isAssignableTo(typeOracle.findType("java.util.Collection"));
   }

   public String getCollectionElementType()
   {
      if (!isCollectionProperty())
      {
         throw new IllegalStateException("Property isn't a collection type.");
      }

      JParameterizedType parameterisedType = getAsParameterisedType();
      return parameterisedType != null ? parameterisedType.getTypeArgs()[0].getQualifiedSourceName() : "java.lang.Object";
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

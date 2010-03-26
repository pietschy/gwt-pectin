/*
 * Copyright 2009 Andrew Pietsch 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you 
 * may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at 
 *      
 *      http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing permissions 
 * and limitations under the License. 
 */

package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.io.PrintWriter;

/**
 *
 */
public class BeanModelProviderCreator
{
   private TreeLogger logger;
   private GeneratorContext context;
   private TypeOracle typeOracle;

   private String typeName;

   public BeanModelProviderCreator(TreeLogger logger, GeneratorContext context, String typeName)
   {
      this.logger = logger;
      this.context = context;
      this.typeOracle = context.getTypeOracle();
      this.typeName = typeName;
   }

   public String createProvider()
   {
      try
      {
         JClassType classType = typeOracle.getType(typeName);

         String fullClassName = createClassNameWithPackage(classType);

         SourceWriter writer = getSourceWriter(classType);


         // The source writer is null if the class already exists, so we don't need
         // to generate it.
         if (writer == null)
         {
            return fullClassName;
         }
         else
         {

            // bean type is in our declaration e.g.
            // class MyProvider extends AbstractBeanModelProvider<BeanType>
            JClassType beanType = classType.getSuperclass().isParameterized().getTypeArgs()[0];

            BeanInfo beanInfo = new BeanInfo(typeOracle, beanType);

            writer.indent();
            
            writer.println();

            generateReadValueMethod(beanInfo, writer);

            writer.println();

            generateWriteValueMethod(beanInfo, writer);

            writer.println();

            generateGetPropertyTypeMethod(writer, beanInfo);

            writer.println();

            generateGetElementTypeMethod(writer, beanInfo);

            writer.println();

            generateIsMutableMethod(writer, beanInfo);

            writer.println();

            writer.commit(logger);

            return fullClassName;
         }
      }
      catch (NotFoundException e)
      {
         e.printStackTrace();
         return null;
      }
   }

   private void generateReadValueMethod(BeanInfo bean, SourceWriter source)
   {
      // create the getAttribute method
      source.println("public Object readProperty(" + bean.getTypeName() + " bean, String property) {");
      source.indent();

      for (PropertyInfo propertyInfo : bean)
      {
         source.println("if (property.equals(\"" + propertyInfo.getName() + "\")) {");
         source.indent();
         source.println("return bean == null ? null : bean." + propertyInfo.getGetterMethodName() + "();");
         source.outdent();
         source.print("} else ");
      }

      source.println("{");
      source.indent();
      source.println("throw new com.pietschy.gwt.pectin.client.bean.UnknownPropertyException(property);");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }

   private void generateWriteValueMethod(BeanInfo beanInfo, SourceWriter source)
   {
      // create the set attribute method
      source.println("public void writeProperty(" + beanInfo.getTypeName() + " bean, String property, Object value) {");
      source.indent();
      source.println("if (bean == null) {");
      source.println("   throw new com.pietschy.gwt.pectin.client.bean.TargetBeanIsNullException(" + beanInfo.getTypeName() + ".class);");
      source.println("}");
      source.println("");
      for (PropertyInfo property : beanInfo)
      {
         source.println("if (property.equals(\"" + property.getName() + "\")) { ");
         source.indent();
         if (property.isMutable())
         {
            source.println("bean." + property.getSetterMethodName() + "((" + property.getTypeName() + ") value);");
         }
         else
         {
            source.println("throw new com.pietschy.gwt.pectin.client.bean.ImmutablePropertyException(property);");
         }
         source.outdent();
         source.print("} else ");
      }
      source.println("{");
      source.indent();
      source.println("throw new com.pietschy.gwt.pectin.client.bean.UnknownPropertyException(property);");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }

   private void generateIsMutableMethod(SourceWriter source, BeanInfo beanInfo)
   {
      // create the set attribute method
      source.println("public boolean isMutable(String property) {");
      source.indent();
      for (PropertyInfo property : beanInfo)
      {
         source.println("if (property.equals(\"" + property.getName() + "\")) { ");
         source.indent();
         if (property.isMutable())
         {
            source.println("return true;");
         }
         else
         {
            source.println("return false;");
         }
         source.outdent();
         source.print("} else ");
      }
      source.println("{");
      source.indent();
      source.println("throw new com.pietschy.gwt.pectin.client.bean.UnknownPropertyException(property);");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }


   private void generateGetPropertyTypeMethod(SourceWriter source, BeanInfo beanInfo)
   {
      // create the getPropertyType method
      source.println("public Class getPropertyType(String property) {");
      source.indent();

      for (PropertyInfo propertyInfo : beanInfo)
      {
         source.println("if (property.equals(\"" + propertyInfo.getName() + "\")) { ");
         source.indent();
         source.println("return " + propertyInfo.getTypeName() + ".class;");

         source.outdent();
         source.print("} else ");
      }

      source.println("{");
      source.indent();
      source.println("throw new com.pietschy.gwt.pectin.client.bean.UnknownPropertyException(property);");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }

   private void generateGetElementTypeMethod(SourceWriter source, BeanInfo beanInfo)
   {
      // create the getPropertyType method
      source.println("public Class getElementType(String property) {");
      source.indent();

      for (PropertyInfo property : beanInfo)
      {
         source.println("if (property.equals(\"" + property.getName() + "\")) { ");
         source.indent();
         if (property.isCollectionProperty())
         {
            source.println("return " + property.getCollectionElementType() + ".class;");
         }
         else
         {
            source.println("throw new com.pietschy.gwt.pectin.client.bean.NotCollectionPropertyException(property, " + property.getTypeName() + ".class);");
         }
         source.outdent();
         source.print("} else ");
      }
      source.println("{");
      source.indent();
      source.println("throw new com.pietschy.gwt.pectin.client.bean.UnknownPropertyException(property);");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }


   public SourceWriter
   getSourceWriter(JClassType classType)
   {
      String packageName = createPackageName(classType);
      String simpleName = createClassName(classType);

      ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
      composer.setSuperclass(classType.getName());

      PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);

      if (printWriter == null)
      {
         // source already exists.
         return null;
      }
      else
      {
         return composer.createSourceWriter(context, printWriter);
      }
   }

   private String
   createPackageName(JClassType classType)
   {
      return classType.getPackage().getName();
   }

   private String
   getClassPrefix(JClassType classType)
   {
      String buf = null;
      JClassType enclosingType = classType.getEnclosingType();
      while (enclosingType != null)
      {
         if (buf == null)
         {
            buf = "";
         }

         buf += enclosingType.getSimpleSourceName();
         buf += '_';

         enclosingType = enclosingType.getEnclosingType();
      }

      return buf != null ? buf : "";
   }

   private String
   createClassName(JClassType classType)
   {
      return getClassPrefix(classType) + classType.getSimpleSourceName() + "Impl";
   }

   private String
   createClassNameWithPackage(JClassType classType)
   {
      return createPackageName(classType) + "." + createClassName(classType);
   }


}
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
import com.pietschy.gwt.pectin.client.bean.BeanPropertyAccessor;
import com.pietschy.gwt.pectin.client.bean.NestedTypes;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: Improve error messages when a property is referenced that hasn't been annotated with @NestedBean.
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

            Set<Class> nestedBeanTypes = getNestedTypeClasses(classType);

            // bean type is in our declaration e.g.
            // class MyProvider extends AbstractBeanModelProvider<BeanType>
            JClassType beanType = classType.getSuperclass().isParameterized().getTypeArgs()[0];

            BeanInfo beanInfo = new BeanInfo(typeOracle, beanType, nestedBeanTypes);

            writer.indent();

            generateGetPropertyTypeMethod(writer, beanInfo);

            writer.println();

            generateGetElementTypeMethod(writer, beanInfo);

            writer.println();

            generateGetBeanPropertyAccessorMethod(writer, beanInfo);

            writer.println();

            generateGetBeanPropertyAccessorByTypeMethod(writer, beanInfo);

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

   private Set<Class> getNestedTypeClasses(JClassType classType)
   {
      NestedTypes nestedTypeAnnotation = classType.findAnnotationInTypeHierarchy(NestedTypes.class);

      return nestedTypeAnnotation == null
             ? Collections.<Class>emptySet()
             : new HashSet<Class>(Arrays.asList(nestedTypeAnnotation.value()));
   }

   private void generateGetPropertyTypeMethod(final SourceWriter source, BeanInfo beanInfo)
   {
      // create the getPropertyType method
      source.println("public Class getPropertyType(String propertyPath) {");
      source.indent();

      beanInfo.visitAllProperties(new Visitor<PropertyInfo>()
      {
         public void visit(PropertyInfo propertyInfo)
         {
            source.println("if (propertyPath.equals(\"" + propertyInfo.getFullPropertyPath() + "\")) { ");
            source.indent();
            source.println("return " + propertyInfo.getTypeName() + ".class;");

            source.outdent();
            source.print("} else ");
         }
      });
      source.println("{");
      source.indent();
      source.println("throw new com.pietschy.gwt.pectin.client.bean.UnknownPropertyException(propertyPath);");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }

   private void generateGetElementTypeMethod(final SourceWriter source, BeanInfo beanInfo)
   {
      // create the getPropertyType method
      source.println("public Class getElementType(String propertyPath) {");
      source.indent();

      beanInfo.visitAllProperties(new Visitor<PropertyInfo>()
      {
         public void visit(PropertyInfo property)
         {
            source.println("if (propertyPath.equals(\"" + property.getFullPropertyPath() + "\")) { ");
            source.indent();
            if (property.isCollectionProperty())
            {
               source.println("return " + property.getCollectionElementTypeName() + ".class;");
            }
            else
            {
               source.println("throw new com.pietschy.gwt.pectin.client.bean.NotCollectionPropertyException(propertyPath, " + property.getTypeName() + ".class);");
            }
            source.outdent();
            source.print("} else ");
         }
      });
      source.println("{");
      source.indent();
      source.println("throw new com.pietschy.gwt.pectin.client.bean.UnknownPropertyException(propertyPath);");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }


   private void generateGetBeanPropertyAccessorMethod(final SourceWriter source, BeanInfo beanInfo)
   {
      // create the getPropertyType method
      source.println("public " + BeanPropertyAccessor.class.getName() + " getBeanAccessorForPropertyPath(String propertyPath) {");
      source.indent();

      beanInfo.visitAllProperties(new Visitor<PropertyInfo>()
      {
         public void visit(PropertyInfo property)
         {
            source.println("if (propertyPath.equals(\"" + property.getFullPropertyPath() + "\")) { ");
            source.indent();
            source.println("return getAccessorByType(\"" + property.getFullPropertyPath() + "\"," + property.getParentType().getTypeName() + ".class);");
            source.outdent();
            source.print("} else ");
         }
      });
      source.println("{");
      source.indent();
      source.println("throw new com.pietschy.gwt.pectin.client.bean.UnknownPropertyException(propertyPath);");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }

   private void generateGetBeanPropertyAccessorByTypeMethod(final SourceWriter source, final BeanInfo beanInfo)
   {
      // create the getPropertyType method
      source.println("public " + BeanPropertyAccessor.class.getName() + " getAccessorByType(String path, Class type) {");
      source.indent();
      // we track the types we've already scanned so we don't do duplicates if the
      // same type is referenced more than once.
      final HashSet<String> typeNames = new HashSet<String>();

      // write our root level accessor
      typeNames.add(beanInfo.getTypeName());
      writeAccessorFor(beanInfo, source);

      // and now generate types for every nested bean we find along the way.
      beanInfo.visitAllProperties(new Visitor<PropertyInfo>()
      {
         public void visit(PropertyInfo property)
         {
            // we only generate one accessor per type so we only proceed if typeNames.add returns true.
            if (property.isNestedBean() && typeNames.add(property.getNestedBeanInfo().getTypeName()))
            {
               writeAccessorFor(property.getNestedBeanInfo(), source);
            }
         }
      });
      source.println("{");
      source.indent();
      source.println("throw new IllegalStateException(\"You've found a Pectin bug.  The rebind generator failed to create an appropriate BeanPropertyAccessor for \" + path + \".\");");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }

   private void writeAccessorFor(BeanInfo beanInfo, SourceWriter source)
   {
      source.println("if (type.equals(" + beanInfo.getTypeName() + ".class)) { ");
      source.indent();
      source.println("return new " + BeanPropertyAccessor.class.getName() + "() {");
      source.indent();
      generateIsMutableMethod(beanInfo, source);
      generateReadValueMethod(beanInfo, source);
      generateWriteValueMethod(beanInfo, source);
      source.outdent();
      source.println("};");
      source.outdent();
      source.print("} else ");
   }

   private void generateIsMutableMethod(BeanInfo beanInfo, final SourceWriter source)
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


   private void generateReadValueMethod(BeanInfo bean, SourceWriter source)
   {
      // create the getAttribute method
      source.println("public Object readProperty(Object bean, String property) {");
      source.indent();

      for (PropertyInfo property : bean)
      {
         source.println("if (property.equals(\"" + property.getName() + "\")) {");
         source.indent();
         source.println("return bean == null ? null : ((" + bean.getTypeName() + ")bean)." + property.getGetterMethodName() + "();");
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
      source.println("public void writeProperty(Object bean, String property, Object value) {");
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
            source.println("((" + beanInfo.getTypeName() + ")bean)." + property.getSetterMethodName() + "((" + property.getTypeName() + ") value);");
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
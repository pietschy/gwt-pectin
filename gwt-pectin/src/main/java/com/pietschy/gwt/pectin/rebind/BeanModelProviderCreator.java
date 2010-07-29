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
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.pietschy.gwt.pectin.client.bean.*;

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

   public String createProvider() throws UnableToCompleteException
   {
      try
      {
         JClassType classType = typeOracle.getType(typeName);

         String fullClassName = createClassNameWithPackage(classType);

         Writer writer = getSourceWriterWithImports(classType,
                                                    PropertyDescriptor.class,
                                                    DefaultPropertyDescriptor.class,
                                                    CollectionPropertyDescriptor.class,
                                                    UnknownPropertyException.class,
                                                    TargetBeanIsNullException.class,
                                                    ReadOnlyPropertyException.class);


         // The source writer is null if the class already exists, so we don't need
         // to generate it.
         if (writer == null)
         {
            return fullClassName;
         }
         else
         {

            Set<Class> nestedBeanTypes = getNestedTypeClasses(classType);
            int maxNestingDepth = getRecursionDepth(classType);

            // bean type is in our declaration e.g.
            // class MyProvider extends AbstractBeanModelProvider<BeanType>
            JClassType beanType = classType.getSuperclass().isParameterized().getTypeArgs()[0];

            Context context = new Context(typeName, logger, typeOracle, nestedBeanTypes, maxNestingDepth);
            BeanInfo beanInfo = new BeanInfo(context, beanType);

            writer.indent();

            writer.println();

            // e.g. private PropertyKey key_firstName = new DefaultPropertyKey(...);
            generateDescriptorVars(writer, beanInfo);

            writer.println();

            // e.g. private BeanPropertyAccessor accessor_a_package_Class = new BeanPropertyAccessor(){...};
            generateGetPropertyDescriptorMethod(writer, beanInfo);

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
   private int getRecursionDepth(JClassType classType)
   {
      LimitPropertyDepth maxNestedDepth = classType.findAnnotationInTypeHierarchy(LimitPropertyDepth.class);

      return maxNestedDepth == null ? -1 : maxNestedDepth.value();
   }

   private String toDescriptorVarName(PropertyInfo property)
   {
      return "descriptor_" + property.getFullPropertyPath().replace('.', '_');
   }

   private String quote(String string)
   {
      return string != null ? "\"" + string + "\"" : null;
   }

   private void generateDescriptorVars(final Writer source, BeanInfo beanInfo)
   {
      beanInfo.visitAllProperties(new Visitor<PropertyInfo>()
      {
         public void visit(PropertyInfo property)
         {
            // generate an appropriate constructor call...
            BeanInfo parentBean = property.getParentBeanInfo();
            if (property.isCollectionProperty())
            {
               // CollectionPropertyDescriptor(String fullPath, String parentPath, String propertyName,
               //                              Class beanType, Class collectionType, Class elementType, boolean mutable)
               source.println("private final PropertyDescriptor %s = new CollectionPropertyDescriptor(%s, %s, %s, %s.class, %s.class, %s.class, %s) {",
                              toDescriptorVarName(property),
                              quote(property.getFullPropertyPath()),
                              quote(property.getParentPath()),
                              quote(property.getName()),
                              parentBean.getTypeName(),
                              property.getTypeName(),
                              property.getCollectionElementTypeName(),
                              property.isMutable());
            }
            else
            {
               // DefaultPropertyKey(Class type, String fullPath, String parentPath, String propertyName)
               source.println("private final PropertyDescriptor %s = new DefaultPropertyDescriptor(%s, %s, %s, %s.class, %s.class, %s) {",
                              toDescriptorVarName(property),
                              quote(property.getFullPropertyPath()),
                              quote(property.getParentPath()),
                              quote(property.getName()),
                              parentBean.getTypeName(),
                              property.getTypeName(),
                              property.isMutable());
            }

            // now implement the methods....
            source.indent();
            source.println("public Object readProperty(Object bean) {");
            source.indent();
            source.println("return bean == null ? null : ((" + parentBean.getTypeName() + ")bean)." + property.getGetterMethodName() + "();");
            source.outdent();
            source.println("}");

            source.println("public void writeProperty(Object bean, Object value) {");
            source.indent();
            source.println("if (bean == null) {");
            source.println("   throw new TargetBeanIsNullException(this, " + parentBean.getTypeName() + ".class);");
            source.println("}");
            if (property.isMutable())
            {
               source.println("((" + parentBean.getTypeName() + ")bean)." + property.getSetterMethodName() + "((" + property.getTypeName() + ") value);");
            }
            else
            {
               source.println("throw new ReadOnlyPropertyException(this);");
            }
            source.outdent();
            source.println("}");
            source.outdent();
            source.println("};");
         }
      });
   }

   private void generateGetPropertyDescriptorMethod(final Writer source, final BeanInfo beanInfo)
   {
      source.println("public PropertyDescriptor createPropertyDescriptor(String path) {");
      source.indent();
      beanInfo.visitAllProperties(new Visitor<PropertyInfo>()
      {
         public void visit(PropertyInfo property)
         {
            source.println("if (\"%s\".equals(path)) {", property.getFullPropertyPath());
            source.indent();
            source.println("return %s;", toDescriptorVarName(property));
            source.outdent();
            source.print("} else ");
         }
      });
      source.println("{");
      source.indent();
      source.println("throw new UnknownPropertyException(path);");
      source.outdent();
      source.println("}");
      source.outdent();
      source.println("}");
   }


   public Writer
   getSourceWriterWithImports(JClassType classType, Class... imports)
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
         for (Class type : imports)
         {
            composer.addImport(type.getName());
         }
         return new Writer(composer.createSourceWriter(context, printWriter));
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
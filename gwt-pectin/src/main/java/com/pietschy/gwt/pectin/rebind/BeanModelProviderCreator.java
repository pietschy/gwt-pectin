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
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
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
         SourceWriter source = getSourceWriter(classType);
         
         String fullClassName = createClassNameWithPackage(classType);
         
         // Si le wrapper existe déjà, getSourceWriter renvoie null
         // Il n'est donc pas nécessaire de créer cette classe
         if (source == null)
         {
            return fullClassName;
         }
         else
         {
            source.indent();
            source.println();
            source.println();

            JClassType beanType = classType.getSuperclass().isParameterized().getTypeArgs()[0];
            JMethod[] methods = beanType.getMethods();
            
            
            // create the getAttribute method
            source.println("public Object readValue(String property) {");
            source.indent();

            for (int i = 0; i < methods.length; i++)
            {
               String methodName = methods[i].getName();
               JParameter[] methodParameters = methods[i].getParameters();
               if (methodName.startsWith("get") && methodParameters.length == 0)
               {
                  source.println("if (property.equals(\""
                                 + toPropertyName(methods[i])
                                 + "\")) {");
                  source.indent();
                  source.println("return getBean() == null ? null : getBean()." + methodName+ "();");
                  source.outdent();
                  source.print("} else ");
               }
            }
            
            for (int i = 0; i < methods.length; i++)
            {
               String methodName = methods[i].getName();
               JParameter[] methodParameters = methods[i].getParameters();
               boolean isBooleanReturn = methods[i].getReturnType().getSimpleSourceName().equals("boolean");
               
               if (methodName.startsWith("is") && methodParameters.length == 0 && isBooleanReturn)
               {
                  source.println("if (property.equals(\""
                                 + toPropertyName(methods[i])
                                 + "\")) {");
                  source.indent();
                  source.println("return getBean() == null ? false : getBean()." + methodName+ "();");
                  source.outdent();
                  source.print("} else ");
               }
            }
            source.println("{");
            source.indent();
            source.println("throw new IllegalArgumentException(\"Unknown property=\" + property);");
            source.outdent();
            source.println("}");
            source.outdent();
            source.println("}");
            source.println();
            // create the set attribute method
            source.println("public void writeValue(String property, Object value) {");
            source.indent();
            for (int i = 0; i < methods.length; i++)
            {
               JMethod method = methods[i];
               JParameter[] parameters = method.getParameters();
               if (method.getName().startsWith("set") && parameters.length == 1)
               {
                  JType type = parameters[0].getType();
                  JPrimitiveType primativeType = type.isPrimitive();
                  String typeName = (primativeType != null) ? primativeType.getQualifiedBoxedSourceName() : type.getQualifiedSourceName();
                  
                  source.println("if (property.equals(\"" + toPropertyName(method) + "\")) { ");
                  source.indent();
                  source.println("getBean()." + method.getName() + "(("+ typeName + ") value);");
                  source.outdent();
                  source.print("} else ");
               }
            }
            source.println("{");
            source.indent();
            source.println("throw new IllegalArgumentException(\"Unknown property=\" + property);");
            source.outdent();
            source.println("}");
            source.outdent();
            source.println("}");
            
            // create the getPropertyType method
            source.println("public Class getPropertyType(String property) {");
            source.indent();

            for (int i = 0; i < methods.length; i++)
            {
               JMethod method = methods[i];
               JParameter[] parameters = method.getParameters();
               if (method.getName().startsWith("set") && parameters.length == 1)
               {
                  JType type = parameters[0].getType();
                  JPrimitiveType primativeType = type.isPrimitive();
                  
                  String typeName = (primativeType != null) ? primativeType.getQualifiedBoxedSourceName() : type.getQualifiedSourceName();
                  
                  source.println("if (property.equals(\"" + toPropertyName(method) + "\")) { ");
                  source.indent();
                  source.println("return " + typeName + ".class;");
                  
                  source.outdent();
                  source.print("} else ");
               }
            }
            
            source.println("{");
            source.indent();
            source.println("throw new IllegalArgumentException(\"Unknown property=\" + property);");
            source.outdent();
            source.println("}");
            source.outdent();
            source.println("}");
            source.println();
            
            source.commit(logger);
            
            return fullClassName;
         }
      }
      catch (NotFoundException e)
      {
         e.printStackTrace();
         return null;
      }
   }

   private String toPropertyName(JMethod method)
   {
      String name = method.getName();
      if (name.startsWith("set") || name.startsWith("get"))
      {
         name = name.substring(3);
      }
      else if (name.startsWith("is"))
      {
         name = name.substring(2);
      }
      
      return name.substring(0,1).toLowerCase() + name.substring(1);
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
   createClassName(JClassType classType)
   {
      return getClassPrefix(classType) + classType.getSimpleSourceName() + "Impl";
   }
   
   private String
   createClassNameWithPackage(JClassType classType)
   {
      return createPackageName(classType) + "." + createClassName(classType);
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
}
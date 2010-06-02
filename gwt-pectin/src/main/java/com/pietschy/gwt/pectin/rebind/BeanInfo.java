package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 15, 2010
 * Time: 9:57:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeanInfo implements Iterable<PropertyInfo>
{
   private JClassType beanType;
   private Map<String,PropertyInfo> properties;
   private TypeOracle typeOracle;
   private String parentPath;

   /**
    * Creates a top level BeanInfo instance.
    * @param typeOracle the type oracle.
    * @param beanType the beans type.
    */
   public BeanInfo(TypeOracle typeOracle, JClassType beanType)
   {
      this(typeOracle, beanType, "");
   }

   /**
    * Creates a nested BeanInfo instance.
    * @param typeOracle the type oracle.
    * @param beanType the beans type.
    * @param parentPath the path of this bean from the root bean.
    */
   public BeanInfo(TypeOracle typeOracle, JClassType beanType, String parentPath)
   {
      this.typeOracle = typeOracle;
      this.beanType = beanType;
      this.parentPath = parentPath;
      properties = scanProperties();
      // verify any nested bean properties are ..
      buildNestedBeanInfos(properties.values());
   }


   public String getTypeName()
   {
      return beanType.getQualifiedSourceName();
   }

   public Iterator<PropertyInfo> iterator()
   {
      return properties.values().iterator();
   }

   Map<String, PropertyInfo> scanProperties()
   {

      ArrayList<MethodInfo> methods = getMethods(beanType);

      HashMap<String, PropertyInfo> map = new HashMap<String, PropertyInfo>();
      for (MethodInfo method : methods)
      {
         if (method.isGetter())
         {
            PropertyInfo info = new PropertyInfo(typeOracle,
                                                 this,
                                                 parentPath,
                                                 method.getPropertyName(),
                                                 method.getReturnType(),
                                                 method.getName(),
                                                 method.isAnnotatedAsNestedBean());
            map.put(info.getName(), info);
         }
      }
      for (MethodInfo method : methods)
      {
         if (method.isSetter())
         {
            PropertyInfo info = map.get(method.getPropertyName());
            // a setter without a getter with the same type isn't a property so we ignore
            // setters that haven't had the info created from an existing getter.
            if (info != null && method.hasSingleParameterOfType(info.getType()))
            {
               info.setSetterMethodName(method.getName());
            }
         }
      }

      return map;
   }

   private void buildNestedBeanInfos(Iterable<PropertyInfo> properties)
   {
      for (PropertyInfo info : properties)
      {
         if (info.isNestedBean())
         {
            // this will generate the nested bean info and barf if there are
            // any issues.
            BeanInfo nestedBean = info.getNestedBeanInfo();
            // now scan the the nested bean for more nesting.
            buildNestedBeanInfos(nestedBean);
         }
      }
   }

   public void visitAllProperties(Visitor<PropertyInfo> visitor)
   {
      for (PropertyInfo info : properties.values())
      {
         visitor.visit(info);
         if (info.isNestedBean())
         {
            info.getNestedBeanInfo().visitAllProperties(visitor);
         }
      }
   }

   ArrayList<MethodInfo> getMethods(JClassType beanType)
   {
      ArrayList<MethodInfo> result = new ArrayList<MethodInfo>();
      extractMethods(beanType, result);

      beanType = beanType.getSuperclass();
      while(beanType != null)
      {
         extractMethods(beanType, result);
         beanType = beanType.getSuperclass();
      }
      
      return result;
   }

   private void extractMethods(JClassType beanType, ArrayList<MethodInfo> result)
   {
      for (JMethod method : beanType.getMethods())
      {
         result.add(new MethodInfo(method));
      }
   }

}

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


   public BeanInfo(TypeOracle typeOracle, JClassType beanType)
   {
      this.typeOracle = typeOracle;
      this.beanType = beanType;
      properties = scanProperties();
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
            PropertyInfo info = new PropertyInfo(typeOracle, method.getPropertyName(),
                                                 method.getReturnType(),
                                                 method.getName());
            map.put(info.getName(), info);
         }
      }
      for (MethodInfo method : methods)
      {
         if (method.isSetter())
         {
            PropertyInfo info = map.get(method.getPropertyName());
            // a setter without a getter with the same type isn't a property
            if (info != null && method.hasSingleParameterOfType(info.getType()))
            {
               info.setSetterMethodName(method.getName());
            }
         }
      }

      return map;
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

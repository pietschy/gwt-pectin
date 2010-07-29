package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;

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
   private Context context;
   private JClassType beanType;
   private PropertyInfo property;
   private Map<String, PropertyInfo> properties;


   /**
    * Creates a top level BeanInfo instance.
    *
    * @param context  the type oracle.
    * @param beanType the beans type.
    */
   public BeanInfo(Context context, JClassType beanType) throws UnableToCompleteException
   {
      this.context = context;
      this.beanType = beanType;
      processProperties();
   }

   /**
    * Creates a nested BeanInfo instance.
    *
    * @param typeOracle the type oracle.
    * @param beanType   the beans type.
    * @param parentPath the path of this bean from the root bean.
    */
   public BeanInfo(PropertyInfo property) throws UnableToCompleteException
   {
      this.property = property;
      this.context = property.getParentBeanInfo().getContext();
      this.beanType = property.getAsClassType();
      processProperties();
   }


   public String getTypeName()
   {
      return beanType.getQualifiedSourceName();
   }

   public Iterator<PropertyInfo> iterator()
   {
      return properties.values().iterator();
   }

   private String getPath()
   {
      return property != null ? property.getFullPropertyPath() : null;
   }

   protected void processProperties() throws UnableToCompleteException
   {
      properties = new HashMap<String, PropertyInfo>();

      extractMethodProperties(properties);

      processNestedBeans(properties.values());
   }

   private void extractMethodProperties(Map<String, PropertyInfo> properties)
   {
      ArrayList<MethodInfo> methods = getMethods(beanType);
      for (MethodInfo method : methods)
      {
         if (method.isGetter())
         {
            PropertyInfo info = new PropertyInfo(this,
                                                 getPath(),
                                                 method.getPropertyName(),
                                                 method.getReturnType(),
                                                 method.getName(),
                                                 method.isAnnotatedAsNestedBean());
            properties.put(info.getName(), info);
         }
      }
      for (MethodInfo method : methods)
      {
         if (method.isSetter())
         {
            PropertyInfo info = properties.get(method.getPropertyName());
            // a setter without a getter with the same type isn't a property so we ignore
            // setters that haven't had the info created from an existing getter.
            if (info != null && method.hasSingleParameterOfType(info.getType()))
            {
               info.setSetterMethodName(method.getName());
            }
         }
      }
   }

   private void processNestedBeans(Iterable<PropertyInfo> properties) throws UnableToCompleteException
   {
      // check the context
      for (PropertyInfo property : properties)
      {
         if (context.isNestedBeanCandidate(property))
         {
            // this will generate the nested bean info and barf if there are
            // any issues.
            property.createNestedBeanInfo();

            // now scan the the nested bean for more nesting.
            processNestedBeans(property.getNestedBeanInfo());
         }
      }
   }

   public void visitAllProperties(Visitor<PropertyInfo> visitor)
   {
      for (PropertyInfo property : properties.values())
      {
         visitor.visit(property);

         if (property.hasNestedBeanInfo())
         {
            property.getNestedBeanInfo().visitAllProperties(visitor);
         }
      }
   }

   ArrayList<MethodInfo> getMethods(JClassType beanType)
   {
      ArrayList<MethodInfo> result = new ArrayList<MethodInfo>();
      extractMethods(beanType, result);

      beanType = beanType.getSuperclass();
      while (beanType != null)
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

//   ArrayList<JField> getPublicFields(JClassType beanType)
//   {
//      ArrayList<JField> result = new ArrayList<JField>();
//      extractPublicFields(beanType, result);
//
//      beanType = beanType.getSuperclass();
//      while (beanType != null)
//      {
//         extractPublicFields(beanType, result);
//         beanType = beanType.getSuperclass();
//      }
//
//      return result;
//   }
//
//   private void extractPublicFields(JClassType beanType, ArrayList<JField> result)
//   {
//      for (JField field : beanType.getFields())
//      {
//         if (field.isPublic() && !field.isStatic())
//         {
//            result.add(field);
//         }
//      }
//   }

   public boolean isRootBean()
   {
      return property == null;
   }

   public PropertyInfo asProperty()
   {
      return property;
   }

   public Context getContext()
   {
      return context;
   }

   public int getPropertyDepth()
   {
      return isRootBean() ? 0 : property.getPropertyDepth();
   }
}

package com.pietschy.gwt.pectin.reflect;

import com.pietschy.gwt.pectin.client.bean.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 1, 2010
 * Time: 11:34:01 AM
 * To change this template use File | Settings | File Templates.
 */
class ReflectionBeanPropertyAccessor implements BeanPropertyAccessor
{
   private Class beanClass;
   private HashMap<Class<?>, Class<?>> primitives = new HashMap<Class<?>, Class<?>>();

   public ReflectionBeanPropertyAccessor(Class beanClass)
   {
      this.beanClass = beanClass;
      primitives.put(boolean.class, Boolean.class);
      primitives.put(char.class, Character.class);
      primitives.put(byte.class, Byte.class);
      primitives.put(short.class, Short.class);
      primitives.put(int.class, Integer.class);
      primitives.put(long.class, Long.class);
      primitives.put(float.class, Float.class);
      primitives.put(double.class, Double.class);
   }

   public Object readProperty(Object bean, String property) throws UnknownPropertyException
   {
      Method getter = getGetter(property);

      if (bean == null)
      {
         return null;
      }

      try
      {
         return getter.invoke(bean);
      }
      catch (IllegalAccessException e)
      {
         throw new UnknownPropertyException(property);
      }
      catch (InvocationTargetException e)
      {
         throw new UnknownPropertyException(property);
      }
   }

   public void writeProperty(Object bean, String property, Object value) throws UnknownPropertyException
   {
      if (bean == null)
      {
         throw new TargetBeanIsNullException(beanClass);
      }

      Method setter = getSetter(property);

      try
      {
         setter.invoke(bean, value);
      }
      catch (IllegalAccessException e)
      {
         throw new UnknownPropertyException(property);
      }
      catch (InvocationTargetException e)
      {
         throw new UnknownPropertyException(property);
      }
   }

   public boolean isMutable(String propertyName) throws UnknownPropertyException
   {
      // oh so hacky...
      try
      {
         getSetter(propertyName);
         return true;
      }
      catch (ImmutablePropertyException e)
      {
         return false;
      }
   }

   private Method getGetter(String property) throws UnknownPropertyException
   {
      try
      {
         return findMethod("get" + capitalise(property));
      }
      catch (NoSuchMethodException e)
      {
         try
         {
            // should really test it's return type is boolean...
            return findMethod("is" + capitalise(property));
         }
         catch (NoSuchMethodException e1)
         {
            throw new UnknownPropertyException(property);
         }
      }
   }

   private Method findMethod(String methodName, Class... parameterTypes) throws NoSuchMethodException
   {
      Class theClass = this.beanClass;
      while (true)
      {
         try
         {
            return theClass.getMethod(methodName, parameterTypes);
         }
         catch (NoSuchMethodException e)
         {
            theClass = theClass.getSuperclass();
            if (theClass == null)
            {
               throw new NoSuchMethodException();
            }
         }
      }
   }

   private Method getSetter(String property) throws UnknownPropertyException, ImmutablePropertyException
   {
      // make sure it has a getter first to ensure it's a property.  This will
      // throw an UnknownPropertyException if it doesn't exist.
      getGetter(property);

      try
      {
         return beanClass.getMethod("set" + capitalise(property), getRawPropertyType(property));
      }
      catch (NoSuchMethodException e)
      {
         throw new ImmutablePropertyException(property);
      }
   }

   public Class getPropertyType(String propertyName) throws UnknownPropertyException
   {
      Class<?> type = getRawPropertyType(propertyName);
      return type.isPrimitive() ? primitives.get(type) : type;
   }

   private Class<?> getRawPropertyType(String propertyName)
   {
      return getGetter(propertyName).getReturnType();
   }

   public Class getElementType(String propertyName) throws UnknownPropertyException, NotCollectionPropertyException
   {
      Method method = getGetter(propertyName);
      Class<?> type = method.getReturnType();

      if (!Collection.class.isAssignableFrom(type))
      {
         throw new NotCollectionPropertyException(propertyName, type);
      }

      Type returnType = method.getGenericReturnType();

      if (returnType instanceof ParameterizedType)
      {
         Type[] typeArgs = ((ParameterizedType) returnType).getActualTypeArguments();
         return typeArgs.length == 1 ? (Class) typeArgs[0] : Object.class;
      }
      else
      {
         return Object.class;
      }

   }


   private String capitalise(String property)
   {
      return property.substring(0, 1).toUpperCase() + property.substring(1);
   }
}

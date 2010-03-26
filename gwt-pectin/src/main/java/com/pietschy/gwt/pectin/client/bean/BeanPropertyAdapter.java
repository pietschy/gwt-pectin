package com.pietschy.gwt.pectin.client.bean;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 9, 2010
 * Time: 10:37:06 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanPropertyAdapter<B>
{
   Class getPropertyType(String property) throws UnknownPropertyException;

   Class getElementType(String property) throws UnknownPropertyException, NotCollectionPropertyException;

   Object readProperty(B bean, String property) throws UnknownPropertyException;

   void writeProperty(B bean, String property, Object value) throws UnknownPropertyException, ImmutablePropertyException, TargetBeanIsNullException;

   boolean isMutable(String propertyName);

//   <T> BeanPropertyAdapter<T> getNestedAdapter(String propertyName, Class<T> beanType);
}

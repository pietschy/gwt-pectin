package com.pietschy.gwt.pectin.client.bean;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 9, 2010
 * Time: 10:37:06 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanPropertyAccessor
{
   Object readProperty(Object bean, String property) throws UnknownPropertyException;
   void writeProperty(Object bean, String property, Object value) throws UnknownPropertyException, ImmutablePropertyException, TargetBeanIsNullException;
   boolean isMutable(String propertyName) throws UnknownPropertyException;
}
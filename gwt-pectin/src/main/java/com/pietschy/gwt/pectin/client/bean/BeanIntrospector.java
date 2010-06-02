package com.pietschy.gwt.pectin.client.bean;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 12, 2010
 * Time: 4:33:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanIntrospector
{
   Class getPropertyType(String propertyPath) throws UnknownPropertyException;
   Class getElementType(String propertyPath) throws UnknownPropertyException, NotCollectionPropertyException;
   BeanPropertyAccessor getBeanAccessorForPropertyPath(String propertyPath) throws UnknownPropertyException;
}

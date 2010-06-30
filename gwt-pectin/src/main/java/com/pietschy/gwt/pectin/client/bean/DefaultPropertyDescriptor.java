package com.pietschy.gwt.pectin.client.bean;

/**
 *
 */
public abstract class DefaultPropertyDescriptor extends AbstractPropertyDescriptor 
{
   private Class valueType;

   public DefaultPropertyDescriptor(String fullPath, String parentPath, String propertyName, Class beanType, Class valueType, boolean mutable)
   {
      super(fullPath, parentPath, propertyName, beanType, mutable);
      this.valueType = valueType;
   }

   public Class getValueType()
   {
      return valueType;
   }

   public boolean isCollection()
   {
      return false;
   }

   public Class getElementType() throws NotCollectionPropertyException
   {
      throw new NotCollectionPropertyException(this, getValueType());
   }

}
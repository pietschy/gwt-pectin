package com.pietschy.gwt.pectin.client.bean;

/**
 *
 */
public abstract class CollectionPropertyDescriptor extends AbstractPropertyDescriptor
{
   private Class collectionType;
   private Class elementType;

   // used by the rebind process
   public CollectionPropertyDescriptor(String fullPath, String parentPath, String propertyName, Class beanType, Class collectionType, Class elementType, boolean mutable)
   {
      super(fullPath, parentPath, propertyName, beanType, mutable);
      this.collectionType = collectionType;
      this.elementType = elementType;
   }

   public Class getValueType()
   {
      return collectionType;
   }

   public boolean isCollection()
   {
      return true;
   }

   public Class getElementType()
   {
      return elementType;
   }
}
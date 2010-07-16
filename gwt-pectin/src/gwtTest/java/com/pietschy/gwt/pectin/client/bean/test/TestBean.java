package com.pietschy.gwt.pectin.client.bean.test;

import com.pietschy.gwt.pectin.client.bean.NestedBean;


/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 9, 2010
 * Time: 3:26:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestBean
{
   private Object object;
   private String string;

   private int primitiveInt;
   private boolean primitiveBoolean;
   private Integer objectInteger;
   private Boolean objectBoolean;

   private Object readOnlyObject = "read only object";
   private int readOnlyPrimitiveInt = 42;

//   private PublicFieldsDTO dto = new PublicFieldsDTO("abc");
   private AnotherBean nestedBean;
   private AnotherBean readOnlyNestedBean = new AnotherBean();
   private BeanWithCollections collections;
   private RecursiveBeanOne recursiveBean;

   public Object getObject()
   {
      return object;
   }

   public void setObject(Object object)
   {
      this.object = object;
   }

   public String getString()
   {
      return string;
   }

   public void setString(String string)
   {
      this.string = string;
   }

   public int getPrimitiveInt()
   {
      return primitiveInt;
   }

   public void setPrimitiveInt(int primitiveInt)
   {
      this.primitiveInt = primitiveInt;
   }

   public boolean isPrimitiveBoolean()
   {
      return primitiveBoolean;
   }

   public void setPrimitiveBoolean(boolean primitiveBoolean)
   {
      this.primitiveBoolean = primitiveBoolean;
   }

   public Integer getObjectInteger()
   {
      return objectInteger;
   }

   public void setObjectInteger(Integer objectInteger)
   {
      this.objectInteger = objectInteger;
   }

   public Boolean getObjectBoolean()
   {
      return objectBoolean;
   }

   public void setObjectBoolean(Boolean objectBoolean)
   {
      this.objectBoolean = objectBoolean;
   }

   // we're not marking this one as nested, but relying on the
   // @NestedTypes annotation on the provider.
   public AnotherBean getNestedBean()
   {
      return nestedBean;
   }

   public void setNestedBean(AnotherBean nestedBean)
   {
      this.nestedBean = nestedBean;
   }

   public AnotherBean getReadOnlyNestedBean()
   {
      return readOnlyNestedBean;
   }

   @NestedBean
   public BeanWithCollections getCollections()
   {
      return collections;
   }

   public void setCollections(BeanWithCollections collections)
   {
      this.collections = collections;
   }

//   public PublicFieldsDTO getDto()
//   {
//      return dto;
//   }

   public Object getReadOnlyObject()
   {
      return readOnlyObject;
   }

   public int getReadOnlyPrimitiveInt()
   {
      return readOnlyPrimitiveInt;
   }

   public RecursiveBeanOne getRecursiveBean()
   {
      return recursiveBean;
   }

   public void setRecursiveBean(RecursiveBeanOne recursiveBean)
   {
      this.recursiveBean = recursiveBean;
   }
}
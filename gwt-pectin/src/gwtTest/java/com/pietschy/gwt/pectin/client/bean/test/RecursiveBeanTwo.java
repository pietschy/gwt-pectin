package com.pietschy.gwt.pectin.client.bean.test;

/**
 * This is a bean that has an instanceof itself as one of it's properties.
 */
public class RecursiveBeanTwo
{
   private RecursiveBeanOne bean;

   public RecursiveBeanOne getBean()
   {
      return bean;
   }

   public void setBean(RecursiveBeanOne bean)
   {
      this.bean = bean;
   }
}
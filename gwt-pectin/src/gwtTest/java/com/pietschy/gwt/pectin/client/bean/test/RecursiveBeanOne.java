package com.pietschy.gwt.pectin.client.bean.test;

/**
 * This is a bean that has an instanceof itself as one of it's properties.
 */
public class RecursiveBeanOne
{
   private RecursiveBeanTwo bean;

   public RecursiveBeanTwo getBean()
   {
      return bean;
   }

   public void setBean(RecursiveBeanTwo bean)
   {
      this.bean = bean;
   }
}

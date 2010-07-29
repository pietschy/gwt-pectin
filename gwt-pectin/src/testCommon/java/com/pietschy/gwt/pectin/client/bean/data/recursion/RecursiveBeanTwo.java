package com.pietschy.gwt.pectin.client.bean.data.recursion;

/**
 * This is a bean that has an instanceof itself as one of it's properties.
 */
public class RecursiveBeanTwo
{
   private RecursiveBeanOne one;

   public RecursiveBeanOne getOne()
   {
      return one;
   }

   public void setOne(RecursiveBeanOne one)
   {
      this.one = one;
   }
}
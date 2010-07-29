package com.pietschy.gwt.pectin.client.bean.data.recursion;

/**
 * This is a bean that has an instanceof itself as one of it's properties.
 */
public class RecursiveBeanOne
{
   private RecursiveBeanTwo two;

   public RecursiveBeanTwo getTwo()
   {
      return two;
   }

   public void setTwo(RecursiveBeanTwo two)
   {
      this.two = two;
   }
}

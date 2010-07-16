package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.typeinfo.TypeOracle;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 4, 2010
 * Time: 10:36:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class Context
{
   private TypeOracle typeOracle;
   private Set<Class> nestedBeanTypes;
   private int maxPropertyDepth;

   public Context(TypeOracle typeOracle, Set<Class> nestedBeanTypes, int maxPropertyDepth)
   {
      this.typeOracle = typeOracle;
      this.nestedBeanTypes = nestedBeanTypes;
      this.maxPropertyDepth = maxPropertyDepth;
   }

   public TypeOracle getTypeOracle()
   {
      return typeOracle;
   }

   public Set<Class> getNestedBeanTypes()
   {
      return nestedBeanTypes;
   }

   public boolean hasRecursionLimit()
   {
      return maxPropertyDepth > 0;
   }

   public boolean isNestedBean(PropertyInfo property)
   {
      return property.isNestedBean();
   }
}

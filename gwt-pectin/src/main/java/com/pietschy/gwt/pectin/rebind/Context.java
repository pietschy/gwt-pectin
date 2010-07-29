package com.pietschy.gwt.pectin.rebind;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.pietschy.gwt.pectin.client.bean.LimitPropertyDepth;

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
   private TreeLogger logger;
   private String providerTypeName;

   public Context(String providerTypeName, TreeLogger logger, TypeOracle typeOracle, Set<Class> nestedBeanTypes, int maxPropertyDepth)
   {
      this.providerTypeName = providerTypeName;
      this.logger = logger;
      this.typeOracle = typeOracle;
      this.nestedBeanTypes = nestedBeanTypes;
      this.maxPropertyDepth = maxPropertyDepth;
   }

   public TypeOracle getTypeOracle()
   {
      return typeOracle;
   }

   public boolean hasRecursionLimit()
   {
      return maxPropertyDepth > 0;
   }

   /**
    * This method checks that the specified property should be traversed as a nested
    * bean.
    * @param property the property to check.
    * @return
    * @throws com.google.gwt.core.ext.UnableToCompleteException
    */
   public boolean isNestedBeanCandidate(PropertyInfo property) throws UnableToCompleteException
   {
      // check if path is recursive
      if (hasRecursionLimit())
      {
         // we have to be less than the max depth as our properties will be over it if were equal..
         return (property.getPropertyDepth() < maxPropertyDepth) && isNestedTypeOrHasAnnotation(property);
      }
      else
      {
         // check we're not recursive..
         if (property.isRecursive())
         {
            logger.log(TreeLogger.Type.ERROR, "Found recursive path at `" + property.getFullPropertyPath() + "`.  Please add a @" + LimitPropertyDepth.class.getSimpleName() +
                                              " annotation to `" + providerTypeName + "`.");

            throw new UnableToCompleteException();
         }

         return isNestedTypeOrHasAnnotation(property);
      }

   }

   private boolean isNestedTypeOrHasAnnotation(PropertyInfo property)
   {
      if (property.isAnnotatedWithNestedBean())
      {
         return true;
      }
      else
      {
         for (Class nestedBeanType : nestedBeanTypes)
         {
            if (property.getTypeName().equals(nestedBeanType.getName()))
            {
               return true;
            }
         }
         return false;
      }
   }
}

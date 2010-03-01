package com.pietschy.gwt.pectin.client.function;

import java.util.List;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Feb 27, 2010
* Time: 10:06:39 AM
* To change this template use File | Settings | File Templates.
*/
public class Join implements Reduce<String, String>
{
   private String separator;

   public Join(String separator)
   {
      this.separator = separator;
   }

   public String compute(List<? extends String> source)
   {
      StringBuilder buf = null;
      for (String value : source)
      {
         if (buf == null)
         {
            buf = new StringBuilder();
         }
         else
         {
            buf.append(separator);
         }

         buf.append(value);
      }
      return buf != null ? buf.toString() : "";
   }
}

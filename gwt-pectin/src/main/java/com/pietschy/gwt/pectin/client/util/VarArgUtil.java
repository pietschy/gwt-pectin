package com.pietschy.gwt.pectin.client.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 24, 2010
 * Time: 5:13:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class VarArgUtil
{
   public static <T> List<T> asList(T first, T... others)
   {
      ArrayList<T> list = new ArrayList<T>();
      list.add(first);
      if (others.length > 0)
      {
         list.addAll(Arrays.asList(others));
      }
      return list;
   }
}

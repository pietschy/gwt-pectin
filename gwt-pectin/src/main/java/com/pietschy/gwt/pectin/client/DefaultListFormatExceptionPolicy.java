package com.pietschy.gwt.pectin.client;

import com.pietschy.gwt.pectin.client.format.FormatException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 8, 2010
 * Time: 2:07:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultListFormatExceptionPolicy<T> implements ListFormatExceptionPolicy<T>
{
   public void onFormatException(List<T> valueList, FormatException e)
   {
      // do nothing.
   }
}
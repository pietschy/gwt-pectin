package com.pietschy.gwt.pectin.client.form;

import com.pietschy.gwt.pectin.client.format.FormatException;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Jun 8, 2010
* Time: 2:07:44 PM
* To change this template use File | Settings | File Templates.
*/
public class DefaultFormatExceptionPolicy<T> implements FormatExceptionPolicy<T>
{

   public void onFormatException(FormattedFieldModel<T> model, FormatException e)
   {
      // do nothing.
   }
}

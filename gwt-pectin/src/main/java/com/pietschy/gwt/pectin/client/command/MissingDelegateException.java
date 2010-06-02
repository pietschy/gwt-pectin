package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.ui.UIObject;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 6, 2010
 * Time: 12:27:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MissingDelegateException extends RuntimeException
{
   public MissingDelegateException(Object debugInfo)
   {
      super(getMessage(debugInfo));
   }

   private static String getMessage(Object debugInfo)
   {
      return debugInfo != null ? debugInfo(debugInfo) :
             "If you'd set the debug context you'd see it here now.";
   }

   private static String debugInfo(Object debugInfo)
   {
      // UIObjects print their content and their class name would be more useful.
      return debugInfo instanceof UIObject ? "debugContext=" + debugInfo.getClass().getName() : String.valueOf(debugInfo);
   }
}

package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 21, 2010
 * Time: 3:10:52 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Invocation
{
   public void proceed()
   {
      getProceedCommand().execute();
   }
   
   public abstract Command getProceedCommand();
}

package com.pietschy.gwt.pectin.client.activity;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 28, 2010
 * Time: 10:20:29 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ActivityCallback<R,E>
{
   void publishSuccess(R result);
   void publishError(E error);
}

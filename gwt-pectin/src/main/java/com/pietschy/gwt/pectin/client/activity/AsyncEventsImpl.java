package com.pietschy.gwt.pectin.client.activity;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 18, 2010
 * Time: 1:22:43 PM
 * To change this template use File | Settings | File Templates.
 */
class AsyncEventsImpl<R, E> extends AbstractAsyncEvents<R,E>
{
   public AsyncEventsImpl()
   {
   }

   public void fireStart()
   {
      getStartChannel().publish(null);
   }

   public void fireSuccess(R result)
   {
      getResultChannel().publish(result);
   }

   public void fireError(E result)
   {
      getErrorChannel().publish(result);
   }

}

package com.pietschy.gwt.pectin.demo.client.command;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pietschy.gwt.pectin.demo.client.domain.Person;

/**
 * This is a dummy save service that has a 50/50 chance of failing.
 */
public class SaveServiceAsync
{
   protected void save(final Person person, final AsyncCallback<Person> callback)
   {
      // we'll fake an async operation... normally you'd call an actual async
      // service interface here and put the receive call in the AsyncCallback
      new Timer()
      {
         @Override
         public void run()
         {
            // using 3 so that the default case has the same likely hood as an error.
            switch (Random.nextInt(3))
            {
               case 0: callback.onFailure(new SaveException());break;
               case 1: callback.onFailure(new Throwable("Aaarrrggg")); break;
               default: callback.onSuccess(person); break;
            }
         }
      }.schedule(2000);
   }
}
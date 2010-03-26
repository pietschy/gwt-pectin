package com.pietschy.gwt.pectin.demo.client.activity;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pietschy.gwt.pectin.client.activity.AbstractAsyncActivity;
import com.pietschy.gwt.pectin.client.activity.ActivityCallback;
import com.pietschy.gwt.pectin.client.activity.Invocation;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.demo.client.domain.Person;

/**
 * The save activity is responsible for validating the model, copying the bean
 * and invoking the underlying save mechanism.  On completion it publishes the
 * success and error to the appropriate channels.  The active state of the activity
 * is automatically updated.
 */
public class SaveActivity extends AbstractAsyncActivity<Person, Throwable>
{
   private SaveServiceAsync service;
   private EditPersonModel model;
   private Channel<String> notificationChannel;

   public SaveActivity(SaveServiceAsync service, EditPersonModel model, Channel<String> notificationChannel)
   {
      this.service = service;
      this.model = model;
      this.notificationChannel = notificationChannel;

      // lets disable while we're active... probably a UX anti-pattern but
      // at least you can see how to do it.
      disableWhen(active());
   }

   @Override
   protected void beforeStarting(Invocation invocation)
   {
      if (model.validate())
      {
         invocation.proceed();
      }
   }

   @Override
   protected void onStarting()
   {
      notificationChannel.publish("Saving.... (we're just pretending, I'm using Random.nextBoolean() to fake errors.)");
   }

   /**
    * Invoke the async service to save our bean.
    * 
    * @param callback used to publish our results.
    */
   protected void performActivity(final ActivityCallback<Person, Throwable> callback)
   {
      // copy the model changes into a new instance.. normally I'd have a person.copy() method to ensure
      // any non exposed attributes are also copied but this is just a demo.
      Person p = new Person();
      model.copyTo(p);

      service.save(p, new AsyncCallback<Person>()
      {
         public void onSuccess(Person result)
         {
            callback.publishSuccess(result);
         }

         public void onFailure(Throwable caught)
         {
            callback.publishError(caught);
         }
      });
   }

   @Override
   protected void afterSuccess(Person result)
   {
      // after we've finished we'll update our model to reflect the updated bean that
      // came back from the service.
      model.readFrom(result);
      notificationChannel.publish("Saved worked.");
   }

   @Override
   protected void afterError(Throwable error)
   {
      notificationChannel.publish("Doh, it failed.");
   }
}

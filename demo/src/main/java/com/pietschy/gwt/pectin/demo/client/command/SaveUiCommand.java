package com.pietschy.gwt.pectin.demo.client.command;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.command.AbstractAsyncUiCommand;
import com.pietschy.gwt.pectin.client.command.AsyncCommandCallback;
import com.pietschy.gwt.pectin.client.command.Invocation;
import com.pietschy.gwt.pectin.demo.client.domain.Person;

/**
 * The save command is responsible for validating the model, copying the bean
 * and invoking the underlying save mechanism.  On completion it publishes the
 * success and error to the appropriate channels.  The active state of the command
 * is automatically updated.
 */
public class SaveUiCommand extends AbstractAsyncUiCommand<Person, Throwable>
{
   private SaveServiceAsync service;
   private EditPersonModel model;
   private Channel<String> notificationChannel;

   public SaveUiCommand(SaveServiceAsync service, EditPersonModel model, Channel<String> notificationChannel)
   {
      this.service = service;
      this.model = model;
      this.notificationChannel = notificationChannel;

      // lets disable while we're active...
      disableWhen(active());
   }

   @Override
   protected void intercept(Invocation invocation)
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
   protected void performAsyncOperation(final AsyncCommandCallback<Person, Throwable> callback)
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
      // came back from the service.  Could also have done this via creating a binding
      // using `always().sendResultTo(..)` style but this is easier in this case.
      model.readFrom(result);
      notificationChannel.publish("Saved worked.");
   }

   @Override
   protected void afterError(Throwable error)
   {
      // Could also have done this via creating a binding using `always().sendResultTo(..)`
      // but I'm keeping it consistent with the above.
      notificationChannel.publish("Doh, it failed.");
   }
}

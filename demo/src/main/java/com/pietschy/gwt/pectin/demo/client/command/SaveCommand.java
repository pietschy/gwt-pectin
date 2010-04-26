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
public class SaveCommand extends AbstractAsyncUiCommand<Person, Throwable>
{
   private SaveServiceAsync service;
   private EditPersonModel model;

   public SaveCommand(SaveServiceAsync service, EditPersonModel model, Channel<String> notificationChannel)
   {
      this.service = service;
      this.model = model;

      // The following could also be done by overriding onStarting(), afterError() and afterSuccess().
      always()
         .onStartSend("Saving.... (we're just pretending, I'm using Random.nextBoolean() to fake errors.)")
         .to(notificationChannel);

      // our model implements Destination so we can send the
      // results straight to it.
      always()
         .sendResultTo(model);
      // and send a notification
      always()
         .onSuccessSend("Save worked.")
         .to(notificationChannel);

      // if we wanted to display the actual error we'd use always().sendErrorTo(...)
      always()
         .onErrorSend("Doh, it failed!")
         .to(notificationChannel);

      // and finally let's disable while we're active...
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
}

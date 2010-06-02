package com.pietschy.gwt.pectin.demo.client.command;

import com.google.gwt.user.client.rpc.AsyncCallback;
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

   public SaveCommand(SaveServiceAsync service, EditPersonModel model)
   {
      this.service = service;
      this.model = model;

      // our model implements ValueTarget so we can send the
      // results straight to it.
      always().sendResultTo(model);

      // and finally let's disable while we're active...
      disableWhen(active());
   }

   @Override
   protected void intercept(Invocation invocation)
   {
      if (model.validate())
      {
         model.commit();
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
      service.save(model.getValue(), new AsyncCallback<Person>()
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

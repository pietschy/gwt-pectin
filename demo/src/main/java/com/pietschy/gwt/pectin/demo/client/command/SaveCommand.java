package com.pietschy.gwt.pectin.demo.client.command;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pietschy.gwt.pectin.client.binding.Binder;
import com.pietschy.gwt.pectin.client.command.AbstractAsyncUiCommand;
import com.pietschy.gwt.pectin.client.command.AsyncCommandCallback;
import com.pietschy.gwt.pectin.client.interceptor.Invocation;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.pietschy.gwt.pectin.demo.client.domain.Person;

import static com.pietschy.gwt.pectin.client.condition.Conditions.isNot;

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
   private ValueHolder<String> text = new ValueHolder<String>();
   private Binder binder = new Binder();

   public SaveCommand(SaveServiceAsync service, EditPersonModel model)
   {
      this.service = service;
      this.model = model;

      // our model implements ValueTarget so we can send the
      // results straight to it.
      always().sendResultTo(model);

      // and finally let's disable while we're active...
      disableWhen(isNot(model.dirty).or(active()));

      text.setValue("Save");

      binder.onTransitionOf(model.dirty).to(true).invoke(new Command()
      {
         public void execute()
         {
            text.setValue("Save");
         }
      });

   }

   @Override
   protected void intercept(Invocation invocation)
   {
      if (model.validate())
      {
         model.commit(false);
         invocation.proceed();
      }
   }

   @Override
   protected void onStarting()
   {
      text.setValue("Saving...");
   }

   @Override
   protected void afterSuccess(Person result)
   {
      model.setValue(result);
      text.setValue("Saved");
   }

   @Override
   protected void afterError(Throwable error)
   {
      text.setValue("Try save again");
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

   public ValueModel<String> text()
   {
      return text;
   }
}

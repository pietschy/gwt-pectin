package com.pietschy.gwt.pectin.demo.client.command;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.pietschy.gwt.pectin.client.binding.Binder;
import com.pietschy.gwt.pectin.client.command.AbstractAsyncUiCommand;
import com.pietschy.gwt.pectin.client.command.AsyncCommandCallback;
import com.pietschy.gwt.pectin.client.command.ExceptionHandler;
import com.pietschy.gwt.pectin.client.command.ExceptionManager;
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
public class SaveCommand extends AbstractAsyncUiCommand<Person, String>
{
   private SaveServiceAsync service;
   private ExceptionManager<String> exceptionManager = new ExceptionManager<String>();
   private EditPersonModel model;
   private ValueHolder<String> text = new ValueHolder<String>();
   private Binder binder = new Binder();
   private static final String TEXT_SAVE = "Save";
   private static final String TEXT_SAVING = "Saving...";
   private static final String TEXT_SAVED = "Saved";
   private static final String TEXT_TRY_SAVE_AGAIN = "Try save again";


   public SaveCommand(SaveServiceAsync service, EditPersonModel model)
   {
      this.service = service;
      this.model = model;

      // our model implements ValueTarget so we can send the
      // results straight to it.
      always().sendResultTo(model);

      // and finally let's disable while we're active...
      disableWhen(isNot(model.dirty).or(active()));


      // when ever the model goes dirty we makes sure our text is
      // set to "Save".
      binder.onTransitionOf(model.dirty).to(true).invoke(setText(TEXT_SAVE));
      // now configure our dynamic messages.  We could also do this by overriding
      // onStart, afterResult, afterError if we wanted, there would be less listeners
      // involved that way.
      always().onStartInvoke(setText(TEXT_SAVING));
      always().onSuccessInvoke(setText(TEXT_SAVED));
      always().onErrorInvoke(setText(TEXT_TRY_SAVE_AGAIN));

      // Sending the new value tto the model will also cause it to go non-dirty
      // we'll disable.
      always().sendResultTo(model);

      // initialise out text.
      text.setValue(TEXT_SAVE);

      // configure our exception handling...
      exceptionManager.onCatching(SaveException.class).publishError("Oops, we caught a SaveException");

      // We can also install a generic handler for the various HTTP/RPC exceptions, but I'm too lazy
      // for the demo so I'll just show an Oops message.
      exceptionManager.onUnregisteredExceptionsInvoke(new ExceptionHandler<Throwable, String>()
      {
         @Override
         public void handle(Throwable error)
         {
            // Something went really wrong so we'll abort the usual flow and display
            // a 'we have a bug' type message.
            abort();
            Window.alert("We caught a Throwable!");
            // this is a bit of a pain, should really have an onErrorOrAbortInvoke(..)
            // option.
            text.setValue(TEXT_TRY_SAVE_AGAIN);
         }
      });
   }

   private Command setText(final String text)
   {
      return new Command()
      {
         public void execute()
         {
            SaveCommand.this.text.setValue(text);
         }
      };
   }

   @Override
   protected void intercept(Invocation invocation)
   {
      if (model.validate())
      {
         // passing `false` commits without affecting the dirty
         // state.  The dirty state is reset when the model is
         // updated with the result when the save completes.
         model.commit(false);
         invocation.proceed();
      }
   }

   /**
    * Invoke the async service to save our bean.
    *
    * @param callback used to publish our results.
    */
   protected void performAsyncOperation(final AsyncCommandCallback<Person, String> callback)
   {
      service.save(model.getValue(), asAsyncCallback(callback, exceptionManager));
   }

   public ValueModel<String> text()
   {
      return text;
   }
}

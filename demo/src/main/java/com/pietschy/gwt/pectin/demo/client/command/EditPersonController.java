package com.pietschy.gwt.pectin.demo.client.command;

import com.google.gwt.user.client.ui.HasWidgets;
import com.pietschy.gwt.pectin.client.channel.DefaultChannel;
import com.pietschy.gwt.pectin.demo.client.domain.Person;

/**
 *
 */
public class EditPersonController
{
   private EditPersonForm editPersonForm;
   private HasWidgets destination;
   private EditPersonModel model;

   public EditPersonController(final Person person, SaveServiceAsync saveService)
   {
      // create a channel to send notifications to the view.
      DefaultChannel<String> notificationChannel = new DefaultChannel<String>();

      // create our editor model
      model = new EditPersonModel();

      // now create our commands.  In this case I'm having them operate directly on the model.
      SaveCommand saveCommand = createSaveCommand(model, saveService, notificationChannel);

      // initialise the model, this could be done at any time, i.e. we don't need
      // to get the person in the constructor if we're a long lived controller.
      model.setValue(person);

      // and finally create the view passing in the model and activities.
      editPersonForm = new EditPersonForm(model, notificationChannel, saveCommand);

      // if our controller needed to hook into the activities (such as to fire an
      // and editFinished event) then we could do something like..
      //    saveCommand.onSuccessExecute(...);
   }

   private SaveCommand createSaveCommand(EditPersonModel model, SaveServiceAsync saveService, DefaultChannel<String> notificationChannel)
   {
      // if I were using GIN I'd inject the save command into the controller and have
      // a saveCommand.configure(EditPersonModel) style method.
      SaveCommand saveCommand = new SaveCommand(saveService, model);

      // Configure our events... we could have also done this in the command if we
      // wanted, just the command would need to be passed the notification channel.
      // Another option would be to use binder.show(someStatusMessage).when(saveCommand.active());
      // in our view.  Just depends on what you need.
      saveCommand.always()
         .onStartSend("Saving.... (we're just pretending, I'm using Random.nextBoolean() to fake errors.)")
         .to(notificationChannel);

      saveCommand.always()
         .onSuccessSend("Save worked.")
         .to(notificationChannel);

      // send our errors direct to the notification channel.
      saveCommand.always()
         .sendErrorTo(notificationChannel);

      return saveCommand;
   }

   public void go(HasWidgets container)
   {
      destination = container;
      destination.add(editPersonForm);
   }

   public void dispose()
   {
      destination.remove(editPersonForm);
   }
}

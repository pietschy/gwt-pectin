package com.pietschy.gwt.pectin.demo.client.command;

import com.google.gwt.user.client.ui.HasWidgets;
import com.pietschy.gwt.pectin.client.channel.DefaultChannel;
import com.pietschy.gwt.pectin.client.command.AbstractUiCommand;
import com.pietschy.gwt.pectin.client.command.UiCommand;
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

      // now create our commands.  In this case I'm having them operate directly
      // on the model, but they could operate on a Display type interface was well.
      SaveUiCommand saveCommand = new SaveUiCommand(saveService, model, notificationChannel);
      UiCommand cancelCommand = new CancelUiCommand(saveCommand);

      // initialise the model
      model.readFrom(person);

      // and finally create the form passing in the model and activities.
      editPersonForm = new EditPersonForm(model, notificationChannel, saveCommand, cancelCommand);

      // if our controller needed to hook into the activities (such as to fire an
      // and editFinished event) then we could do something like..
      //    saveCommand.onSuccessExecute(...);
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

   private class CancelUiCommand extends AbstractUiCommand
   {
      public CancelUiCommand(SaveUiCommand saveCommand)
      {
         disableWhen(saveCommand.active());
      }

      @Override
      protected void doExecute()
      {
         model.revert();
      }
   }
}

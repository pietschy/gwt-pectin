package com.pietschy.gwt.pectin.demo.client.activity;

import com.google.gwt.user.client.ui.HasWidgets;
import com.pietschy.gwt.pectin.client.activity.AbstractActivity;
import com.pietschy.gwt.pectin.client.activity.Activity;
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

      // now create the activities.  In this case I'm having them operate directly
      // on the model, but they could operate on a Display type interface was well.
      SaveActivity saveActivity = new SaveActivity(saveService, model, notificationChannel);
      Activity cancelActivity = new CancelActivity(saveActivity);

      // initialise the model
      model.readFrom(person);

      // and finally create the form passing in the model and activities.
      editPersonForm = new EditPersonForm(model, notificationChannel, saveActivity, cancelActivity);

      // if our controller needed to hook into the activities (such as to fire an
      // and editFinished event) then we could do something like..
      //    saveActivity.onSuccessExecute(...);
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

   private class CancelActivity extends AbstractActivity
   {
      public CancelActivity(SaveActivity saveActivity)
      {
         disableWhen(saveActivity.active());
      }

      public void execute()
      {
         model.revert();
      }
   }
}

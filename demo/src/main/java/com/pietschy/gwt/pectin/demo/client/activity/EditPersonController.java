package com.pietschy.gwt.pectin.demo.client.activity;

import com.google.gwt.user.client.ui.HasWidgets;
import com.pietschy.gwt.pectin.client.activity.AbstractActivity;
import com.pietschy.gwt.pectin.client.activity.Activity;
import com.pietschy.gwt.pectin.demo.client.domain.Person;

/**
 * 
 */
public class EditPersonController
{
   private EditPersonForm editPersonForm;
   private HasWidgets destination;

   public EditPersonController(final Person person, SaveServiceAsync saveService)
   {
      // create our model and initialise it from the person
      EditPersonModel editModel = new EditPersonModel();
      editModel.readFrom(person);

      // now create the activities that will be displayed on the form.
      SaveActivity saveActivity = new SaveActivity(editModel, saveService);
      Activity cancelActivity = new CancelActivity(editModel);

      // and finally create the form passing in the model and activities.
      editPersonForm = new EditPersonForm(editModel, saveActivity, cancelActivity);
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

   private static class CancelActivity extends AbstractActivity
   {
      private EditPersonModel model;

      public CancelActivity(EditPersonModel model)
      {
         this.model = model;
      }

      public void execute()
      {
         model.revert();
      }
   }
}

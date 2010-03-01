package com.pietschy.gwt.pectin.demo.client.activity;

import com.google.gwt.user.client.ui.HasWidgets;
import com.pietschy.gwt.pectin.client.activity.AbstractActivity;
import com.pietschy.gwt.pectin.client.activity.Activity;
import com.pietschy.gwt.pectin.demo.client.domain.Person;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 27, 2010
 * Time: 10:49:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class EditPersonController
{
   private EditPersonForm editPersonForm;
   private HasWidgets destination;

   public EditPersonController(final Person person, SaveServiceAsync saveService)
   {
      EditPersonModel editModel = new EditPersonModel();
      editModel.readFrom(person);

      SaveActivity saveActivity = new SaveActivity(editModel, saveService);
      Activity cancelActivity = new CancelCommand(editModel);

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

   private static class CancelCommand extends AbstractActivity
   {
      private EditPersonModel model;

      public CancelCommand(EditPersonModel model)
      {
         this.model = model;
      }

      public void execute()
      {
         model.revert();
      }
   }
}

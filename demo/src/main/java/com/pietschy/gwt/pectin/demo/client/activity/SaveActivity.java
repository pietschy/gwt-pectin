package com.pietschy.gwt.pectin.demo.client.activity;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pietschy.gwt.pectin.client.activity.AbstractAsyncActivity;
import com.pietschy.gwt.pectin.client.activity.ResultCallback;
import com.pietschy.gwt.pectin.demo.client.domain.Person;

/**
 * The save activity is responsible for validating the model, copying the bean
 * and invoking the underlying save mechanism.  On completion it publishes the
 * success and error to the appropriate channels.  The active state of the activity
 * is automatically updated.
 */
public class SaveActivity extends AbstractAsyncActivity<Person, Throwable>
{
   private SaveServiceAsync service;
   private EditPersonModel model;

   public SaveActivity(EditPersonModel model, SaveServiceAsync service)
   {
      this.model = model;
      this.service = service;
   }

   @Override
   protected boolean beforeExecute()
   {
      // we'll only continue if the model is valid.
      return model.validate();
   }

   /**
    * Invoke the async service to save our bean.
    * 
    * @param callback used to publish our results.
    */
   protected void performActivity(final ResultCallback<Person, Throwable> callback)
   {
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
      // came back from the service.
      model.readFrom(result);
   }
}

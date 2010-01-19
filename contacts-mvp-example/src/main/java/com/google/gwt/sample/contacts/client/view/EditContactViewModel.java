package com.google.gwt.sample.contacts.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.contacts.shared.Contact;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;
import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.watermark;
import com.pietschy.gwt.pectin.client.validation.ValidationManager;
import com.pietschy.gwt.pectin.client.validation.ValidationPlugin;
import static com.pietschy.gwt.pectin.client.validation.ValidationPlugin.validateField;
import com.pietschy.gwt.pectin.client.validation.validator.NotEmptyValidator;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jan 2, 2010
 * Time: 9:06:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditContactViewModel extends FormModel
{

   // create our bindings to our bean
   public abstract static class ContactProvider extends BeanModelProvider<Contact> {}

   // we're using the base type so we can use a refection variant in our JRE tests.
   private BeanModelProvider<Contact> contactProvider;

   protected final FieldModel<String> firstName;
   protected final FieldModel<String> lastName;
   protected final FieldModel<String> emailAddress;

   public EditContactViewModel()
   {
      this((ContactProvider) GWT.create(ContactProvider.class));
   }

   /**
    * This constructor is provided our testing.
    */
   protected EditContactViewModel(BeanModelProvider<Contact> contactProvider)
   {
      this.contactProvider = contactProvider;

      // create our fields and bind to our bean
      firstName = fieldOfType(String.class).boundTo(contactProvider, "firstName");
      lastName = fieldOfType(String.class).boundTo(contactProvider, "lastName");
      emailAddress = fieldOfType(String.class).boundTo(contactProvider, "emailAddress");

      // add some validation rules
      validateField(firstName).using(new NotEmptyValidator("Please enter your first name"));
      validateField(lastName).using(new NotEmptyValidator("Please enter your last name"));
      validateField(emailAddress).using(new NotEmptyValidator("Please enter your email address"));

      // add some water marks to denote required fields
      watermark(firstName, lastName, emailAddress).with("Required");
   }

   public void setContact(Contact contact)
   {
      // clear any previous validation state.
      getValidationManager().clear();
      // and update all our value models
      contactProvider.setBean(contact);
   }

   public void commit()
   {
      contactProvider.commit();
   }

   public boolean validate()
   {
      return getValidationManager().validate();
   }

   /**
    * Exposed for testing.
    */
   ValidationManager getValidationManager()
   {
      return ValidationPlugin.getValidationManager(this);
   }
}

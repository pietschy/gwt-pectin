/*
 * Copyright 2010 Andrew Pietsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.google.gwt.sample.contacts.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.contacts.shared.Contact;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;
import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.watermark;
import static com.pietschy.gwt.pectin.client.validation.ValidationPlugin.getValidationManager;
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
   private ContactProvider contactProvider = GWT.create(ContactProvider.class);

   protected final FieldModel<String> firstName;
   protected final FieldModel<String> lastName;
   protected final FieldModel<String> emailAddress;

   public EditContactViewModel()
   {
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
      getValidationManager(this).clear();
      // and update all our value models
      contactProvider.setBean(contact);
   }

   public void commit()
   {
      contactProvider.commit();
   }

   public boolean validate()
   {
      return getValidationManager(this).validate();
   }
}

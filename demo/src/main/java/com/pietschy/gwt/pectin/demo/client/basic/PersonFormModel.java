/*
 * Copyright 2009 Andrew Pietsch 
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

package com.pietschy.gwt.pectin.demo.client.basic;

import com.google.gwt.core.client.GWT;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.ListFieldModel;
import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;
import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.*;
import static com.pietschy.gwt.pectin.client.validation.ValidationPlugin.validateField;
import static com.pietschy.gwt.pectin.client.validation.ValidationPlugin.getValidationManager;
import com.pietschy.gwt.pectin.client.validation.validator.NotEmptyValidator;
import com.pietschy.gwt.pectin.client.validation.validator.NotNullValidator;

/**
 *
 */
public class PersonFormModel extends FormModel
{

   public static abstract class PersonProvider extends BeanModelProvider<Person>
   {
   }

   private PersonProvider personProvider = GWT.create(PersonProvider.class);

   private AgeFormat ageFormat = new AgeFormat();

   protected final FieldModel<String> givenName;
   protected final FieldModel<String> surname;
   protected final FieldModel<String> nickName;
   protected final FieldModel<Boolean> hasNickName;
   protected final FormattedFieldModel<Integer> age;
   protected final FieldModel<String> loginName;
   protected final FieldModel<Gender> gender;
   protected final FieldModel<Boolean> wineLover;
   protected final ListFieldModel<Wine> favoriteWines;


   public PersonFormModel()
   {

      // Create our models...
      givenName = fieldOfType(String.class).boundTo(personProvider, "givenName");
      surname = fieldOfType(String.class).boundTo(personProvider, "surname");

      hasNickName = fieldOfType(Boolean.class).createWithValue(false);
      nickName = fieldOfType(String.class).boundTo(personProvider, "nickName");

      loginName = fieldOfType(String.class)
         .computedFrom(givenName, surname)
         .using(new LoginNameGenerator());

      gender = fieldOfType(Gender.class).boundTo(personProvider, "gender");

      age = formattedFieldOfType(Integer.class)
         .using(ageFormat)
         .boundTo(personProvider, "age");

      wineLover = fieldOfType(Boolean.class).boundTo(personProvider, "wineLover");
      favoriteWines = listOfType(Wine.class).boundTo(personProvider, "favoriteWines");

      // Configure our validator rules
      validateField(givenName).using(new NotEmptyValidator("Given Name is required"));
      validateField(surname).using(new NotEmptyValidator("Surname is required"));
      // use the fomatter to provide validation at the text level.
      validateField(age).usingFieldFormat();
      validateField(age).using(new NotNullValidator("Age is required"));

      validateField(nickName)
         .using(new NotEmptyValidator("Nick name is required"))
         .when(hasNickName);

      validateField(favoriteWines).using(new WineListValidator()).when(wineLover);

      // Configure and wire up the metadata.
      enable(nickName).when(hasNickName);
      show(favoriteWines).when(wineLover);
      // manually configure the metadata for the login name.
      getMetadata(loginName).setEnabled(false);
   }

   public void commit()
   {
      personProvider.commit();
   }

   public void revert()
   {
      personProvider.revert();
   }

   public void setPerson(Person person)
   {
      personProvider.setBean(person);
   }

   public boolean validate()
   {
      return getValidationManager(this).validate();
   }

}

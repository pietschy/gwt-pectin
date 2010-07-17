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

package com.pietschy.gwt.pectin.demo.client.command;

import com.google.gwt.core.client.GWT;
import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;
import com.pietschy.gwt.pectin.client.form.FieldModel;
import com.pietschy.gwt.pectin.client.form.FormModel;
import com.pietschy.gwt.pectin.client.form.ListFieldModel;
import com.pietschy.gwt.pectin.client.form.validation.validator.NoEmptyElementsValidator;
import com.pietschy.gwt.pectin.client.form.validation.validator.NotEmptyValidator;
import com.pietschy.gwt.pectin.client.form.validation.validator.NotNullValidator;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.pietschy.gwt.pectin.client.value.ValueTarget;
import com.pietschy.gwt.pectin.demo.client.domain.Gender;
import com.pietschy.gwt.pectin.demo.client.domain.Person;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;

import static com.pietschy.gwt.pectin.client.form.validation.ValidationPlugin.getValidationManager;
import static com.pietschy.gwt.pectin.client.form.validation.ValidationPlugin.validateField;

/**
 *
 */
public class EditPersonModel extends FormModel implements ValueTarget<Person>
{
   public static abstract class PersonProvider extends BeanModelProvider<Person>{}
   private PersonProvider personProvider = GWT.create(PersonProvider.class);

   protected final FieldModel<String> givenName;
   protected final FieldModel<String> surname;
   protected final FieldModel<Gender> gender;
   protected final ListFieldModel<Wine> favoriteWines;
   protected final ListFieldModel<String> favoriteCheeses;

   protected final ValueModel<Boolean> dirty;

   public EditPersonModel()
   {
      // Create our models...
      givenName = fieldOfType(String.class).boundTo(personProvider, "givenName");
      surname = fieldOfType(String.class).boundTo(personProvider, "surname");
      gender = fieldOfType(Gender.class).boundTo(personProvider, "gender");

      // some list fields
      favoriteWines = listOfType(Wine.class).boundTo(personProvider, "favoriteWines");
      favoriteCheeses = listOfType(String.class).boundTo(personProvider, "favoriteCheeses");

      validateField(givenName).using(new NotEmptyValidator("Please enter your first name"));
      validateField(surname).using(new NotEmptyValidator("Please enter your surname"));
      validateField(gender).using(new NotNullValidator("Please specify your gender"));
      validateField(favoriteCheeses).using(new NoEmptyElementsValidator(true)
      {
         @Override
         protected String getErrorTextForIndex(int index)
         {
            return "Please remove any empty cheeses";
         }
      });

      dirty = personProvider.getDirtyModel();
   }


   public Person getValue()
   {
      return personProvider.getValue();
   }

   public void setValue(Person person)
   {
      personProvider.setValue(person);
   }

   public void commit(boolean checkpoint)
   {
      personProvider.commit(checkpoint);
   }

   public void checkpoint()
   {
      personProvider.checkpoint();
   }


   public void revert()
   {
      personProvider.revert();
   }


   public boolean validate()
   {
      return getValidationManager(this).validate();
   }


}
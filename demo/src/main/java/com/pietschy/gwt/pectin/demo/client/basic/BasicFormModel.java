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
import com.pietschy.gwt.pectin.client.bean.BeanPropertyValueModel;
import static com.pietschy.gwt.pectin.client.validation.ValidationPlugin.validateField;
import com.pietschy.gwt.pectin.demo.client.domain.Gender;
import com.pietschy.gwt.pectin.demo.client.domain.Person;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;
import com.pietschy.gwt.pectin.demo.client.misc.LoginNameGenerator;
import com.pietschy.gwt.pectin.demo.client.misc.AgeFormat;

/**
 *
 */
public class BasicFormModel extends FormModel
{

   public static abstract class PersonProvider extends BeanModelProvider<Person> {}

   private PersonProvider personProvider = GWT.create(PersonProvider.class);

   private AgeFormat ageFormat = new AgeFormat();

   protected final FieldModel<String> givenName;
   protected final FieldModel<String> surname;
   protected final FormattedFieldModel<Integer> age;
   protected final FieldModel<String> loginName;
   protected final FieldModel<Gender> gender;
   protected final ListFieldModel<Wine> favoriteWines;

   public BasicFormModel()
   {
      // Create our models...
      givenName = fieldOfType(String.class).boundTo(personProvider, "givenName");
      surname = fieldOfType(String.class).boundTo(personProvider, "surname");
      gender = fieldOfType(Gender.class).boundTo(personProvider, "gender");

      // a formatted field.
      age = formattedFieldOfType(Integer.class)
         .using(ageFormat)
         .boundTo(personProvider, "age");

      // a list field
      favoriteWines = listOfType(Wine.class).boundTo(personProvider, "favoriteWines");
      
      // a computed field
      loginName = fieldOfType(String.class)
         .computedFrom(givenName, surname)
         .using(new LoginNameGenerator());
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
}

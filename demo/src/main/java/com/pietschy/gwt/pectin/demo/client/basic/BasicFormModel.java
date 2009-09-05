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
import com.pietschy.gwt.pectin.client.value.Converter;
import com.pietschy.gwt.pectin.client.value.Function;
import com.pietschy.gwt.pectin.demo.client.domain.Gender;
import com.pietschy.gwt.pectin.demo.client.domain.Person;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;
import com.pietschy.gwt.pectin.demo.client.misc.AgeFormat;

import java.util.List;

/**
 *
 */
public class BasicFormModel extends FormModel
{

   public static abstract class PersonProvider extends BeanModelProvider<Person> {}

   private PersonProvider personProvider = GWT.create(PersonProvider.class);

   protected final FieldModel<String> givenName;
   protected final FieldModel<String> surname;
   protected final FieldModel<Integer> lettersInName;
   protected final FormattedFieldModel<Integer> age;
   protected final FormattedFieldModel<Integer> ageInDogYears;
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
         .using(new AgeFormat())
         .boundTo(personProvider, "age");

      // a list field
      favoriteWines = listOfType(Wine.class).boundTo(personProvider, "favoriteWines");
      
      // a computed field
      lettersInName = fieldOfType(Integer.class)
         .computedFrom(givenName, surname)
         .using(new CharacterCounter());
      
      // a converted field (that is also a formatted field)
      ageInDogYears = formattedFieldOfType(Integer.class).using(new AgeFormat())
         .convertedFrom(age).using(new DogYearsConverter());
      
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
   
   public Person getPerson()
   {
      return personProvider.getBean();
   }

   private static class CharacterCounter implements Function<Integer, String>
   {
      public Integer compute(List<String> source)
      {
         int total = 0;
         for (String name : source)
         {
            if (name != null)
            {
               // I'm ignoring the fact that there could be
               // whitespace in the name..
               total += name.trim().length();
            }
         }
         
         return total;
      }
   }

   private static class DogYearsConverter implements Converter<Integer, Integer>
   {
      private static final int DOG_AGE_MULTIPLE = 7;

      public Integer fromSource(Integer value)
      {
         return value != null ? DOG_AGE_MULTIPLE * value : null;
      }

      public Integer toSource(Integer value)
      {
         return value != null ? (int) ((double) value / DOG_AGE_MULTIPLE) : null;
      }
   }
}

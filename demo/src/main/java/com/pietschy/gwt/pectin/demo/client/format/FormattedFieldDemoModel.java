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

package com.pietschy.gwt.pectin.demo.client.format;

import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.FormattedListFieldModel;
import com.pietschy.gwt.pectin.client.format.IntegerFormat;
import com.pietschy.gwt.pectin.client.value.Converter;
import com.pietschy.gwt.pectin.demo.client.misc.AgeFormat;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Nov 21, 2009
 * Time: 8:32:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldDemoModel extends FormModel
{
   protected final FormattedFieldModel<Integer> age;
   protected final FormattedFieldModel<Integer> ageInDogYears;
   protected final FormattedListFieldModel<Integer> formattedList;

   public FormattedFieldDemoModel()
   {
      // a formatted field.
      age = formattedFieldOfType(Integer.class)
         .using(new AgeFormat())
         .create();

      // a converted field (that is also a formatted field)
      ageInDogYears = formattedFieldOfType(Integer.class)
         .using(new AgeFormat())
         .convertedFrom(age)
         .using(new DogYearsConverter());

      // a formatted list with a base type of Integer
      formattedList = formattedListOfType(Integer.class)
         .using(new IntegerFormat())
         .create();
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

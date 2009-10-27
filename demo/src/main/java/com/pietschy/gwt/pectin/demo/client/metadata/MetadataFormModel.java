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

package com.pietschy.gwt.pectin.demo.client.metadata;

import com.google.gwt.core.client.GWT;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.ListFieldModel;
import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;
import static com.pietschy.gwt.pectin.client.condition.Conditions.and;
import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.enable;
import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.watermark;
import com.pietschy.gwt.pectin.demo.client.domain.Person;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;
import com.pietschy.gwt.pectin.demo.client.misc.AgeFormat;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Aug 10, 2009
 * Time: 4:25:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetadataFormModel extends FormModel
{
   protected final FieldModel<String> givenName;
   protected final FieldModel<String> surname;
   protected final FormattedFieldModel<Integer> age;
   protected final FieldModel<Boolean> editWatermark;
   protected final FieldModel<String> ageWaterMark;
   protected final FieldModel<String> nickName;
   protected final FieldModel<Boolean> hasNickName;
   protected final FieldModel<Boolean> wineLover;
   protected final ListFieldModel<Wine> favoriteWines;
   protected final FieldModel<Boolean> hasFavoriteWines;




   public static abstract class PersonProvider extends BeanModelProvider<Person> {}
   private PersonProvider personProvider = GWT.create(PersonProvider.class);
   
   
   public MetadataFormModel()
   {
      // Create our field models..
      givenName = fieldOfType(String.class).boundTo(personProvider, "givenName");
      surname = fieldOfType(String.class).boundTo(personProvider, "surname");
      // a formatted field.
      age = formattedFieldOfType(Integer.class)
         .using(new AgeFormat())
         .boundTo(personProvider, "age");

      hasNickName = fieldOfType(Boolean.class).createWithValue(false);
      nickName = fieldOfType(String.class).boundTo(personProvider, "nickName");
      wineLover = fieldOfType(Boolean.class).boundTo(personProvider, "wineLover");
      hasFavoriteWines = fieldOfType(Boolean.class).createWithValue(false);
      favoriteWines = listOfType(Wine.class).boundTo(personProvider, "favoriteWines");

      
      // Configure the metadata for the various models.
      // first some watermarks..
      watermark(givenName).with("Enter your first name");
      watermark(surname).with("Enter your last name");

      // we can also use other value models for the watermark, you can use any old
      // ValueModel<String>, were using a field so we can add it to the UI..
      ageWaterMark= fieldOfType(String.class).createWithValue("Enter your age");
      editWatermark = fieldOfType(Boolean.class).createWithValue(false);
      watermark(age).using(ageWaterMark);

      // and now our enabled-ness stuff.
      enable(nickName).when(hasNickName);
      enable(hasFavoriteWines).when(wineLover);
      enable(favoriteWines).when(and(wineLover, hasFavoriteWines));


   }
   
   public void setPerson(Person person)
   {
      personProvider.setBean(person);
   }
   
}

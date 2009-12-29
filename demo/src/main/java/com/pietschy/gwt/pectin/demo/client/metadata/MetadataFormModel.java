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
import static com.pietschy.gwt.pectin.client.condition.Conditions.is;
import static com.pietschy.gwt.pectin.client.condition.Conditions.valueOf;
import com.pietschy.gwt.pectin.client.format.IntegerFormat;
import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.*;
import com.pietschy.gwt.pectin.client.value.Function;
import com.pietschy.gwt.pectin.demo.client.domain.Person;
import com.pietschy.gwt.pectin.demo.client.domain.Protocol;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;


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
   protected final FieldModel<String> nickName;
   protected final FieldModel<Boolean> hasNickName;
   protected final FieldModel<Boolean> wineLover;
   protected final ListFieldModel<Wine> favoriteWines;
   protected final FieldModel<Boolean> hasFavoriteWines;

   protected final FieldModel<Protocol> protocol;
   protected final FormattedFieldModel<Integer> port;
   protected final FieldModel<Integer> defaultPort;
   protected final FieldModel<String> portWatermark;


   public static abstract class PersonProvider extends BeanModelProvider<Person> {}
   private PersonProvider personProvider = GWT.create(PersonProvider.class);
   
   
   public MetadataFormModel()
   {
      // Create our field models..
      givenName = fieldOfType(String.class).boundTo(personProvider, "givenName");
      surname = fieldOfType(String.class).boundTo(personProvider, "surname");

      // now the static watermarks
      watermark(givenName).with("Enter your first name");
      watermark(surname).with("Enter your last name");


      // now create our protocol fields..
      protocol = fieldOfType(Protocol.class).createWithValue(Protocol.FTP);
      port = formattedFieldOfType(Integer.class).using(new IntegerFormat()).create();

      // the default port field is computed from the currently selected protocol.
      defaultPort = fieldOfType(Integer.class).computedFrom(protocol).using(new DefaultPortExtractor());

      // now we create a value model to compute the port watermark.
      portWatermark = fieldOfType(String.class).computedFrom(defaultPort).using(new ToStringFunction());

      // now we've created all our values we configure our metadata.
      enable(port).when(valueOf(protocol).isNotNull());

      // and we use the computed value model for the port watermark.
      watermark(port).with(portWatermark);

      // only display the default port if the user has entered a non null value that isn't the default.
      hide(defaultPort).when(valueOf(port).isNull().or(valueOf(port).isSameAs(defaultPort)));


      // now configure our nick name
      hasNickName = fieldOfType(Boolean.class).createWithValue(false);
      nickName = fieldOfType(String.class).boundTo(personProvider, "nickName");

      enable(nickName).when(hasNickName);


      // and our wine lists
      wineLover = fieldOfType(Boolean.class).boundTo(personProvider, "wineLover");
      hasFavoriteWines = fieldOfType(Boolean.class).createWithValue(false);
      favoriteWines = listOfType(Wine.class).boundTo(personProvider, "favoriteWines");

      enable(hasFavoriteWines).when(wineLover);
      enable(favoriteWines).when(is(wineLover).and(hasFavoriteWines));
   }
   
   public void setPerson(Person person)
   {
      personProvider.setBean(person);
   }

   private static class DefaultPortExtractor implements Function<Integer, Protocol>
   {
      public Integer compute(Protocol source)
      {
         return source != null ? source.getDefaultPort() : null;
      }
   }

   private static class ToStringFunction implements Function<String, Object>
   {
      public String compute(Object value)
      {
         return value != null ? value.toString() : null;
      }
   }
}

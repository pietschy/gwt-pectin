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

import com.google.gwt.user.client.ui.*;
import com.pietschy.gwt.pectin.client.binding.FormBinder;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.components.AbstractDynamicList;
import com.pietschy.gwt.pectin.client.components.ComboBox;
import com.pietschy.gwt.pectin.client.components.EnhancedTextBox;
import com.pietschy.gwt.pectin.client.components.NullSafeCheckBox;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;
import com.pietschy.gwt.pectin.demo.client.domain.Protocol;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;
import com.pietschy.gwt.pectin.demo.client.misc.NickNameEditor;
import com.pietschy.gwt.pectin.demo.client.misc.VerySimpleForm;

import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.metadataOf;

/**
 *
 */
public class MetadataForm extends VerySimpleForm
{
   private TextBox givenName = new TextBox();
   private TextBox surname = new TextBox();
   private PasswordTextBox password = new PasswordTextBox();
   private TextBox passwordClear = new TextBox();
   private CheckBox showPassword = new CheckBox("Show Password");

   private ComboBox<Protocol> protocol = new ComboBox<Protocol>(Protocol.values());
   private TextBox port = new EnhancedTextBox();
   private Label defaultPortLabel = createHint();

   private CheckBox hasNickName = new CheckBox("I have a nick name");
   // NickNameEditor is an example of a custom HasValue<T> widget.
   private NickNameEditor nickName = new NickNameEditor();

   // value models can contain nulls so if we need a null safe check
   // box if we're binding directly to one.
   private CheckBox wineLover = new NullSafeCheckBox("I like wine");
   private CheckBox hasFavoriteWines = new NullSafeCheckBox("I have favorite wines");
   // the containingValue and withValue bindings never use nulls so we
   // can use regular check boxes.
   private CheckBox cabSavCheckBox = new CheckBox("Cab Sav");
   private CheckBox merlotCheckBox = new CheckBox("Merlot");
   private CheckBox shirazCheckBox = new CheckBox("Shiraz");

   private CheckBox cheeseLover = new NullSafeCheckBox("I like cheese");
   private AbstractDynamicList<String> favoriteCheeses = new AbstractDynamicList<String>("Add Cheese")
   {
      protected HasValue<String> createWidget()
      {
         return new TextBox();
      }
   };


   private FormBinder binder = new WidgetBinder();

   public MetadataForm(MetadataFormModel model)
   {
      protocol.setRenderer(new ProtocolRenderer());
      port.setVisibleLength(5);

      binder.bind(model.givenName).to(givenName);
      binder.bind(model.surname).to(surname);

      binder.bind(model.password).to(password);
      binder.bind(model.password).to(passwordClear);
      binder.bind(model.revealPassword).to(showPassword);

      // now we'll flip two fields based on the state of the model.
      binder.hide(password).when(model.revealPassword);
      binder.show(passwordClear).when(model.revealPassword);

      binder.bind(model.protocol).to(protocol);
      binder.bind(model.port).to(port);
      binder.bind(model.defaultPort).toLabel(defaultPortLabel).withFormat(new DisplayFormat<Integer>()
      {
         public String format(Integer port)
         {
            return port != null ? "(the default is " + port + ")" : "";
         }
      });

      binder.bind(model.hasNickName).to(hasNickName);
      binder.bind(model.nickName).to(nickName);

      binder.bind(model.wineLover).to(wineLover);
      binder.bind(model.hasFavoriteWines).to(hasFavoriteWines);

      binder.bind(model.favoriteWines).containingValue(Wine.CAB_SAV).to(cabSavCheckBox);
      binder.bind(model.favoriteWines).containingValue(Wine.MERLOT).to(merlotCheckBox);
      binder.bind(model.favoriteWines).containingValue(Wine.SHIRAZ).to(shirazCheckBox);

      binder.bind(model.cheeseLover).to(cheeseLover);
      binder.bind(model.favoriteCheeses).to(favoriteCheeses);


      addRow("First Name", givenName, "The first two fields use a plain text watermark");
      addRow("Last Name", surname);
      // put both password and passwordClear in the same row as we always show one
      // and hide the other.
      addRow("Password", hpWithNoGap(password, passwordClear), "And here we show & hide widgets based on the state of the checkbox.");
      addRow("", showPassword);

      addGap();
      addNote("The following shows a dynamically changing watermark for the port field " +
              "based on the selected protocol.");
      addNote("The default port is also displayed if the user enters a port value that isn't the default.");
      addRow("Protocol", protocol);
      addRow("Port", port, defaultPortLabel);

      addGap();
      addRow("", hasNickName);
      addRow("Nick name", nickName);
      addGap();
      addNote("The favorites list is only enabled only if you're a wine lover with favorites.");
      addRow("Wine", wineLover);
      addRow("", hasFavoriteWines);
      addRow("Favorites", cabSavCheckBox, merlotCheckBox, shirazCheckBox);

      addGap();
      addNote("Now we'll show and hide a field based on the checkbox value.");
      addRow("Cheese", cheeseLover);
      Row favoriteCheeseRow = addTallRow("Favorites", favoriteCheeses);

      // Now lets hide the whole row based on the metadata for the field.
      // Please Note: Normally if I was hiding the whole row like above I'd probably
      // use binder.show(favoriteCheeseRow).when(cheeseLover) and not bother using the
      // metadata at all.
      binder.show(favoriteCheeseRow).when(metadataOf(model.favoriteCheeses).isVisible());
   }

   private static class ProtocolRenderer implements ComboBox.Renderer<Protocol>
   {
      public String toDisplayString(Protocol protocol)
      {
         return protocol != null ? protocol.getDisplayName() : "";
      }
   }
}
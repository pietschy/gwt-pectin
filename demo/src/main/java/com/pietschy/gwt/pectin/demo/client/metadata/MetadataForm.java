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

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.components.EnhancedTextBox;
import com.pietschy.gwt.pectin.client.components.NullSafeCheckBox;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBinder;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;
import com.pietschy.gwt.pectin.demo.client.misc.NickNameEditor;
import com.pietschy.gwt.pectin.demo.client.misc.VerySimpleForm;

/**
 * 
 */
public class MetadataForm extends VerySimpleForm
{
   private TextBox givenName = new TextBox();
   private TextBox surname = new TextBox();
   private TextBox age = new TextBox();
   private CheckBox editAgeWatermark = new CheckBox("Edit watermark");
   private TextBox ageWaterMark = new EnhancedTextBox();

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
   
   private WidgetBinder widgets = new WidgetBinder();
   private MetadataBinder metadata = new MetadataBinder();


   public MetadataForm(MetadataFormModel model)
   {
      widgets.bind(model.givenName).to(givenName);
      widgets.bind(model.surname).to(surname);
      widgets.bind(model.age).to(age);
      widgets.bind(model.editingAgeWatermark).to(editAgeWatermark);
      widgets.bind(model.ageWaterMark).to(ageWaterMark);

      widgets.bind(model.hasNickName).to(hasNickName);
      widgets.bind(model.nickName).to(nickName);
      
      widgets.bind(model.wineLover).to(wineLover);
      widgets.bind(model.hasFavoriteWines).to(hasFavoriteWines);

      widgets.bind(model.favoriteWines).containingValue(Wine.CAB_SAV).to(cabSavCheckBox);
      widgets.bind(model.favoriteWines).containingValue(Wine.MERLOT).to(merlotCheckBox);
      widgets.bind(model.favoriteWines).containingValue(Wine.SHIRAZ).to(shirazCheckBox);


      addRow("First Name", givenName, "The first two fields use a plain text watermark");
      addRow("Last Name", surname);

      addRow("Age", age, editAgeWatermark);

      // Here we'll show and hide the whole row based on the visibility on whether
      // we in edit mode.
      Row watermarkRow = addRow("Age Watermark", ageWaterMark, "You can change the Age watermark here");
      metadata.bindValueOf(model.editingAgeWatermark).toVisibilityOf(watermarkRow);

      addGap();
      addRow("", hasNickName);
      addRow("Nick name", nickName);
      addGap();
      addRow("", wineLover);
      addRow("", hasFavoriteWines);
      addRow("Favorite Wines", cabSavCheckBox, merlotCheckBox, shirazCheckBox);
   }

}
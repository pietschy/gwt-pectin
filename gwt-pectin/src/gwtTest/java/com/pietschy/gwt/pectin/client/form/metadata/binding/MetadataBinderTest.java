package com.pietschy.gwt.pectin.client.form.metadata.binding;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Button;
import com.pietschy.gwt.pectin.client.form.FieldModel;
import com.pietschy.gwt.pectin.client.form.FormModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;

import static com.pietschy.gwt.pectin.client.form.metadata.MetadataPlugin.enable;
import static com.pietschy.gwt.pectin.client.form.metadata.MetadataPlugin.show;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 24, 2010
 * Time: 3:17:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetadataBinderTest extends GWTTestCase
{
   private ValueHolder<Boolean> condition;
   private ValueHolder<Boolean> fieldVisible;
   private ValueHolder<Boolean> fieldEnabled;
   private FormModel form;
   private FieldModel<String> field;

   private Button button;
   private MetadataBinder binder;

   @Override
   protected void gwtSetUp() throws Exception
   {
      binder = new MetadataBinder();

      condition = new ValueHolder<Boolean>(false);
      fieldVisible = new ValueHolder<Boolean>(false);
      fieldEnabled = new ValueHolder<Boolean>(false);

      form = new FormModel();
      field = form.fieldOfType(String.class).create();
      enable(field).when(fieldEnabled);
      show(field).when(fieldVisible);

      button = new Button("test");
   }

   @Override
   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   public void testShowWhen() throws Exception
   {
      binder.show(button).when(condition);

      assertEquals("showWhen-a", condition, button.isVisible());

      condition.setValue(!condition.getValue());

      assertEquals("showWhen-b", condition, button.isVisible());
   }

   public void testHideWhen() throws Exception
   {
      binder.hide(button).when(condition);

      assertEquals("hideWhen-a", condition, !button.isVisible());

      condition.setValue(!condition.getValue());

      assertEquals("hideWhen-b", condition, !button.isVisible());
   }

   public void testShowUsingMetadataOf() throws Exception
   {
      binder.show(button).usingMetadataOf(field);

      assertEquals("showUsingMetadataOf-a", fieldVisible, button.isVisible());

      fieldVisible.setValue(!fieldVisible.getValue());

      assertEquals("showUsingMetadataOf-b", fieldVisible, button.isVisible());
   }

   public void testHideUsingMetadataOf() throws Exception
   {
      binder.hide(button).usingMetadataOf(field);

      assertEquals("hideUsingMetadataOf-a", fieldVisible, button.isVisible());

      fieldVisible.setValue(!fieldVisible.getValue());

      assertEquals("hideUsingMetadataOf-b", fieldVisible, button.isVisible());

   }

   public void testEnableWhen() throws Exception
   {
      binder.enable(button).when(condition);

      assertEquals("enableWhen-a", condition, button.isEnabled());

      condition.setValue(!condition.getValue());

      assertEquals("enableWhen-b", condition, button.isEnabled());
   }

   public void testDisableWhen() throws Exception
   {
      binder.disable(button).when(condition);

      assertEquals("disableWhen-a", condition, !button.isEnabled());

      condition.setValue(!condition.getValue());

      assertEquals("disableWhen-b", condition, !button.isEnabled());
   }

   public void testEnableUsingMetadataOf() throws Exception
   {
      binder.enable(button).usingMetadataOf(field);

      assertEquals("enableUsingMetadataOf-a", fieldEnabled, button.isEnabled());

      fieldEnabled.setValue(!fieldEnabled.getValue());

      assertEquals("enableUsingMetadataOf-b", fieldEnabled, button.isEnabled());
   }

   public void testDisableUsingMetadataOf() throws Exception
   {
      binder.disable(button).usingMetadataOf(field);

      assertEquals("disableUsingMetadataOf-a",fieldEnabled, button.isEnabled());

      fieldEnabled.setValue(!fieldEnabled.getValue());

      assertEquals("disableUsingMetadataOf-b", fieldEnabled, button.isEnabled());
   }

   private void assertEquals(String message, ValueHolder<Boolean> condition, boolean value)
   {
      assertEquals(message, (boolean) condition.getValue(), value);
   }

}

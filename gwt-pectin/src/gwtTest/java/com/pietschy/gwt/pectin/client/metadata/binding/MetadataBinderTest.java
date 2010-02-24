package com.pietschy.gwt.pectin.client.metadata.binding;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Button;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;

import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.enable;
import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.show;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 24, 2010
 * Time: 3:17:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetadataBinderTest extends GWTTestCase
{
   protected ValueHolder<Boolean> whenCondition;
   protected ValueHolder<Boolean> fieldVisible;
   private ValueHolder<Boolean> fieldEnabled;
   protected FormModel form;
   protected FieldModel<String> field;

   protected Button button;
   protected MetadataBinder binder;

   @Override
   protected void gwtSetUp() throws Exception
   {
      binder = new MetadataBinder();

      whenCondition = new ValueHolder<Boolean>(false);
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
      binder.show(button).when(whenCondition);

      assertEquals(whenCondition, button.isVisible());

      whenCondition.setValue(!whenCondition.getValue());

      assertEquals(whenCondition, button.isVisible());
   }

   public void testHideWhen() throws Exception
   {
      binder.show(button).when(whenCondition);

      assertEquals(whenCondition, !button.isVisible());

      whenCondition.setValue(!whenCondition.getValue());

      assertEquals(whenCondition, !button.isVisible());
   }

   public void testShowAlongWith() throws Exception
   {
      binder.show(button).usingMetadataOf(field);

      assertEquals(fieldVisible, button.isVisible());

      fieldVisible.setValue(!fieldVisible.getValue());

      assertEquals(fieldVisible, button.isVisible());
   }

   public void testHideAlongWith() throws Exception
   {
      binder.hide(button).usingMetadataOf(field);

      assertEquals(fieldVisible, button.isVisible());

      fieldVisible.setValue(!fieldVisible.getValue());

      assertEquals(fieldVisible, button.isVisible());

   }

   public void testEnableWhen() throws Exception
   {
      binder.enable(button).when(whenCondition);

      assertEquals(whenCondition, button.isEnabled());

      whenCondition.setValue(!whenCondition.getValue());

      assertEquals(whenCondition, button.isEnabled());
   }

   public void testDisableWhen() throws Exception
   {
      binder.disable(button).when(whenCondition);

      assertEquals(whenCondition, !button.isEnabled());

      whenCondition.setValue(!whenCondition.getValue());

      assertEquals(whenCondition, !button.isEnabled());
   }

   public void testEnableAlongWith() throws Exception
   {
      binder.enable(button).usingMetadataOf(field);

      assertEquals(fieldEnabled, button.isVisible());

      fieldEnabled.setValue(!fieldEnabled.getValue());

      assertEquals(fieldEnabled, button.isVisible());
   }

   public void testDisableAlongWith() throws Exception
   {
      binder.disable(button).usingMetadataOf(field);

      assertEquals(fieldEnabled, button.isVisible());

      fieldEnabled.setValue(!fieldEnabled.getValue());

      assertEquals(fieldEnabled, button.isVisible());
   }

   private void assertEquals(ValueHolder<Boolean> condition, boolean value)
   {
      assertEquals((boolean) condition.getValue(), value);
   }

}

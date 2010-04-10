package com.pietschy.gwt.pectin.client.binding;

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
public class WidgetBinderMetadataTest extends GWTTestCase
{
   private ValueHolder<Boolean> condition;
   private ValueHolder<Boolean> fieldVisible;
   private ValueHolder<Boolean> fieldEnabled;
   private FormModel form;
   private FieldModel<String> field;

   private Button button;
   private WidgetBinder binder;

   @Override
   protected void gwtSetUp() throws Exception
   {
      binder = new WidgetBinder();

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

   private void assertEquals(String message, ValueHolder<Boolean> condition, boolean value)
   {
      assertEquals(message, (boolean) condition.getValue(), value);
   }

}

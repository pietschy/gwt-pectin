package com.pietschy.gwt.pectin.client.form.metadata;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.pietschy.gwt.pectin.client.form.metadata.binding.TextBoxWatermarkable;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 28, 2010
 * Time: 9:34:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class MetadataManagerTest  extends GWTTestCase
{

   @Override
   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   public void testPrepareWatermarkableWithWatermarkable()
   {
      MetadataManager m = new MetadataManager();
      Watermarkable watermarkable = new TestWatermarkable();

      assertEquals(m.prepareWatermarkable(watermarkable), watermarkable);
   }

   public void testPrepareWatermarkableWithTextBox()
   {
      MetadataManager m = new MetadataManager();

      assertTrue(m.prepareWatermarkable(new TextBox()) instanceof TextBoxWatermarkable);
   }

   public void testPrepareWatermarkableWithUnknown()
   {
      MetadataManager m = new MetadataManager();

      assertNull(m.prepareWatermarkable(new Button()));
   }

   private static class TestWatermarkable implements Watermarkable
   {
      public void applyWatermarkStyle()
      {
      }

      public void removeWatermarkStyle()
      {
      }

      public String getValue()
      {
         return null;
      }

      public void setValue(String value)
      {
      }

      public HandlerRegistration addBlurHandler(BlurHandler blurHandler)
      {
         return null;
      }

      public HandlerRegistration addFocusHandler(FocusHandler focusHandler)
      {
         return null;
      }

      public void setValue(String value, boolean fireEvents)
      {

      }

      public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> stringValueChangeHandler)
      {
         return null;
      }

      public void fireEvent(GwtEvent<?> gwtEvent)
      {
      }
   }
}

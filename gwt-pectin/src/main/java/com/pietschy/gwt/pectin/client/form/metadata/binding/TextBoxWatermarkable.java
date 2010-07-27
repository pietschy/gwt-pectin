package com.pietschy.gwt.pectin.client.form.metadata.binding;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;
import com.pietschy.gwt.pectin.client.form.metadata.MetadataPlugin;
import com.pietschy.gwt.pectin.client.form.metadata.Watermarkable;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 27, 2010
* Time: 5:41:24 PM
* To change this template use File | Settings | File Templates.
*/
public class TextBoxWatermarkable implements Watermarkable
{
   private final TextBox widget;

   public TextBoxWatermarkable(TextBox widget)
   {
      this.widget = widget;
   }

   public String getValue()
   {
      return widget.getValue();
   }

   public void setValue(String value)
   {
      widget.setValue(value);
   }

   public void setValue(String value, boolean fireEvents)
   {
      widget.setValue(value, fireEvents);
   }

   public void applyWatermarkStyle()
   {
      widget.addStyleName(MetadataPlugin.DEFAULT_WATERMARK_STYLE);
   }

   public void removeWatermarkStyle()
   {
      widget.removeStyleName(MetadataPlugin.DEFAULT_WATERMARK_STYLE);
   }

   public HandlerRegistration addBlurHandler(BlurHandler blurHandler)
   {
      return widget.addBlurHandler(blurHandler);
   }

   public HandlerRegistration addFocusHandler(FocusHandler focusHandler)
   {
      return widget.addFocusHandler(focusHandler);
   }

   public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler)
   {
      return widget.addValueChangeHandler(handler);
   }

   public void fireEvent(GwtEvent<?> gwtEvent)
   {
      widget.fireEvent(gwtEvent);
   }
}

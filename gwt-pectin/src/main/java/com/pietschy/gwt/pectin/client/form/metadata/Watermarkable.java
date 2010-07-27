package com.pietschy.gwt.pectin.client.form.metadata;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.user.client.ui.HasValue;

/**
 * This interface allows arbitrary components to be watermarked.
 */
public interface Watermarkable extends HasValue<String>, HasAllFocusHandlers
{
   void applyWatermarkStyle();
   void removeWatermarkStyle();
}

package com.pietschy.gwt.pectin.client.style;


import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Button;
import com.pietschy.gwt.pectin.client.value.ValueHolder;


/**
 * StyleBinder Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created November 6, 2009
 * @since   1.0
 */
public class StyleBinderTest extends GWTTestCase
{

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.Pectin";
   }

   public void testStyle()
   {
      Button b1 = new Button("b1");
      Button b2 = new Button("b1");
      Button b3 = new Button("b1");

      ValueHolder<Boolean> trigger = new ValueHolder<Boolean>(true);
      StyleBinder style = new StyleBinder();

      String styleName = "blah";

      // start of with no style
      assertFalse(b1.getStyleName().contains(styleName));
      assertFalse(b2.getStyleName().contains(styleName));
      assertFalse(b3.getStyleName().contains(styleName));

      // after binding style should reflect trigger
      style.style(b1, b2, b3).with(styleName).when(trigger);
      assertTrue(b1.getStyleName().contains(styleName));
      assertTrue(b2.getStyleName().contains(styleName));
      assertTrue(b3.getStyleName().contains(styleName));

      // changing the trigger should update the style.
      trigger.setValue(false);
      assertFalse(b1.getStyleName().contains(styleName));
      assertFalse(b2.getStyleName().contains(styleName));
      assertFalse(b3.getStyleName().contains(styleName));
   }
   
}

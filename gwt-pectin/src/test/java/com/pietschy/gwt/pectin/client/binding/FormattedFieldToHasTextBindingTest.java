package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;
import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.format.FormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 22, 2010
 * Time: 3:05:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldToHasTextBindingTest
{
   private FormModel form;
   private FormattedFieldModel<Integer> field;
   private HasText hasText;
   private FormattedFieldToHasTextBinding<Integer> binding;
   private Format<Integer> mockFormat;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      mockFormat = (Format<Integer>) mock(Format.class);
      hasText = mock(HasText.class);
      form = new FormModel();
      field = form.formattedFieldOfType(Integer.class)
         .using(mockFormat)
         .create();
      binding = new FormattedFieldToHasTextBinding<Integer>(field, hasText);
   }

   @Test
   public void formatUsesModelsFormatAsDefault()
   {
      final String defaultValue = "I'm the default";
      final String userValue = "the user value";

      when(mockFormat.format(42)).thenReturn(defaultValue);

      Assert.assertEquals(binding.format(42), defaultValue);

      binding.setFormat(new DisplayFormat<Integer>()
      {
         public String format(Integer value)
         {
            return userValue;
         }
      });

      Assert.assertEquals(binding.format(42), userValue);
   }

   @Test
   public void formatChangesUpdateWidget()
   {
      final String firstValue = "I'm the default";
      final String secondValue = "the user value";

      field.setFormat(new SillyFormat(firstValue));
      field.setFormat(new SillyFormat(secondValue));

      verify(hasText).setText(eq(firstValue));
      verify(hasText).setText(eq(secondValue));
   }


   private static class SillyFormat implements Format<Integer>
   {
      private final String defaultValue;

      public SillyFormat(String defaultValue)
      {
         this.defaultValue = defaultValue;
      }

      public Integer parse(String text) throws FormatException
      {
         return null;
      }

      public String format(Integer value)
      {
         return defaultValue;
      }
   }
}

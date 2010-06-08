package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.FormattedListFieldModel;
import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.format.FormatException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 8, 2010
 * Time: 3:00:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedListFieldToHasTextBindingTest
{
   private FormModel form;
   private FormattedListFieldModel<Integer> field;
   private HasText hasText;
   private FormattedListFieldToHasTextBinding<Integer> binding;
   private Format<Integer> mockFormat;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      mockFormat = (Format<Integer>) mock(Format.class);
      hasText = mock(HasText.class);
      form = new FormModel();
      field = form.formattedListOfType(Integer.class)
         .using(mockFormat)
         .create();
   }

   @Test
   public void formatUsesModelsFormatAsDefault()
   {
      field.setElements(Arrays.asList(42,42,42));
      // the binding uses a CollectionToString format based on the
      // underlying fields format if non is supplied.
      when(mockFormat.format(42)).thenReturn("blah");

      binding = new FormattedListFieldToHasTextBinding<Integer>(field, hasText, null);
      binding.updateTarget();

      // the default CollectionToString format should be used with the mockformat.
      verify(hasText).setText(eq("blah, blah, blah"));
   }

   @Test
   public void formatChangesUpdateWidget()
   {
      final String firstValue = "d";
      final String secondValue = "u";

      field.setElements(Arrays.asList(42,42,42));
      field.setFormat(new SillyFormat(firstValue));

      binding = new FormattedListFieldToHasTextBinding<Integer>(field, hasText, null);
      binding.updateTarget();
      verify(hasText).setText(eq(firstValue + ", " + firstValue + ", " + firstValue));

      field.setFormat(new SillyFormat(secondValue));
      verify(hasText).setText(eq(secondValue + ", " + secondValue + ", " + secondValue));
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

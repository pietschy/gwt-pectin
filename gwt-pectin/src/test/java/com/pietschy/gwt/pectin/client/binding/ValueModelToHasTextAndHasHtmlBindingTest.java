package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 23, 2010
 * Time: 9:35:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class ValueModelToHasTextAndHasHtmlBindingTest
{
   @Test
   public void toLabelBindingWorks()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ValueHolder<String> model = new ValueHolder<String>("a");
      HasText hasText = mock(HasText.class);

      binder.bind(model).toTextOf(hasText);
      model.setValue("b");
      verify(hasText, times(1)).setText(eq("a"));
      verify(hasText, times(1)).setText(eq("b"));
   }

   @Test
   public void toLabelBindingWorksWithFormat()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ValueHolder<String> model = new ValueHolder<String>("a");
      HasText hasText = mock(HasText.class);

      binder.bind(model).toTextOf(hasText).withFormat(new DisplayFormat<String>()
      {
         public String format(String value)
         {
            return String.format("[%s]", value);
         }
      });
      model.setValue("b");
      verify(hasText, times(1)).setText(eq("[a]"));
      verify(hasText, times(1)).setText(eq("[b]"));

   }

   @Test
   public void toHtmlBindingWorks()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ValueHolder<String> model = new ValueHolder<String>("a");
      HasHTML hasText = mock(HasHTML.class);

      binder.bind(model).toHtmlOf(hasText);
      model.setValue("b");
      verify(hasText, times(1)).setHTML(eq("a"));
      verify(hasText, times(1)).setHTML(eq("b"));
   }

   @Test
   public void toHtmlBindingWorksWithFormat()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ValueHolder<String> model = new ValueHolder<String>("a");
      HasHTML hasText = mock(HasHTML.class);

      binder.bind(model).toHtmlOf(hasText).withFormat(new DisplayFormat<String>()
      {
         public String format(String value)
         {
            return String.format("<b>%s</b>", value);
         }
      });
      model.setValue("b");
      verify(hasText, times(1)).setHTML(eq("<b>a</b>"));
      verify(hasText, times(1)).setHTML(eq("<b>b</b>"));

   }
}
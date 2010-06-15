package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.function.Join;
import com.pietschy.gwt.pectin.client.list.ArrayListModel;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 23, 2010
 * Time: 9:35:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListModelToHasTextAndHasHtmlBindingTest
{
   @Test
   public void toLabelBindingWorks()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ArrayListModel<String> model = new ArrayListModel<String>("a", "b");
      HasText hasText = mock(HasText.class);

      binder.bind(model).toTextOf(hasText);
      model.setElements(Arrays.asList("c", "d"));

      verify(hasText, times(1)).setText(eq("a, b"));
      verify(hasText, times(1)).setText(eq("c, d"));
   }

   @Test
   public void toLabelBindingWorksWithFormat()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ArrayListModel<String> model = new ArrayListModel<String>("a", "b");
      HasText hasText = mock(HasText.class);

      binder.bind(model).toTextOf(hasText).withFormat(new Join(":"));

      model.setElements(Arrays.asList("c", "d"));

      verify(hasText, times(1)).setText(eq("a:b"));
      verify(hasText, times(1)).setText(eq("c:d"));

   }

   @Test
   public void toHtmlBindingWorks()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ArrayListModel<String> model = new ArrayListModel<String>("a", "b");
      HasHTML hasHTML = mock(HasHTML.class);

      binder.bind(model).toHtmlOf(hasHTML);
      model.setElements(Arrays.asList("c", "d"));

      verify(hasHTML, times(1)).setHTML(eq("a, b"));
      verify(hasHTML, times(1)).setHTML(eq("c, d"));
   }

   @Test
   public void toHtmlBindingWorksWithFormat()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ArrayListModel<String> model = new ArrayListModel<String>("a", "b");
      HasHTML hasHTML = mock(HasHTML.class);

      binder.bind(model).toHtmlOf(hasHTML).withFormat(new Join(":"));
      model.setElements(Arrays.asList("c", "d"));

      verify(hasHTML, times(1)).setHTML(eq("a:b"));
      verify(hasHTML, times(1)).setHTML(eq("c:d"));

   }
}
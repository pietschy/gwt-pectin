package com.pietschy.gwt.pectin.client.binding;

import com.pietschy.gwt.pectin.client.list.ArrayListModel;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 23, 2010
 * Time: 9:35:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class MutableListModelToMutableListModelBindingTest
{
   @Test
   public void bindingsGoBothWays()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ArrayListModel<String> a = new ArrayListModel<String>();
      ArrayListModel<String> b = new ArrayListModel<String>();

      List<String> listOne = Arrays.asList("a", "b", "c");
      a.setElements(listOne);
      assertEquals(a.asUnmodifiableList(), listOne);
      assertEquals(b.size(), 0);

      binder.bind(a).to(b);

      assertEquals(a.asUnmodifiableList(), listOne);
      assertEquals(b.asUnmodifiableList(), listOne);

      List<String> listTwo = Arrays.asList("e", "f", "g");
      b.setElements(listTwo);
      assertEquals(a.asUnmodifiableList(), listTwo);
      a.remove("g");
      assertEquals(b.asUnmodifiableList(), Arrays.asList("e", "f"));

   }
}
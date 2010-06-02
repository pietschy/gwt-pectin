package com.pietschy.gwt.pectin.reflect;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.pietschy.gwt.pectin.client.list.ListModelChangedEvent;
import com.pietschy.gwt.pectin.client.util.Utils;
import org.mockito.ArgumentMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Matchers.argThat;
import static org.testng.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 11, 2010
 * Time: 11:15:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class AssertUtil
{
   public static void assertContentEquals(Collection<?> list, Object... values)
   {
      assertContentEquals(list, Arrays.asList(values));
   }

   public static void assertContentEquals(Collection<?> list, Collection<?> values)
   {
      if (list.size() != values.size())
      {
         fail("collections are different sizes: list=" + list.toString() + ", values= " + values.toString());
      }

      if (!list.containsAll(values))
      {
         fail("contents are different, list=" + list.toString() + ", values= " + values.toString());
      }
   }

   public static <T> ValueChangeEvent<T> isValueChangeEventWithValue(final T newValue)
   {
      return argThat(new ArgumentMatcher<ValueChangeEvent<T>>()
      {
         @Override
         public boolean matches(Object o)
         {
            T eventValue = ((ValueChangeEvent<T>) o).getValue();
            return newValue == null ? eventValue == null : newValue.equals(eventValue);
         }
      });
   }

   public static <T> ListModelChangedEvent<T> isListChangeEventWithValues(T first, T... others)
   {
      return isListChangeEventWithValues(Utils.asList(first, others));
   }

   public static <T> ListModelChangedEvent<T> isListChangeEventWithValues(final List<T> values)
   {
      return argThat(new ArgumentMatcher<ListModelChangedEvent<T>>()
      {
         @Override
         public boolean matches(Object o)
         {
            ListModelChangedEvent<T> event = (ListModelChangedEvent<T>) o;
            return compare(event.getSourceModel().asUnmodifiableList(), values);
         }
      });
   }

   private static <T> boolean compare(List<T> source, List<T> values)
   {
      if (source.size() != values.size())
      {
         return false;
      }

      for (int i = 0; i < source.size(); i++)
      {
         if (!areEqual(source.get(i), values.get(i)))
         {
            return false;
         }
      }

      return true;
   }

   private static <T> boolean areEqual(T first, T second)
   {
      return first == null ? second == null : first.equals(second);
   }
}

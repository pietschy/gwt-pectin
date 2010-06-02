package com.pietschy.gwt.pectin.client.value;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 14, 2010
 * Time: 12:04:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class GatedLatchTest
{
   private ValueHolder<String> source;
   private ValueHolder<Boolean> trigger;
   private GatedLatch<String> latch;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      source = new ValueHolder<String>();
      trigger = new ValueHolder<Boolean>(false);
      latch = new GatedLatch<String>(source, trigger);
   }

   @Test
   public void latchTracksValueWhileTriggerIsFalse()
   {
      ValueChangeHandler<String> mock = (ValueChangeHandler<String>) mock(ValueChangeHandler.class);

      latch.addValueChangeHandler(mock);

      assertNull(latch.getValue());

      source.setValue("a");
      assertEquals(latch.getValue(), source.getValue());
      verify(mock, times(1)).onValueChange(argThat(new IsValueChangeEventWithValue<String>("a")));

      source.setValue("b");
      assertEquals(latch.getValue(), source.getValue());
      verify(mock, times(1)).onValueChange(argThat(new IsValueChangeEventWithValue<String>("b")));

   }

   @Test
   public void latchHoldsValueWhileTriggerIsTrue()
   {
      ValueChangeHandler<String> mock = (ValueChangeHandler<String>) mock(ValueChangeHandler.class);
      latch.addValueChangeHandler(mock);

      assertNull(latch.getValue());

      source.setValue("a");
      assertEquals(latch.getValue(), source.getValue());
      trigger.setValue(true);
      source.setValue("b");
      assertEquals(latch.getValue(), "a");
      source.setValue("c");
      assertEquals(latch.getValue(), "a");
      source.setValue("d");
      assertEquals(latch.getValue(), "a");

      trigger.setValue(false);
      assertEquals(latch.getValue(), source.getValue());

      verify(mock, times(1)).onValueChange(argThat(new IsValueChangeEventWithValue<String>("a")));
      verify(mock, times(1)).onValueChange(argThat(new IsValueChangeEventWithValue<String>("d")));

      // should have had only two value change events.
      verify(mock, times(2)).onValueChange(Mockito.any(ValueChangeEvent.class));




   }
}

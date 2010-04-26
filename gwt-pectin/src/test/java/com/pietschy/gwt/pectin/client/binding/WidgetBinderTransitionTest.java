package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

/**
 * 
 */
public class WidgetBinderTransitionTest
{
   private ValueHolder<String> value;
   private WidgetBinder binder;

   @BeforeTest
   public void setUp()
   {
      value = new ValueHolder<String>();
      binder = new WidgetBinder();
   }

   @Test
   public void commandNeverFiresOnBinding()
   {
      // this is the value that would have fire bindings were alread added...
      value.setValue("b");

      Command toCommand = Mockito.mock(Command.class);
      Command fromCommand = Mockito.mock(Command.class);
      binder.onTransitionOf(value).from("a").invoke(fromCommand);
      binder.onTransitionOf(value).to("b").invoke(toCommand);

      verify(toCommand, never()).execute();
      verify(fromCommand, never()).execute();
   }

   @Test
   public void transitionToOnlyFiresOnceIfValuesChangesMultipleTimes()
   {
      value.setValue("a");
      value.setFireEventsEvenWhenValuesEqual(true);

      ValueChangeHandler<String> valueHandlerMock = (ValueChangeHandler<String>) Mockito.mock(ValueChangeHandler.class);
      value.addValueChangeHandler(valueHandlerMock);


      Command toMock = Mockito.mock(Command.class);
      Command fromMock = Mockito.mock(Command.class);

      binder.onTransitionOf(value).from("a").invoke(fromMock);
      binder.onTransitionOf(value).to("b").invoke(toMock);

      // should fire..
      value.setValue("b");

      verify(valueHandlerMock, times(1)).onValueChange(isA(ValueChangeEvent.class));
      verify(toMock, times(1)).execute();
      verify(fromMock, times(1)).execute();

      // value change should fire but command should not.
      value.setValue("b");
      // the value change should have happened twice now..
      verify(valueHandlerMock, times(2)).onValueChange(isA(ValueChangeEvent.class));
      // but the values still only once.
      verify(toMock, times(1)).execute();
      verify(fromMock, times(1)).execute();


   }

   @Test
   public void transitionToFiresCorrectly()
   {
      value.setValue(null);

      Command toMock = Mockito.mock(Command.class);

      binder.onTransitionOf(value).to("c").invoke(toMock);

      value.setValue("b");
      verify(toMock, times(0)).execute();

      // entering 'c', should fire.
      value.setValue("c");
      verify(toMock, times(1)).execute();

      value.setValue("b");
      verify(toMock, times(1)).execute();

      value.setValue(null);
      verify(toMock, times(1)).execute();

      // entering 'c', should fire.
      value.setValue("c");
      verify(toMock, times(2)).execute();
   }

   @Test
   public void transitionFromFiresCorrectly()
   {
      value.setValue(null);
      value.setFireEventsEvenWhenValuesEqual(true);

      Command toMock = Mockito.mock(Command.class);

      binder.onTransitionOf(value).from("a").invoke(toMock);

      value.setValue("b");
      verify(toMock, times(0)).execute();

      value.setValue("a");
      verify(toMock, times(0)).execute();

      // leaving 'a', should fire
      value.setValue("b");
      verify(toMock, times(1)).execute();

      value.setValue("c");
      verify(toMock, times(1)).execute();

      value.setValue("a");
      verify(toMock, times(1)).execute();

      // leaving 'a', should fire
      value.setValue(null);
      verify(toMock, times(2)).execute();
   }
}

package com.pietschy.gwt.pectin.client.command;

import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 26, 2010
 * Time: 10:14:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class DelegatingUiCommandTest
{
   private ValueHolder<Boolean> userEnabled;
   private ValueHolder<Boolean> userDisabled;
   private ValueHolder<Boolean> delegateEnabled;

   private TestUiCommand delegate;
   private TestTemporalUiCommand temporalDelegate;

   @BeforeTest
   public void setUp()
   {
      userEnabled = new ValueHolder<Boolean>();
      userDisabled = new ValueHolder<Boolean>();
      delegateEnabled = new ValueHolder<Boolean>();

      delegate = new TestUiCommand(delegateEnabled);
      temporalDelegate = new TestTemporalUiCommand(delegateEnabled);
   }

   @Test
   public void disabledWithoutDelegate()
   {
      assertFalse(new DelegatingUiCommand().enabled().getValue());
   }

   @Test
   public void enabledWithDelegate()
   {
      delegateEnabled.setValue(true);

      DelegatingUiCommand da = new DelegatingUiCommand(delegate);
      assertTrue(da.enabled().getValue());
   }

   @Test
   public void constructorConfiguresDelegate()
   {
      final AtomicBoolean setDelegateCalled = new AtomicBoolean(false);

      new DelegatingUiCommand(delegate)
      {
         @Override
         public void setDelegate(UiCommand delegate)
         {
            super.setDelegate(delegate);
            setDelegateCalled.set(true);
         }
      };

      assertTrue(setDelegateCalled.get());
   }
   

   @Test(dataProvider = "enabledTracksDelegateData")
   public void enabledTracksDelegate(boolean delegateEnabled, boolean userEnabled)
   {
      DelegatingUiCommand command = new DelegatingUiCommand();

      this.delegateEnabled.setValue(delegateEnabled);
      this.userEnabled.setValue(userEnabled);

      command.enableWhen(this.userEnabled);
      command.setDelegate(temporalDelegate);

      assertEquals(delegateEnabled && userEnabled, (boolean) command.enabled().getValue());
   }

   @DataProvider
   public Object[][] enabledTracksDelegateData()
   {
      return new Object[][]
         {
            new Object[] {false, false},
            new Object[] {true, false},
            new Object[] {true, true},
            new Object[] {false, true},
         };
   }

   @Test(dataProvider = "disabledWhenWorksData")
   public void disabledWhenWorks(boolean delegateEnabled, boolean userDisabled)
   {
      DelegatingUiCommand command = new DelegatingUiCommand();

      this.delegateEnabled.setValue(delegateEnabled);
      this.userDisabled.setValue(userDisabled);

      command.disableWhen(this.userDisabled);
      command.setDelegate(temporalDelegate);

      assertEquals(delegateEnabled && !userDisabled, (boolean) command.enabled().getValue());
   }

   @DataProvider
   public Object[][] disabledWhenWorksData()
   {
      return new Object[][]
         {
            new Object[] {false, false},
            new Object[] {true, false},
            new Object[] {true, true},
            new Object[] {false, true},
         };
   }

   @Test(expectedExceptions = MissingDelegateException.class)
   public void executeWithNoDelegateBarfs() throws Exception
   {
      new DelegatingUiCommand().execute();
   }

   @Test
   public void activeTracksDelegate() throws Exception
   {
      DelegatingUiCommand command = new DelegatingUiCommand();

      command.setDelegate(temporalDelegate);
      assertFalse(command.active().getValue());

      temporalDelegate.setActive(true);
      assertTrue(command.active().getValue());

      command.setDelegate(delegate);
      assertFalse(command.active().getValue());

      command.setDelegate(temporalDelegate);
      assertTrue(command.active().getValue());

      temporalDelegate.setActive(false);
      assertFalse(command.active().getValue());

      command.setDelegate(null);
      assertFalse(command.active().getValue());


   }
   @Test
   public void activeIsFalseForNonTemporalCommand() throws Exception
   {
      DelegatingUiCommand command = new DelegatingUiCommand();

      command.setDelegate(delegate);

      assertFalse(command.active().getValue());
   }

   private static class TestTemporalUiCommand extends IncrementalUiCommand
   {
      public TestTemporalUiCommand(ValueHolder<Boolean> delegateEnabled)
      {
         enableWhen(delegateEnabled);
      }

      @Override
      public void setActive(boolean active)
      {
         super.setActive(active);
      }

      @Override
      public boolean doIncrementalWork()
      {
         return false;
      }
   }

   private static class TestUiCommand extends AbstractUiCommand
   {
      public TestUiCommand(ValueHolder<Boolean> delegateEnabled)
      {
         enableWhen(delegateEnabled);
      }

      @Override
      protected void doExecute()
      {
      }
   }
}

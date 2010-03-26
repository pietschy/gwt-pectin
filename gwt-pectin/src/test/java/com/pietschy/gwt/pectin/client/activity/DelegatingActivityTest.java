package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 26, 2010
 * Time: 10:14:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class DelegatingActivityTest
{
   private ValueHolder<Boolean> userEnabled;
   private ValueHolder<Boolean> userDisabled;
   private ValueHolder<Boolean> delegateEnabled;

   private TestActivity delegate;
   private TestTemporalActivity temporalDelegate;

   @BeforeTest
   public void setUp()
   {
      userEnabled = new ValueHolder<Boolean>();
      userDisabled = new ValueHolder<Boolean>();
      delegateEnabled = new ValueHolder<Boolean>();

      delegate = new TestActivity(delegateEnabled);
      temporalDelegate = new TestTemporalActivity(delegateEnabled);
   }

   @Test
   public void disabledWithoutDelegate()
   {
      assertFalse(new DelegatingActivity().enabled().getValue());
   }

   @Test(dataProvider = "enabledTracksDelegateData")
   public void enabledTracksDelegate(boolean delegateEnabled, boolean userEnabled)
   {
      DelegatingActivity activity = new DelegatingActivity();

      this.delegateEnabled.setValue(delegateEnabled);
      this.userEnabled.setValue(userEnabled);

      activity.enableWhen(this.userEnabled);
      activity.setDelegate(temporalDelegate);

      assertEquals(delegateEnabled && userEnabled, (boolean) activity.enabled().getValue());
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
      DelegatingActivity activity = new DelegatingActivity();

      this.delegateEnabled.setValue(delegateEnabled);
      this.userDisabled.setValue(userDisabled);

      activity.disableWhen(this.userDisabled);
      activity.setDelegate(temporalDelegate);

      assertEquals(delegateEnabled && !userDisabled, (boolean) activity.enabled().getValue());
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

   @Test(expectedExceptions = NullPointerException.class)
   public void executeWithNoDelegateBarfs() throws Exception
   {
      new DelegatingActivity().execute();
   }

   @Test
   public void activeTracksDelegate() throws Exception
   {
      DelegatingActivity activity = new DelegatingActivity();

      activity.setDelegate(temporalDelegate);
      assertFalse(activity.active().getValue());

      temporalDelegate.setActive(true);
      assertTrue(activity.active().getValue());

      activity.setDelegate(delegate);
      assertFalse(activity.active().getValue());

      activity.setDelegate(temporalDelegate);
      assertTrue(activity.active().getValue());

      temporalDelegate.setActive(false);
      assertFalse(activity.active().getValue());

      activity.setDelegate(null);
      assertFalse(activity.active().getValue());


   }
   @Test
   public void activeIsFalseForNonTemporalActivity() throws Exception
   {
      DelegatingActivity activity = new DelegatingActivity();

      activity.setDelegate(delegate);

      assertFalse(activity.active().getValue());
   }

   private static class TestTemporalActivity extends AbstractIncrementalActivity
   {
      public TestTemporalActivity(ValueHolder<Boolean> delegateEnabled)
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

   private static class TestActivity extends AbstractActivity
   {
      public TestActivity(ValueHolder<Boolean> delegateEnabled)
      {
         enableWhen(delegateEnabled);
      }

      public void execute()
      {
      }
   }
}

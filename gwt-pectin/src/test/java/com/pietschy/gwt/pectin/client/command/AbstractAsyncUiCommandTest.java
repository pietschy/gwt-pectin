package com.pietschy.gwt.pectin.client.command;

import org.mockito.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 11, 2010
 * Time: 10:33:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractAsyncUiCommandTest
{
   @BeforeTest
   public void setUp()
   {
   }

   @Test
   public void abortCallsAfterAbortAndFinishesTheExecution()
   {
      final AtomicBoolean afterAbortCalled = new AtomicBoolean(false);
      final AtomicBoolean afterFinishCalled = new AtomicBoolean(false);

     AsyncLifeCycleCallback<Void,Void> events = (AsyncLifeCycleCallback<Void, Void>) mock(AsyncLifeCycleCallback.class);

      AbstractAsyncUiCommand<Void, Void> test = new AbstractAsyncUiCommand<Void, Void>()
      {
         @Override
         protected void performAsyncOperation(AsyncCommandCallback<Void, Void> callback)
         {
            callback.abort();
         }

         @Override
         protected void afterAbort()
         {
            afterAbortCalled.set(true);
         }

         @Override
         protected void afterFinish()
         {
            afterFinishCalled.set(true);
         }
      };


      test.always().sendAllEventsTo(events);

      test.execute();

      Assert.assertTrue(afterAbortCalled.get());
      Assert.assertTrue(afterFinishCalled.get());

      verify(events, times(1)).onStart();
      verify(events, times(0)).onError(Matchers.any(Void.class));
      verify(events, times(0)).onSuccess(Matchers.any(Void.class));
      verify(events, times(1)).onFinish();
   }




}

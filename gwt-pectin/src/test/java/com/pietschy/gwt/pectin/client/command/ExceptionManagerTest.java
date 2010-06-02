package com.pietschy.gwt.pectin.client.command;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 2, 2010
 * Time: 3:20:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionManagerTest
{
   private ExceptionManager<String> manager;
   @Mock
   private AsyncCommandCallback<String, String> callback;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      MockitoAnnotations.initMocks(this);
      manager = new ExceptionManager<String>();
   }

   @Test
   public void defaultHandlerCalledWhenNonRegistered()
   {
      final String message = "an error happened";
      manager.onUnregisteredExceptionsInvoke(new ExceptionHandler<Throwable, String>()
      {
         @Override
         public void handle(Throwable error)
         {
            publishError(message);
         }
      });

      manager.processException(new Exception(), callback);
      verify(callback).publishError(eq(message));
   }

   @Test
   public void onCatchingWithPublishErrorInvokesCallback()
   {
      final String message = "an error happened";
      manager.onCatching(Exception.class).invoke(new ExceptionHandler<Exception, String>()
      {
         @Override
         public void handle(Exception error)
         {
            publishError(message);
         }
      });

      manager.processException(new Exception(), callback);

      verify(callback).publishError(eq(message));
   }

   @Test
   public void onCatchingWithAbortInvokesCallback()
   {
      final String message = "an error happened";
      manager.onCatching(Exception.class).invoke(new ExceptionHandler<Exception, String>()
      {
         @Override
         public void handle(Exception error)
         {
            abort();
         }
      });

      manager.processException(new Exception(), callback);

      verify(callback).abort();
   }



   @Test
   public void onCatchingWithDirectPublishWorks()
   {
      String error = "message";
      manager.onCatching(Exception.class).publishError(error);
      manager.processException(new Exception(), callback);
      verify(callback).publishError(eq(error));
   }

   @Test(expectedExceptions = IllegalStateException.class)
   public void handlerBarfsIfNoHandlerRegistered()
   {
      manager.processException(new Exception(), callback);
   }
}

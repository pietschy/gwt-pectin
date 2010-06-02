package com.pietschy.gwt.pectin.client.command;

import java.util.HashMap;

/**
 * A class that can be used to process exceptions from async callbacks.
 * <pre>
 * ExceptionManger exceptions = ...;
 *
 * // Configure our exception handling.
 * // Some exceptions just translate to messages...
 * exceptions.onCatching(UserNameInUseException.class)
 *    .publishError(constants.userNameInUseMessage());
 *
 * // other exceptions need explicit handling...
 * exceptions.onCatching(StaleEntityException.class)
 *    .invoke(new ExceptionHandler() {
 *        public void handle(StaleEntityException error) {
 *           // do things like refreshing the entity...
 *           ...
 *           // and finally publish the error
 *           publishError(messages.staleEntityMessage());
 *        }
 *    });
 *
 * // We also need a default handler for all those RPC exceptions..
 * exceptions.onUnregisteredExceptionsInvoke(new ExceptionHandler() {
 *    public void handle(Throwable error) {
 *       // handle things like status code exceptions etc.
 *    }
 * });
 *
 *
 * // And finally we can use it in our async command
 * public void performAsyncOperation(final AsyncCommandCallback commandCallback) {
 *    service.doStuff(new AsyncCallback() {
 *       public void onFailure(Throwable caught) {
 *          exceptions.processException(caught, commandCallback);
 *       }
 *       public void onSuccess(T success) {
 *          // do stuff..
 *       }
 *    }
 * }
 * </pre>
 *
 * The manager also provides the method {@link #containsHandlerFor(Class)} so you can perform unit testing
 * to ensure a handler is registered for all the declared exceptions of the service.
 * <pre>
 * public void testAllExceptionsAreHandled() {
 *   for(Class exceptionType : reflectivelyGetServiceExceptions(MyService.class, "doStuff")) {
 *     assertTrue(exceptionManager.containsHandlerFor(exceptionType));
 *   }
 * }
 * </pre>
 * 
 */
public class ExceptionManager<E>
{
   private ExceptionHandler<?, E> defaultHandler;
   private HashMap<Class<? extends Throwable>, ExceptionHandler<?, E>> handlers = new HashMap<Class<? extends Throwable>, ExceptionHandler<?, E>>();

   public <T extends Throwable> ExceptionHandlerBuilder<T> onCatching(Class<T> type)
   {
      if (containsHandlerFor(type))
      {
         throw new IllegalStateException("Exception type already registered: " + type);
      }
      return new ExceptionHandlerBuilder<T>(type);
   }

   public void onUnregisteredExceptionsInvoke(ExceptionHandler<Throwable, E> callback)
   {
      defaultHandler = callback;
   }

   public void processException(Throwable caught, AsyncCommandCallback<?,E> callback)
   {
      ExceptionHandler<?, E> handler = getHandlerFor(caught);

      if (handler == null)
      {
         throw new IllegalStateException("No handler registered for: " + caught);
      }

      handler.process(caught, callback);
   }

   private ExceptionHandler<?, E> getHandlerFor(Throwable caught)
   {
      ExceptionHandler<?, E> handler = handlers.get(caught.getClass());
      return (handler != null) ? handler: defaultHandler;
   }

   public boolean containsHandlerFor(Class<? extends Throwable> type)
   {
      return handlers.containsKey(type);
   }

   public class ExceptionHandlerBuilder<T extends Throwable>
   {
      private Class<T> type;

      public ExceptionHandlerBuilder(Class<T> type)
      {
         this.type = type;
      }

      public void invoke(ExceptionHandler<T, E> handler)
      {
         handlers.put(type, handler);
      }

      public void publishError(final E error)
      {
         handlers.put(type, new ExceptionHandler<T, E>()
         {
            @Override
            public void handle(T caught)
            {
               publishError(error);
            }
         });
      }
   }
}
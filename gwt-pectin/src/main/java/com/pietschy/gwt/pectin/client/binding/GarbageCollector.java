package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.event.shared.HandlerRegistration;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 19, 2010
 * Time: 1:57:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class GarbageCollector implements Disposable
{
   private ArrayList<Disposable> disposables = new ArrayList<Disposable>();

   public void add(Disposable disposable)
   {
      disposables.add(disposable);
   }

   public void add(final HandlerRegistration registration)
   {
      add(new Disposable()
      {
         public void dispose()
         {
            registration.removeHandler();
         }
      });
   }

   public void dispose()
   {
      for (Disposable disposable : disposables)
      {
         disposable.dispose();
      }

      disposables.clear();
   }
}

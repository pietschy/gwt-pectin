package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.event.shared.HandlerRegistration;
import com.pietschy.gwt.pectin.client.util.VarArgUtil;

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

   /**
    * Adds the specified disposable to this collector and returns it.
    * @param disposable the disposable to add.
    * @return the disposable after adding it to the collector.
    */
   public <T extends Disposable> T capture(T disposable)
   {
      add(disposable);
      return disposable;
   }

   public void add(Disposable disposable)
   {
      disposables.add(disposable);
   }

   public void add(Disposable disposable, Disposable... others)
   {
      disposables.addAll(VarArgUtil.asList(disposable, others));
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

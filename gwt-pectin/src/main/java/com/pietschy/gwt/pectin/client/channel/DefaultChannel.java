package com.pietschy.gwt.pectin.client.channel;

import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.activity.ParameterisedCommand;
import com.pietschy.gwt.pectin.client.function.Function;
import com.pietschy.gwt.pectin.client.value.MutableValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 23, 2010
 * Time: 2:32:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultChannel<T> implements Channel<T>
{
   private ArrayList<Registration> registrations = new ArrayList<Registration>();

   public void publish(T value)
   {
      // make a copy so we can mutate the registration list during the iteration.  Otherwise
      // registration.dispose() will cause a concurrent modification exception.
      for (Registration registration : copyRegistrations())
      {
         registration.publish(value);
//         if (registration.isOnlyOnce())
//         {
//            registration.dispose();
//         }
      }
   }

   private List<Registration> copyRegistrations()
   {
      return new ArrayList<Registration>(registrations);
   }

//   public <S> Publisher<S> formattedWith(final Function<T, S> function)
//   {
//      return new Publisher<S>()
//      {
//         public void publish(S value)
//         {
//            DefaultChannel.this.publish(function.compute(value));
//         }
//      };
//   }

   public <S> Destination<S> formattedWith(final Function<T,S> function)
   {
      return new Destination<S>()
      {
         public void receive(S value)
         {
            publish(function.compute(value));
         }
      };
   }

   public Destination<T> asDestination()
   {
      return new Destination<T>()
      {
         public void receive(T value)
         {
            publish(value);
         }
      };
   }

   public ChannelRegistration sendTo(Destination<? super T> destination)
   {
      Registration registration = new Registration(destination);
      registrations.add(registration);
      return registration;
   }

   public ChannelRegistration sendTo(final Publisher<? super T> publisher)
   {
      return sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            publisher.publish(value);
         }
      });
   }

   public ChannelRegistration sendTo(final MutableValue<? super T> destination)
   {
      return sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setValue(value);
         }
      });
   }

   public ChannelRegistration sendTo(final HasValue<? super T> destination)
   {
      return sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setValue(value);
         }
      });
   }

   public ChannelRegistration sendTo(final ParameterisedCommand<? super T> destination)
   {
      return sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.execute(value);
         }
      });
   }

   private class Registration implements ChannelRegistration
   {
      private Destination<? super T> destination;
      private boolean onlyOnce;

      private Registration(Destination<? super T> destination)
      {
         this.destination = destination;
      }

      private void publish(T value)
      {
         destination.receive(value);
      }

      public void dispose()
      {
         registrations.remove(this);
      }

//      public ChannelRegistration onlyOnce()
//      {
//         this.onlyOnce = true;
//         return this;
//      }
//
//      public boolean isOnlyOnce()
//      {
//         return onlyOnce;
//      }
   }
}

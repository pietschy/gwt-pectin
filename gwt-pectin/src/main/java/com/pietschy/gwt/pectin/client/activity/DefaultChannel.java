package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.value.MutableValue;

import java.util.ArrayList;

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
      for (Registration registration : registrations)
      {
         registration.publish(value);
      }
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

   public ChannelRegistration sendTo(Destination<T> destination)
   {
      Registration registration = new Registration(destination);
      registrations.add(registration);
      return registration;
   }

   public ChannelRegistration sendTo(final MutableValue<T> destination)
   {
      return sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setValue(value);
         }
      });
   }

   private class Registration implements ChannelRegistration
   {
      private Destination<T> destination;

      private Registration(Destination<T> destination)
      {
         this.destination = destination;
      }

      private void publish(T value)
      {
         destination.receive(value);
      }

      public void dispose()
      {
         // todo: this is totally ignoring the case where someone might call registration.dispose() during recieve(T)
         registrations.remove(this);
      }
   }
}

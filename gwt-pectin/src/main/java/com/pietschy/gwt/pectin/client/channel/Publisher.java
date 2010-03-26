package com.pietschy.gwt.pectin.client.channel;

/**
 * Interface for publishing values to a Channel.
 */
public interface Publisher<T>
{
   void publish(T value);
}

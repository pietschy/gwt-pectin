package com.pietschy.gwt.pectin.client.value;

/**
 * This interface is used as a basis for all objects that can vend a value of particular type.
 * @see ValueModel
 */
public interface ValueSource<T>
{
   /**
    * Gets the value held by this provider.
    * @return the value held by this provider.
    */
   T getValue();
}

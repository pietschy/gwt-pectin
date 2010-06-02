package com.pietschy.gwt.pectin.client.value;

/**
 * @deprecated use {@link com.pietschy.gwt.pectin.client.value.ValueSource} instead.
 */
public interface HasValueGetter<T> extends ValueSource<T>
{
   /**
    * Gets the value held by this provider.
    * @return the value held by this provider.
    */
   T getValue();
}

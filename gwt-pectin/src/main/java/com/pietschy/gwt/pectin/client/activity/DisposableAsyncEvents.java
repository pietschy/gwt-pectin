package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.binding.Disposable;

/**
 * An subtype of {@link com.pietschy.gwt.pectin.client.activity.AsyncEvents} that can be disposed
 * of as a whole rather than disposing each of the event registrations (disposables).
 */
public interface DisposableAsyncEvents<R,E> extends AsyncEvents<R, E>, Disposable
{
}

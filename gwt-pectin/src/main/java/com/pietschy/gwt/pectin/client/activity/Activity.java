package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * An Activity is a {@link Command} that also knows it's enabled state.
 * @see #enabled()
 * @see TemporalActivity
 * @see AsyncActivity
 * @see AbstractActivity
 * @see AbstractIncrementalActivity
 * @see DelegatingActivity
 */
public interface Activity extends Command
{
   /**
    * Returns a ValueModel representing the enabled state of this activity.
    * @return a ValueModel representing the enabled state of this activity.
    */
   ValueModel<Boolean> enabled();
}
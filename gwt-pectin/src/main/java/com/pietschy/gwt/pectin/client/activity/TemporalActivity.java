package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * TemporalActivity represents activities whose execution is not synchronous (i.e. the
 * command doesn't complete until sometime after execute returns).  TemporalActivities
 * provide a {@link com.pietschy.gwt.pectin.client.value.ValueModel ValueModel<Boolean>} that
 * holds the active state of the command.
 * <p>
 * The two main subclasses are {@link com.pietschy.gwt.pectin.client.activity.AbstractAsyncActivity} and
 * {@link com.pietschy.gwt.pectin.client.activity.AbstractIncrementalActivity}.
 */
public interface TemporalActivity extends Activity
{
   /**
    * Returns a value model that is true while the command is executing.
    *
    * @return a value model that is true while the command is executing.
    */
   ValueModel<Boolean> active();
}

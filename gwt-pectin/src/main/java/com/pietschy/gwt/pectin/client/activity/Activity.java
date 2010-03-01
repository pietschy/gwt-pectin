package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 23, 2010
 * Time: 11:22:45 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Activity extends Command
{
   ValueModel<Boolean> isEnabled();
}
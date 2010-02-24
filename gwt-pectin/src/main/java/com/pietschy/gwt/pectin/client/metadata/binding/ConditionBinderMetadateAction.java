package com.pietschy.gwt.pectin.client.metadata.binding;

import com.pietschy.gwt.pectin.client.metadata.Metadata;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Feb 24, 2010
* Time: 4:16:14 PM
* To change this template use File | Settings | File Templates.
*/
public interface ConditionBinderMetadateAction<T>
{
   public void apply(T widget, boolean value);

   ValueModel<Boolean> getModel(Metadata metadata);
}

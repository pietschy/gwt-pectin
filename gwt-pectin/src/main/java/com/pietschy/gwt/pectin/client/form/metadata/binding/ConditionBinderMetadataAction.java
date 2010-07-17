package com.pietschy.gwt.pectin.client.form.metadata.binding;

import com.pietschy.gwt.pectin.client.form.metadata.Metadata;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Feb 24, 2010
* Time: 4:16:14 PM
* To change this template use File | Settings | File Templates.
*/
public interface ConditionBinderMetadataAction<T>  extends ConditionBinderWidgetAction<T>
{
   ValueModel<Boolean> getModel(Metadata metadata);
}

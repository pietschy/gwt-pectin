package com.pietschy.gwt.pectin.client.bean;

import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 26, 2010
 * Time: 11:17:24 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanPropertyModelBase
{
   void writeToSource(boolean clearDirtyState);

   void readFromSource();

   void checkpoint();

   void revertToCheckpoint();

   boolean isMutable();

   boolean isMutableProperty();

   ValueModel<Boolean> getDirtyModel();
}
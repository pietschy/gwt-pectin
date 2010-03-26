package com.pietschy.gwt.pectin.client.bean;

import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 26, 2010
 * Time: 11:17:24 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanPropertyModelBase<B>
{
   void copyTo(B bean, boolean clearDirtyState);

   @SuppressWarnings("unchecked")
   void readFrom(B bean);

   void checkpoint();

   void revertToCheckpoint();

   boolean isMutable();

   ValueModel<Boolean> getDirtyModel();
}

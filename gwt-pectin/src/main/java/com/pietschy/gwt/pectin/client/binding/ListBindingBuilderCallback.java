package com.pietschy.gwt.pectin.client.binding;

import com.pietschy.gwt.pectin.client.list.ListModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 22, 2010
 * Time: 10:50:32 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ListBindingBuilderCallback<T>
{
   void onBindingCreated(AbstractListBinding<T> binding, ListModel<T> model, Object target);
}
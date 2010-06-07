package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.format.CollectionToStringFormat;
import com.pietschy.gwt.pectin.client.list.ListModel;
import com.pietschy.gwt.pectin.client.value.ValueTarget;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 22, 2010
 * Time: 11:28:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListBindingBuilder<T>
{
   private BindingBuilderCallback callback;
   private ListModel<T> model;

   public ListBindingBuilder(ListModel<T> model, BindingBuilderCallback callback)
   {
      this.callback = callback;
      this.model = model;
   }

   public ListDisplayFormatBuilder<T> toLabel(HasText label)
   {
      ListModelToHasTextBinding<T> binding = new ListModelToHasTextBinding<T>(getModel(), label, CollectionToStringFormat.DEFAULT_INSTANCE);
      getCallback().onBindingCreated(binding, label);
      return new ListDisplayFormatBuilder<T>(binding);
   }

   public void to(ValueTarget<Collection<T>> target)
   {
      getCallback().onBindingCreated(new ListModelToValueTargetBinding<T>(model, target),
                                     target);
   }

   protected BindingBuilderCallback getCallback()
   {
      return callback;
   }

   protected ListModel<T> getModel()
   {
      return model;
   }
}

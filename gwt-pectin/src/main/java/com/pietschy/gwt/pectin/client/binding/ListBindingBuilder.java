package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.command.ParameterisedCommand;
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

   /**
    * Binds the list to the specified {@link HasText} target.
    * @param target the target to bind too.
    * @return a builder to optionally configure the format.
    * @deprecated use {@link #toText(HasText)} instead.
    */
   @Deprecated
   public ListDisplayFormatBuilder<T> toLabel(HasText label)
   {
      return toText(label);
   }

   /**
    * Binds the list to the specified {@link HasText} target.
    *
    * @param target the target to bind too.
    * @return a builder to optionally configure the format.
    */
   public ListDisplayFormatBuilder<T> toText(HasText target)
   {
      ListModelToHasTextBinding<T> binding = new ListModelToHasTextBinding<T>(getModel(), target, CollectionToStringFormat.DEFAULT_INSTANCE);
      getCallback().onBindingCreated(binding, target);
      return new ListDisplayFormatBuilder<T>(binding);
   }

   /**
    * Binds the list to the specified {@link HasHTML} target.
    *
    * @param target the target to bind too.
    * @return a builder to optionally configure the format.
    */
   public ListDisplayFormatBuilder<T> toHtml(HasHTML target)
   {
      ListModelToHasHTMLBinding<T> binding = new ListModelToHasHTMLBinding<T>(getModel(), target, CollectionToStringFormat.DEFAULT_INSTANCE);
      getCallback().onBindingCreated(binding, target);
      return new ListDisplayFormatBuilder<T>(binding);
   }

   public void to(ValueTarget<Collection<T>> target)
   {
      getCallback().onBindingCreated(new ListModelToValueTargetBinding<T>(model, target),
                                     target);
   }

   public void to(final ParameterisedCommand<Collection<T>> target)
   {
      AbstractBinding binding = new ListModelToValueTargetBinding<T>(model, new ValueTarget<Collection<T>>()
      {
         public void setValue(Collection<T> value)
         {
            target.execute(value);
         }
      });
      getCallback().onBindingCreated(binding, target);
   }

   public void to(final Destination<Collection<T>> target)
   {
      AbstractBinding binding = new ListModelToValueTargetBinding<T>(model, new ValueTarget<Collection<T>>()
      {
         public void setValue(Collection<T> value)
         {
            target.receive(value);
         }
      });
      getCallback().onBindingCreated(binding, target);
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

package com.pietschy.gwt.pectin.client.function.builder;

import com.pietschy.gwt.pectin.client.function.Reduce;
import com.pietschy.gwt.pectin.client.util.Utils;
import com.pietschy.gwt.pectin.client.value.ReducingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 16, 2010
* Time: 12:52:15 PM
* To change this template use File | Settings | File Templates.
*/
public class ReducingBuilder<S>
{
   private List<ValueModel<S>> models;

   public ReducingBuilder(ValueModel<S> first, ValueModel<S>... others)
   {
      this.models = Utils.asList(first, others);
   }

   public ReducingBuilder(Collection<ValueModel<S>> models)
   {
      this.models = new ArrayList<ValueModel<S>>(models);
   }

   public <T> ValueModel<T> using(Reduce<T, ? super S> function)
   {
      return new ReducingValueModel<T, S>(function, models);
   }
}

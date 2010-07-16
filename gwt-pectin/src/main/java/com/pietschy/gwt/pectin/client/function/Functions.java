package com.pietschy.gwt.pectin.client.function;

import com.pietschy.gwt.pectin.client.function.builder.ConverterBuilder;
import com.pietschy.gwt.pectin.client.function.builder.MutableConverterBuilder;
import com.pietschy.gwt.pectin.client.function.builder.ReducingBuilder;
import com.pietschy.gwt.pectin.client.function.builder.ReducingListBuilder;
import com.pietschy.gwt.pectin.client.list.ListModel;
import com.pietschy.gwt.pectin.client.value.MutableValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.Collection;

/**
 * 
 */
public class Functions
{
   public static <S> ConverterBuilder<S> convert(ValueModel<S> source)
   {
      return new ConverterBuilder<S>(source);
   }

   public static <S> MutableConverterBuilder<S> convert(MutableValueModel<S> source)
   {
      return new MutableConverterBuilder<S>(source);
   }

   public static <S> ReducingBuilder<S> computedFrom(ValueModel<S> source, ValueModel<S>... others)
   {
      return new ReducingBuilder<S>(source, others);
   }

   public static <S> ReducingBuilder<S> computedFrom(Collection<ValueModel<S>> models)
   {
      return new ReducingBuilder<S>(models);
   }

   public static <S> ReducingListBuilder<S> computedFrom(ListModel<S> source)
   {
      return new ReducingListBuilder<S>(source);
   }

}

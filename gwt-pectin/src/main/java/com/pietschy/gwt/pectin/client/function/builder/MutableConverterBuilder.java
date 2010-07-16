package com.pietschy.gwt.pectin.client.function.builder;

import com.pietschy.gwt.pectin.client.value.Converter;
import com.pietschy.gwt.pectin.client.value.ConvertingMutableValueModel;
import com.pietschy.gwt.pectin.client.value.MutableValueModel;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 16, 2010
* Time: 12:52:46 PM
* To change this template use File | Settings | File Templates.
*/
public class MutableConverterBuilder<S>
{
   private MutableValueModel<S> source;

   public MutableConverterBuilder(MutableValueModel<S> source)
   {
      this.source = source;
   }

   public <T> MutableValueModel<T> using(Converter<T, S> converter)
   {
      return new ConvertingMutableValueModel<T, S>(source, converter);
   }
}

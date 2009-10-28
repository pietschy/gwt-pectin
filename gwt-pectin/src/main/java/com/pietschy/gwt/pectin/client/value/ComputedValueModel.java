/*
 * Copyright 2009 Andrew Pietsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.pietschy.gwt.pectin.client.value;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Oct 28, 2009
 * Time: 11:14:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class ComputedValueModel<T,S> extends AbstractComputedValueModel<T,S>
{
   private Function<T,S> function;

   public ComputedValueModel(ValueModel<S> source, Function<T,S> function)
   {
      super(source);

      if (function == null)
      {
         throw new NullPointerException("function is null");
      }

      this.function = function;

   }

   protected T computeValue(S value)
   {
      return function.compute(value);
   }
}

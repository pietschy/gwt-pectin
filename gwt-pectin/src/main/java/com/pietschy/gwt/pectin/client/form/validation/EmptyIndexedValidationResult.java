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

package com.pietschy.gwt.pectin.client.form.validation;

import java.util.Collections;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Nov 20, 2009
 * Time: 12:38:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmptyIndexedValidationResult extends EmptyValidationResult implements IndexedValidationResult
{
   public int size()
   {
      return 0;
   }

   public ValidationResult getUnindexedResult()
   {
      return EmptyValidationResult.INSTANCE;
   }

   public ValidationResult getIndexedResult(int index)
   {
      return EmptyValidationResult.INSTANCE;
   }

   public Set<Integer> getResultIndicies()
   {
      return Collections.emptySet();
   }

   
}

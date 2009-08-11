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

package com.pietschy.gwt.pectin.demo.client.validation;

import com.pietschy.gwt.pectin.client.validation.ListValidator;
import com.pietschy.gwt.pectin.client.validation.IndexedValidationResultCollector;
import com.pietschy.gwt.pectin.client.validation.message.ErrorMessage;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 13, 2009
 * Time: 4:07:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class WineListValidator implements ListValidator<Wine>
{
   public void validate(List<? extends Wine> values, IndexedValidationResultCollector results)
   {
      if (values.size() < 1)
      {
         results.add(new ErrorMessage("Please select at least one wine"));
      }
   }
}

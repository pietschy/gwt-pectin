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


import com.pietschy.gwt.pectin.client.form.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.form.validation.message.ValidationMessageImpl;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * FormattedFieldValidatorImpl Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created September 22, 2009
 * @since   1.0
 */
public class FormattedFieldValidatorImplTest
{
   @Test(dataProvider = "validateTestData")
   public void validateReturnOnSeverities(final Severity[] severities, boolean isValid)
   {
      // non of these provide any test behaviour, just here to stop null pointers
      FormattedFieldModel<Integer> mock = (FormattedFieldModel<Integer>) mock(FormattedFieldModel.class);
      when(mock.getValue()).thenReturn(1);
      ValueHolder<String> textModel = new ValueHolder<String>("blah");
      when(mock.getTextModel()).thenReturn(textModel);
      
      FormattedFieldValidatorImpl<Integer> fieldValidator = new FormattedFieldValidatorImpl<Integer>(mock);

      ValueHolder<Boolean> condition = new ValueHolder<Boolean>(true);
      for (final Severity severity : severities)
      {
         fieldValidator.addValidator(new Validator<Integer>()
         {
            public void validate(Integer value, ValidationResultCollector results)
            {
               results.add(new ValidationMessageImpl(severity, "blah"));
            }
         }, condition);
      }
      
      Assert.assertEquals(fieldValidator.validate(), isValid);
   }
   
   @DataProvider
   public Object[][] validateTestData()
   {
      return new Object[][] 
         {
            new Object[] {new Severity[0], true},
            new Object[] {new Severity[] {Severity.ERROR}, false},
            new Object[] {new Severity[] {Severity.WARNING}, true},
            new Object[] {new Severity[] {Severity.INFO}, true},
            new Object[] {new Severity[] {Severity.INFO, Severity.ERROR}, false},
            new Object[] {new Severity[] {Severity.WARNING, Severity.INFO}, true},
         };
   }
}

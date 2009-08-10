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

package com.pietschy.gwt.pectin.client.validation;

import com.pietschy.gwt.pectin.client.validation.Validator;
import com.pietschy.gwt.pectin.client.validation.message.ErrorMessage;
import com.pietschy.gwt.pectin.client.validation.message.Message;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.format.FormatException;
import com.pietschy.gwt.pectin.client.condition.DelegatingCondition;


/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 7:58:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldValidationBuilder<T> {
  
  FormattedFieldValidatorImpl<T> fieldValidator;
  DelegatingCondition conditionDelegate = new DelegatingCondition();

  public FormattedFieldValidationBuilder(ValidationManager validationManager, FormattedFieldModel<T> field) {
    fieldValidator = validationManager.getFieldValidator(field, true);
  }

  public ConditionBuilder usingFieldFormat() {
    
    FieldFormatValidator<T> validator = new FieldFormatValidator<T>(fieldValidator.getFieldModel());
    fieldValidator.addTextValidator(validator, conditionDelegate);
    return new DelegatingConditionBuilder(conditionDelegate);
    
  }

  public ConditionBuilder usingTextValidator(Validator<String> validator, Validator<String>... others) {
    fieldValidator.addTextValidator(validator, conditionDelegate);

    for (Validator<? super String> other : others) {
      fieldValidator.addTextValidator(other, conditionDelegate);
    }

    return new DelegatingConditionBuilder(conditionDelegate);
  }

  public ConditionBuilder using(Validator<? super T> validator, Validator<? super T>... others) {
    fieldValidator.addValidator(validator, conditionDelegate);

    for (Validator<? super T> other : others) {
      fieldValidator.addValidator(other, conditionDelegate);
    }

    return new DelegatingConditionBuilder(conditionDelegate);
  }

  private class FieldFormatValidator<T> implements Validator<String> {

    protected FormattedFieldModel<T> model;

    public FieldFormatValidator(FormattedFieldModel<T> model) {
      this.model = model;
    }

    public void validate(String value, ValidationResultCollector results) {
      Format<T> format = model.getFormat();

      try {
        format.parse(value);
      }
      catch (FormatException e) {
        if (e instanceof Message) {
          Message m = (Message) e;
          results.add(new ErrorMessage(m.getMessage(), m.getAdditionalInfo()));
        }
        else {
          results.add(new ErrorMessage(e.getMessage()));
        }
      }

    }
  }
}
package com.pietschy.gwt.pectin.client.validation;


import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.validation.message.ValidationMessageImpl;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * FieldValidatorImpl Tester.
 *
 * @author andrew
 * @version $Revision$, $Date$
 * @created September 22, 2009
 * @since 1.0
 */
public class FieldValidatorImplTest
{
   @BeforeMethod
   public void setUp()
   {
   }

   @Test(dataProvider = "validateTestData")
   public void validateReturnOnSeverities(final Severity[] severities, boolean isValid)
   {
      FieldModel<String> mock = (FieldModel<String>) mock(FieldModel.class);
      when(mock.getValue()).thenReturn("blah");

      FieldValidatorImpl<String> fieldValidator = new FieldValidatorImpl<String>(mock);

      ValueHolder<Boolean> condition = new ValueHolder<Boolean>(true);
      for (final Severity severity : severities)
      {
         fieldValidator.addValidator(new Validator<String>()
         {
            public void validate(String value, ValidationResultCollector results)
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
            new Object[]{new Severity[0], true},
            new Object[]{new Severity[]{Severity.ERROR}, false},
            new Object[]{new Severity[]{Severity.WARNING}, true},
            new Object[]{new Severity[]{Severity.INFO}, true},
            new Object[]{new Severity[]{Severity.INFO, Severity.ERROR}, false},
            new Object[]{new Severity[]{Severity.WARNING, Severity.INFO}, true},
         };
   }
}

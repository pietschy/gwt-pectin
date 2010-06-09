package com.pietschy.gwt.pectin.client;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.format.FormatException;
import com.pietschy.gwt.pectin.client.format.IntegerFormat;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.pietschy.gwt.pectin.reflect.AssertUtil.isValueChangeEventWithValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 22, 2010
 * Time: 3:36:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldModelImplTest
{
   private FormModel form;
   private Format<Integer> mockFormat;
   private FormattedFieldModelImpl<Integer> field;
   private ValueHolder<Integer> source;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      form = new FormModel();
      mockFormat = (Format<Integer>) mock(Format.class);
      source = new ValueHolder<Integer>();
      field = new FormattedFieldModelImpl<Integer>(form,
                                                   source,
                                                   mockFormat,
                                                   new DefaultFormatExceptionPolicy<Integer>(), 
                                                   Integer.class);
   }

   @Test
   public void valueChangesTriggerTextModelUpdates()
   {
      ValueChangeHandler<String> textHandler = mock(ValueChangeHandler.class);

      field.setFormat(new IntegerFormat());
      field.getTextModel().addValueChangeHandler(textHandler);

      field.setValue(42);
      field.setValue(0);

      verify(textHandler).onValueChange(isValueChangeEventWithValue("42"));
      verify(textHandler).onValueChange(isValueChangeEventWithValue("0"));
   }

   @Test
   public void sourceValueChangesTriggerTextModelUpdates()
   {
      ValueChangeHandler<String> textHandler = mock(ValueChangeHandler.class);

      field.setFormat(new IntegerFormat());
      field.getTextModel().addValueChangeHandler(textHandler);

      source.setValue(42);
      source.setValue(0);

      verify(textHandler).onValueChange(isValueChangeEventWithValue("42"));
      verify(textHandler).onValueChange(isValueChangeEventWithValue("0"));
   }

   @Test
   public void setFormatTriggersTextModelUpdate()
   {
      ValueChangeHandler<String> textHandler = mock(ValueChangeHandler.class);

      field.setValue(42);

      field.getTextModel().addValueChangeHandler(textHandler);
      field.setFormat(new IntegerFormat());
      String result = "some value";
      field.setFormat(new DummyFormat(result));

      verify(textHandler).onValueChange(isValueChangeEventWithValue("42"));
      verify(textHandler).onValueChange(isValueChangeEventWithValue(result));
   }


   @Test
   public void builderConfiguresFormatExceptionPolicy()
   {
      FormModel form = new FormModel();
      // not specifying the policy should use the default.
      FormattedFieldModel<Integer> field = form.formattedFieldOfType(Integer.class).using(new IntegerFormat()).create();
      Assert.assertNotNull(field.getFormatExceptionPolicy());
      Assert.assertEquals(field.getFormatExceptionPolicy().getClass(), DefaultFormatExceptionPolicy.class);

      // specifying the policy should work.
      FormatExceptionPolicy<Integer> customPolicy = new FormatExceptionPolicy<Integer>()
      {
         public void onFormatException(FormattedFieldModel<Integer> formattedFieldModel, FormatException e)
         {

         }
      };
      field = form.formattedFieldOfType(Integer.class).using(new IntegerFormat(), customPolicy).create();
      Assert.assertNotNull(field.getFormatExceptionPolicy());
      Assert.assertEquals(field.getFormatExceptionPolicy(), customPolicy);
   }

   private static class DummyFormat implements Format<Integer>
   {
      private final String result;

      public DummyFormat(String result)
      {
         this.result = result;
      }

      public Integer parse(String text) throws FormatException
      {
         return null;  //To change body of implemented methods use File | Settings | File Templates.
      }

      public String format(Integer value)
      {
         return result;  //To change body of implemented methods use File | Settings | File Templates.
      }
   }
}

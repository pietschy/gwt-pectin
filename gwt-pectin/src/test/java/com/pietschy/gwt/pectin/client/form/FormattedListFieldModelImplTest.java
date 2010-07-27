package com.pietschy.gwt.pectin.client.form;

import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.format.FormatException;
import com.pietschy.gwt.pectin.client.format.IntegerFormat;
import com.pietschy.gwt.pectin.client.list.ArrayListModel;
import com.pietschy.gwt.pectin.client.list.ListModelChangedEvent;
import com.pietschy.gwt.pectin.client.list.ListModelChangedHandler;
import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.pietschy.gwt.pectin.reflect.AssertUtil.assertContentEquals;
import static com.pietschy.gwt.pectin.reflect.AssertUtil.isListChangeEventWithValues;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 22, 2010
 * Time: 3:49:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedListFieldModelImplTest
{
   @Captor
   ArgumentCaptor<ListModelChangedEvent<String>> captor;
   @Mock
   private Format<Integer> mockFormat;
   
   private FormModel form;
   private FormattedListFieldModelImpl<Integer> field;
   private ArrayListModel<Integer> source;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      MockitoAnnotations.initMocks(this);

      form = new FormModel();
      source = new ArrayListModel<Integer>();
      field = new FormattedListFieldModelImpl<Integer>(form,
                                                       source,
                                                       mockFormat,
                                                       new DefaultListFormatExceptionPolicy<Integer>(),
                                                       Integer.class);
   }

   @Test
   public void valueChangesTriggerTextModelUpdates()
   {
      ListModelChangedHandler<String> textHandler = mock(ListModelChangedHandler.class);


      field.setFormat(new IntegerFormat());
      field.getTextModel().addListModelChangedHandler(textHandler);

      field.setElements(Arrays.asList(42, 0));

      verify(textHandler, times(1)).onListDataChanged(captor.capture());
      verify(textHandler).onListDataChanged(isListChangeEventWithValues("42", "0"));
   }

   @Test
   public void sourceValueChangesTriggerTextModelUpdates()
   {
      ListModelChangedHandler<String> textHandler = mock(ListModelChangedHandler.class);

      field.setFormat(new IntegerFormat());
      field.getTextModel().addListModelChangedHandler(textHandler);
      source.setElements(Arrays.asList(42, 0));
      verify(textHandler).onListDataChanged(isListChangeEventWithValues("42", "0"));
   }

   @Test
   public void setFormatTriggersTextModelUpdate()
   {
      ListModelChangedHandler<String> textHandler = mock(ListModelChangedHandler.class);

      field.setFormat(new IntegerFormat());
      field.setElements(Arrays.asList(42, 0));
      assertContentEquals(field.getTextModel().asUnmodifiableList(), "42", "0");

      field.getTextModel().addListModelChangedHandler(textHandler);
      field.setFormat(new DummyFormat("blah"));
      verify(textHandler).onListDataChanged(isListChangeEventWithValues("blah", "blah"));
   }


   @Test
   public void builderConfiguresFormatExceptionPolicy()
   {
      FormModel form = new FormModel();
      // not specifying the policy should use the default.
      FormattedListFieldModel<Integer> field = form.formattedListOfType(Integer.class).using(new IntegerFormat()).create();
      assertNotNull(field.getFormatExceptionPolicy());
      assertEquals(field.getFormatExceptionPolicy().getClass(), DefaultListFormatExceptionPolicy.class);

      // specifying the policy should work.
      ListFormatExceptionPolicy<Integer> customPolicy = new ListFormatExceptionPolicy<Integer>()
      {
         public void onFormatException(String value, List<Integer> valueList, FormatException e)
         {
         }
      };
      field = form.formattedListOfType(Integer.class).using(new IntegerFormat(), customPolicy).create();
      assertNotNull(field.getFormatExceptionPolicy());
      Assert.assertEquals(field.getFormatExceptionPolicy(), customPolicy);
   }


   @Test
   public void sanitiseTextWorksAndLeavesInvalidValuesAlone() throws FormatException
   {
      when(mockFormat.parse(eq("1.0"))).thenReturn(1);
      when(mockFormat.parse(eq("2.0"))).thenReturn(2);
      when(mockFormat.parse(eq("abc"))).thenThrow(new FormatException("blah"));

      when(mockFormat.format(eq(1))).thenReturn("1");
      when(mockFormat.format(eq(2))).thenReturn("2");

      List<String> rawValues = Arrays.asList("1.0", "2.0", "abc");

      field.getTextModel().setElements(rawValues);

      // the value should be 1 & 2 and the text should still be "1.0", "2.0" & "abc"
      assertContentEquals(field.asUnmodifiableList(), new Integer(1), new Integer(2));
      assertContentEquals(field.getTextModel().asUnmodifiableList(), rawValues);


      ListModelChangedHandler<Integer> mockVCH = (ListModelChangedHandler<Integer>) mock(ListModelChangedHandler.class);

      field.addListModelChangedHandler(mockVCH);
      field.sanitiseText();

      // the value should be 1 & 2 and the text should be "1", "2" & "abc"
      assertContentEquals(field.asUnmodifiableList(), new Integer(1), new Integer(2));
      assertContentEquals(field.getTextModel().asUnmodifiableList(), "1", "2", "abc");

      // and no event should be fired...
      verify(mockVCH, times(0)).onListDataChanged(Matchers.<ListModelChangedEvent<Integer>>any());
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
         return null;
      }

      public String format(Integer value)
      {
         return result;
      }
   }
}

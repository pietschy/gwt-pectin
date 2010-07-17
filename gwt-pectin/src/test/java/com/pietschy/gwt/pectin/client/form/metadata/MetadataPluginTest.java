package com.pietschy.gwt.pectin.client.form.metadata;


import com.pietschy.gwt.pectin.client.form.*;
import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * MetadataPlugin Tester.
 *
 * @author andrew
 * @version $Revision$, $Date$
 * @created November 15, 2009
 * @since 1.0
 */
public class MetadataPluginTest
{
   protected FormModel form;
   protected FieldModel<String> fieldA;
   protected FormattedFieldModel<Integer> fieldB;
   protected ListFieldModel<Boolean> fieldC;
   protected ValueHolder<Boolean> condition;

   @BeforeMethod
   public void setUp()
   {
      form = new FormModel();
      fieldA = form.fieldOfType(String.class).create();
      Format<Integer> format = (Format<Integer>) mock(Format.class);
      fieldB = form.formattedFieldOfType(Integer.class).using(format).create();
      fieldC = form.listOfType(Boolean.class).create();

      condition = new ValueHolder<Boolean>(false);
   }

   @Test
   public void enableWithAllFieldsCollection()
   {
      MetadataPlugin.enable(form.allFields()).when(condition);

      assertAllFieldsDisabled();

      condition.setValue(true);

      assertAllFieldsEnabled();
   }

   @Test
   public void enableWithVarArgs()
   {
      MetadataPlugin.enable(fieldA, fieldB, fieldC).when(condition);

      assertAllFieldsDisabled();

      condition.setValue(true);

      assertAllFieldsEnabled();
   }

   @Test
   public void disableWithAllFieldsCollection()
   {
      MetadataPlugin.disable(form.allFields()).when(condition);

      assertAllFieldsEnabled();

      condition.setValue(true);

      assertAllFieldsDisabled();
   }

   @Test
   public void disableWithVarArgs()
   {
      MetadataPlugin.disable(fieldA, fieldB, fieldC).when(condition);

      assertAllFieldsEnabled();

      condition.setValue(true);

      assertAllFieldsDisabled();
   }

   @Test
   public void showWithAllFieldsCollection()
   {
      MetadataPlugin.show(form.allFields()).when(condition);

      assertAllFieldsHidden();

      condition.setValue(true);

      assertAllFieldsVisible();
   }

   @Test
   public void showWithVarArgs()
   {
      MetadataPlugin.show(fieldA, fieldB, fieldC).when(condition);

      assertAllFieldsHidden();

      condition.setValue(true);

      assertAllFieldsVisible();
   }

   @Test
   public void hideWithAllFieldsCollection()
   {
      MetadataPlugin.hide(form.allFields()).when(condition);

      assertAllFieldsVisible();

      condition.setValue(true);

      assertAllFieldsHidden();
   }

   @Test
   public void hideWithVarArgs()
   {
      MetadataPlugin.hide(fieldA, fieldB, fieldC).when(condition);

      assertAllFieldsVisible();

      condition.setValue(true);

      assertAllFieldsHidden();
   }


   private void assertAllFieldsEnabled()
   {
      for (Field<?> field : form.allFields())
      {
         assertTrue(MetadataPlugin.getMetadata(field).isEnabled());
      }
   }

   private void assertAllFieldsDisabled()
   {
      for (Field<?> field : form.allFields())
      {
         assertFalse(MetadataPlugin.getMetadata(field).isEnabled());
      }
   }

   private void assertAllFieldsVisible()
   {
      for (Field<?> field : form.allFields())
      {
         assertTrue(MetadataPlugin.getMetadata(field).isVisible());
      }
   }

   private void assertAllFieldsHidden()
   {
      for (Field<?> field : form.allFields())
      {
         assertFalse(MetadataPlugin.getMetadata(field).isVisible());
      }
   }


}

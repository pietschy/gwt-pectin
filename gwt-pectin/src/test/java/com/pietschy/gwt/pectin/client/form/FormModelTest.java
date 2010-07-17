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

package com.pietschy.gwt.pectin.client.form;


import com.pietschy.gwt.pectin.client.format.Format;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

/**
 * FormModel Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created November 14, 2009
 * @since   1.0
 */
public class FormModelTest
{
   @BeforeMethod
   public void setUp()
   {
   }


   @Test
   public void allFields()
   {
      FormModel fm = new FormModel();
      FieldModel<String> fa = fm.fieldOfType(String.class).create();
      FormattedFieldModel<String> fb = fm.formattedFieldOfType(String.class).using((Format<String>) mock(Format.class)).create();
      ListFieldModel<String> fc = fm.listOfType(String.class).create();


      Collection<Field<?>> fields = fm.allFields();

      assertEquals(fields.size(), 3);
      assertTrue(fields.contains(fa));
      assertTrue(fields.contains(fb));
      assertTrue(fields.contains(fc));

      // the collection isn't live so a field added after the call to allFields shouldn't
      // contain this one.
      FieldModel<Integer> fd = fm.fieldOfType(Integer.class).create();
      assertFalse(fields.contains(fd));
   }

   @Test
   public void allFieldsExcept()
   {
      FormModel fm = new FormModel();
      FieldModel<String> fa = fm.fieldOfType(String.class).create();
      FormattedFieldModel<String> fb = fm.formattedFieldOfType(String.class).using((Format<String>) mock(Format.class)).create();
      ListFieldModel<String> fc = fm.listOfType(String.class).create();

      Collection<Field<?>> fields = fm.allFieldsExcept(fa, fc);

      assertEquals(fields.size(), 1);
      assertTrue(fields.contains(fb));

      // the collection isn't live so a field added after the call to allFields shouldn't
      // contain this one.
      FieldModel<Integer> fd = fm.fieldOfType(Integer.class).create();
      assertFalse(fields.contains(fd));
   }

}

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

package com.pietschy.gwt.pectin.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.pietschy.gwt.pectin.client.bean.BeanModelProviderGeneralTest;
import com.pietschy.gwt.pectin.client.bean.BeanModelProviderListModelTest;
import com.pietschy.gwt.pectin.client.bean.BeanModelProviderPropertyDescriptorTest;
import com.pietschy.gwt.pectin.client.bean.BeanModelProviderValueModelTest;
import com.pietschy.gwt.pectin.client.binding.FormBinderMetadataTest;
import com.pietschy.gwt.pectin.client.binding.FormBinderUiCommandTest;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBinderTest;
import com.pietschy.gwt.pectin.client.style.StyleBinderTest;
import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Nov 23, 2009
 * Time: 8:47:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class PectinTestSuite extends GWTTestSuite
{
   /**
    * Creates the TestSuite that contains the GWTTestCases.
    */
   public static Test suite()
   {
      TestSuite gwtTestSuite = new GWTTestSuite("Testing Pectin");

      // bean tests
      gwtTestSuite.addTestSuite(BeanModelProviderPropertyDescriptorTest.class);
      gwtTestSuite.addTestSuite(BeanModelProviderValueModelTest.class);
      gwtTestSuite.addTestSuite(BeanModelProviderListModelTest.class);
      gwtTestSuite.addTestSuite(BeanModelProviderGeneralTest.class);

      // Binder tests
      gwtTestSuite.addTestSuite(FormBinderUiCommandTest.class);
      gwtTestSuite.addTestSuite(FormBinderMetadataTest.class);

      // Metadata tests
      gwtTestSuite.addTestSuite(MetadataBinderTest.class);

      // style tests
      gwtTestSuite.addTestSuite(StyleBinderTest.class);

      // form model tests
      gwtTestSuite.addTestSuite(MissingPropertiesFormModelTest.class);
      
      return gwtTestSuite;
   }

}

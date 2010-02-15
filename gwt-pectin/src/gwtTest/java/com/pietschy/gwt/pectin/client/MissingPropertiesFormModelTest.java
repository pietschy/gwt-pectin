package com.pietschy.gwt.pectin.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.test.TestBeanModelProvider;


/**
 * StyleBinder Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created November 6, 2009
 * @since   1.0
 */
public class MissingPropertiesFormModelTest extends GWTTestCase
{
   private TestBeanModelProvider provider;


   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestBeanModelProvider.class);
   }

   public void testMissingPropertyBarfs()
   {
      try
      {
         new FormModel()
         {
            {
               fieldOfType(Object.class).boundTo(provider, "noSuchPropertyExists");
            }
         };

         fail("form construction was expected to fail.");
      }
      catch (Exception e)
      {

      }
   }

   public void testWrongTypeBarfs()
   {
      try
      {
         new FormModel()
         {
            {
               // wrong property type
               fieldOfType(Integer.class).boundTo(provider, "object");
            }
         };

         fail("form construction was expected to fail.");
      }
      catch (Exception e)
      {

      }
   }

   public void testMissingListPropertyBarfs()
   {
      try
      {
         new FormModel()
         {
            {
               listOfType(Object.class).boundTo(provider, "noSuchPropertyExists");
            }
         };

         fail("form construction was expected to fail.");
      }
      catch (Exception e)
      {

      }
   }

   public void testWrongListTypeBarfs()
   {
      try
      {
         new FormModel()
         {
            {
               // Integer is wrong property type, should be a string.
               listOfType(Integer.class).boundTo(provider, "set");
            }
         };

         fail("form construction was expected to fail.");
      }
      catch (Exception e)
      {

      }
   }

   public void testListWithNonObjectTypeBarfsWithUntypedCollection()
   {
      try
      {
         new FormModel()
         {
            {
               // Integer is wrong property type, should be a string.
               listOfType(Integer.class).boundTo(provider, "untypedCollection");
            }
         };

         fail("form construction was expected to fail.");
      }
      catch (Exception e)
      {

      }
   }

}
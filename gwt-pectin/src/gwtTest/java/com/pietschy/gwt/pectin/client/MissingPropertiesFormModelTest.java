package com.pietschy.gwt.pectin.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.test.TestProvider;


/**
 * These test should really be moved to AbstractBeanModelProviderTest since it's responsible
 * for throwing the actual exception.
 */
public class MissingPropertiesFormModelTest extends GWTTestCase
{
   private TestProvider provider;


   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestProvider.class);
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
               listOfType(Integer.class).boundTo(provider, "collections.stringCollection");
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
               listOfType(Integer.class).boundTo(provider, "collections.untypedCollection");
            }
         };

         fail("form construction was expected to fail.");
      }
      catch (Exception e)
      {

      }
   }

}
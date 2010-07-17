package com.google.gwt.sample.contacts.client.view;


import com.google.gwt.sample.contacts.shared.Contact;
import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;
import com.pietschy.gwt.pectin.client.form.metadata.Metadata;
import com.pietschy.gwt.pectin.client.form.metadata.MetadataPlugin;
import com.pietschy.gwt.pectin.client.form.validation.HasValidation;
import com.pietschy.gwt.pectin.reflect.ReflectionProviders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * EditContactViewModel Tester.
 *
 * @author andrew
 * @version $Revision$, $Date$
 * @created January 19, 2010
 * @since 1.0
 */
public class EditContactViewModelTest
{
   private EditContactModel model;

   @BeforeMethod
   public void setUp()
   {
      // we'll use our jvm provider for the tests.
      BeanModelProvider<Contact> contactProvider = ReflectionProviders.providerFor(Contact.class);

      model = new EditContactModel(contactProvider);
   }

   @Test
   public void setContact()
   {
      Contact contact = createContact();

      model.setContact(contact);

      assertEquals(model.firstName.getValue(), "Joe");
      assertEquals(model.lastName.getValue(), "Blogs");
      assertEquals(model.emailAddress.getValue(), "joe@bloggy.com");
   }

   @Test
   public void commit()
   {
      Contact contact = createContact();
      model.setContact(contact);

      model.firstName.setValue("Joseph");
      model.lastName.setValue("Blogs-Smith");
      model.emailAddress.setValue("joseph@blogsmithy.com");

      // values should still be the same on the bean till we commit.
      assertEquals(contact.getFirstName(), "Joe");
      assertEquals(contact.getLastName(), "Blogs");
      assertEquals(contact.getEmailAddress(), "joe@bloggy.com");

      model.commit();

      assertEquals(contact.getFirstName(), "Joseph");
      assertEquals(contact.getLastName(), "Blogs-Smith");
      assertEquals(contact.getEmailAddress(), "joseph@blogsmithy.com");
   }

   // An example of testing the validation of a specific field.

   @Test(dataProvider = "firstNameValidationData")
   public void validateFirstName(String value, boolean valid)
   {
      model.setContact(createContact());
      model.firstName.setValue(value);
      HasValidation validator = model.getValidationManager().getValidator(model.firstName);
      assertEquals(validator.validate(), valid);
   }

   @DataProvider
   public Object[][] firstNameValidationData()
   {
      return new Object[][]
         {
            new Object[]{null, false},
            new Object[]{"", false},
            new Object[]{" ", false},
            new Object[]{"Joe", true},
         };
   }

   @Test
   public void waterMarkMetadataIsConfigured()
   {
      // and example of using the plugin to fetch the metadata for testing.
      Metadata metadata = MetadataPlugin.getMetadata(model.firstName);

      assertEquals(metadata.getWatermark(), "Required");

   }


   private Contact createContact()
   {
      return new Contact("0", "Joe", "Blogs", "joe@bloggy.com");
   }

}

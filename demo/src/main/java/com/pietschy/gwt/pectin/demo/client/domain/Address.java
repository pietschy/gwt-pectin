package com.pietschy.gwt.pectin.demo.client.domain;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 30, 2010
 * Time: 9:56:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Address
{
   private String addressOne = "1 Orchard Drv";
   private String addressTwo;
   private String suburb = "Fruitville";
   private String postCode = "1234";

   public String getAddressOne()
   {
      return addressOne;
   }

   public void setAddressOne(String addressOne)
   {
      this.addressOne = addressOne;
   }

   public String getAddressTwo()
   {
      return addressTwo;
   }

   public void setAddressTwo(String addressTwo)
   {
      this.addressTwo = addressTwo;
   }

   public String getSuburb()
   {
      return suburb;
   }

   public void setSuburb(String suburb)
   {
      this.suburb = suburb;
   }

   public String getPostCode()
   {
      return postCode;
   }

   public void setPostCode(String postCode)
   {
      this.postCode = postCode;
   }
}

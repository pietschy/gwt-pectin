package com.pietschy.gwt.pectin.client.bean.data;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 23, 2010
 * Time: 4:00:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class DtoWithPublicFields
{
   // read only field
   public final String id;

   // mutable field..
   public String name;

   public DtoWithPublicFields(String id)
   {
      this.id = id;
   }
}

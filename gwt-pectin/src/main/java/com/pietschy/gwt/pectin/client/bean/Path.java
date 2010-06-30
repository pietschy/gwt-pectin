package com.pietschy.gwt.pectin.client.bean;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 25, 2010
 * Time: 9:53:11 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Path
{
   String getFullPath();

   String getParentPath();

   String getPropertyName();

   boolean isTopLevel();
}

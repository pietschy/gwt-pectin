package com.pietschy.gwt.pectin.rebind;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 13, 2010
 * Time: 12:50:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Visitor<T>
{
   void visit(T subject);
}

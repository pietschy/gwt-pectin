package com.pietschy.gwt.pectin.client.bean;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Mar 26, 2010
* Time: 11:42:27 AM
* To change this template use File | Settings | File Templates.
*/
public interface PropertyModelVisitor
{
   public void visit(BeanPropertyModelBase model);
}
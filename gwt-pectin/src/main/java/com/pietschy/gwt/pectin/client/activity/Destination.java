package com.pietschy.gwt.pectin.client.activity;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 23, 2010
 * Time: 10:05:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Destination<T>
{
   public void receive(T value);
}
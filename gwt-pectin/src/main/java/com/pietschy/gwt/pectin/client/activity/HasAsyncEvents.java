package com.pietschy.gwt.pectin.client.activity;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 18, 2010
 * Time: 1:47:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HasAsyncEvents<R, E>
{
   AsyncEvents<R, E> always();

   AsyncEvents<R,E> onNextCall();

//   AsyncEvents<R, E> when(ValueModel<Boolean> condition);
}

package com.pietschy.gwt.pectin.client.bean;

/**
 * CopyingBeanModelProvider is allows for the state of value models to be copied to and from
 * underlying beans.  State can be read from one bean, updated by the form and then copied to
 * another bean.  This you to apply changes to a clone of the original object.  It also allows
 * you to track dirty state, and only update it after the values have been copied to a bean and
 * successfully updated via RPC.
 */
public abstract class CopyingBeanModelProvider<B> extends AbstractBeanModelProvider<B>
{
   public CopyingBeanModelProvider()
   {
   }

   @Override
   public void copyTo(B bean, boolean clearDirtyState)
   {
      super.copyTo(bean, clearDirtyState);
   }

   @Override
   public void readFrom(B bean)
   {
      super.readFrom(bean);
   }

   @Override
   public void checkpoint()
   {
      super.checkpoint();
   }

   @Override
   public void revertToCheckpoint()
   {
      super.revertToCheckpoint();
   }
}

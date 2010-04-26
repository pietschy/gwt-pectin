package com.pietschy.gwt.pectin.client.bean;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.value.HasValueGetter;

/**
 * CopyingBeanModelProvider is allows for the state of value models to be copied to and from
 * underlying beans.  State can be read from one bean, updated by the form and then copied to
 * another bean.  This you to apply changes to a clone of the original object.
 * <p>
 * Another important features is that this provider gives you control over when the dirty state
 * is recomputed.  Thus you can copy the state to a bean, initiate an RPC call an only clear
 * the dirty state once the call succeeds.
 * @see #copyTo(Object, boolean)
 * @see #readFrom(Object)
 */
public abstract class CopyingBeanModelProvider<B> extends AbstractBeanModelProvider<B>
{
   protected CheckpointCommand checkpointCommand = new CheckpointCommand();
   protected RevertCommand revertToCheckpointCommand = new RevertCommand();

   public CopyingBeanModelProvider()
   {
   }

   @Override
   public void copyTo(B bean, boolean clearDirtyState)
   {
      super.copyTo(bean, clearDirtyState);
   }

   /**
    * This method copies all of the providers state into the value held by the specified source.  The value
    * will be updated in place and is equivalent to calling <code>copyTo(source.getValue(), boolean)</code>
    * @param source the source of the value.
    * @param clearDirtyState true to clear the dirty state of the provider, false to leave it as is.
    */
   public void copyTo(HasValueGetter<B> source, boolean clearDirtyState)
   {
      super.copyTo(source.getValue(), clearDirtyState);
   }

   @Override
   public void readFrom(B bean)
   {
      super.readFrom(bean);
   }

   public void readFrom(HasValueGetter<B> source)
   {
      readFrom(source.getValue());
   }

   @Override
   public void checkpoint()
   {
      super.checkpoint();
   }

   public Command checkpointCommand()
   {
      return checkpointCommand;
   }

   @Override
   public void revertToCheckpoint()
   {
      super.revertToCheckpoint();
   }

   public Command revertToCheckpointCommand()
   {
      return revertToCheckpointCommand;
   }

   private class CheckpointCommand implements Command
   {
      public void execute()
      {
         checkpoint();
      }
   }

   private class RevertCommand implements Command
   {
      public void execute()
      {
         revertToCheckpoint();
      }
   }
}

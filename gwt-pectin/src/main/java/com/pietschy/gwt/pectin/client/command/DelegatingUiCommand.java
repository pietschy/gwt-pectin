package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.binding.Disposable;
import com.pietschy.gwt.pectin.client.value.DelegatingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import static com.pietschy.gwt.pectin.client.condition.Conditions.is;

/**
 * DelegatingUiCommands delegate their behaviour to another command.  This is useful when different command
 * implementations are used at runtime.  DelegatingUiCommand also supports additional constraints placed on
 * it's enabled state.  Thus it's possible to have a command disable itself even when it's delegate is
 * enabled.  The DelegatingUiCommand will never be enabled if it's delegate is disabled.
 */
public class DelegatingUiCommand extends UiCommandSupport implements TemporalUiCommand, Disposable
{
   private Object debugInfo;

   // create our delegating models all with default values of false.
   private DelegatingValueModel<Boolean> delegateActive = new DelegatingValueModel<Boolean>(false);
   private DelegatingValueModel<Boolean> delegateEnabled = new DelegatingValueModel<Boolean>(false);
   private EventSupport eventSupport = new EventSupport();

   // our enabled state is a combination of the our enabled state and that of the delegates.
   // we call super.enabled() as we've overridden it to return `compoundEnabled`.
   private ValueModel<Boolean> compoundEnabled = is(delegateEnabled).and(super.enabled());

   private UiCommand delegate;


   public DelegatingUiCommand()
   {
   }

   public DelegatingUiCommand(UiCommand delegate)
   {
      setDelegate(delegate);
   }

   public DelegatingUiCommand withDebugContext(Object debugInfo)
   {
      this.debugInfo = debugInfo;
      return this;
   }


   public Events onNextCall()
   {
      return eventSupport.onNextCall();
   }

   public Events always()
   {
      return eventSupport.always();
   }

   public void execute()
   {
      if (delegate == null)
      {
         throw new MissingDelegateException(debugInfo);
      }

      runWithInterceptors(new Command()
      {
         public void execute()
         {
            LifeCycleCallback eventCallback = eventSupport.prepareEvents().asLifeCycleCallback();
            delegate.onNextCall().sendAllEventsTo(eventCallback);
            delegate.execute();
         }
      });

   }

   public ValueModel<Boolean> active()
   {
      return delegateActive;
   }

   public ValueModel<Boolean> enabled()
   {
      return compoundEnabled;
   }

   public void setDelegate(UiCommand delegate)
   {
      this.delegate = delegate;
      delegateEnabled.setDelegate(delegate != null ? delegate.enabled() : null);
      if (delegate instanceof TemporalUiCommand)
      {
         delegateActive.setDelegate(((TemporalUiCommand) delegate).active());
      }
      else
      {
         delegateActive.setDelegate(null);
      }
   }

   public void dispose()
   {
      setDelegate(null);
   }
}

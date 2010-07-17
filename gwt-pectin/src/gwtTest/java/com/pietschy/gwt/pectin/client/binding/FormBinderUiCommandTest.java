package com.pietschy.gwt.pectin.client.binding;


import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Button;
import com.pietschy.gwt.pectin.client.command.AbstractUiCommand;
import com.pietschy.gwt.pectin.client.command.TemporalUiCommand;
import com.pietschy.gwt.pectin.client.form.binding.FormBinder;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;


/**
 * StyleBinder Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created November 6, 2009
 * @since   1.0
 */
public class FormBinderUiCommandTest extends GWTTestCase
{
   private Button button;
   private FormBinder binder;
   private ValueHolder<Boolean> enabled;
   private ValueHolder<Boolean> active;

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Override
   protected void gwtSetUp() throws Exception
   {
      button = new Button("b1");
      binder = new FormBinder();
      enabled = new ValueHolder<Boolean>(true);
      active = new ValueHolder<Boolean>(false);
   }

   public void testBindTracksEnabled()
   {
      TestUiCommand command = new TestUiCommand(enabled, active);

      enabled.setValue(false);

      binder.bind(command).to(button);

      // start of with no style
      assertFalse("button is disabled after bind", button.isEnabled());

      enabled.setValue(true);

      // start of with no style
      assertTrue("button is enabled after enabled state change", button.isEnabled());
   }

//  the call to button.click() doesn't seem to work in the unit tests...
//   public void testClickExecutesCommand()
//   {
//
//      final boolean[] clicked = new boolean[1];
//      TestUiCommand command = new TestUiCommand(enabled, active)
//      {
//
//         @Override
//         protected void doExecute()
//         {
//            clicked[0] = true;
//         }
//      };
//
//
//      binder.bind(command).to(button);
//
//      assertFalse("execute not yet called", clicked[0]);
//      button.click();
//      assertTrue("execute called", clicked[0]);
//   }



   private static class TestUiCommand extends AbstractUiCommand implements TemporalUiCommand
   {
      private ValueHolder<Boolean> active;

      private TestUiCommand(ValueHolder<Boolean> enabled, ValueHolder<Boolean> active)
      {
         enableWhen(enabled);
         this.active = active;
      }

      public ValueModel<Boolean> active()
      {
         return active;
      }

      @Override
      protected void doExecute()
      {

      }
   }

}
/*
 * Copyright 2009 Andrew Pietsch 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you 
 * may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at 
 *      
 *      http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing permissions 
 * and limitations under the License. 
 */

package com.pietschy.gwt.pectin.client.components;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.BlurEvent;
import com.pietschy.gwt.pectin.client.list.ListModel;
import com.pietschy.gwt.pectin.client.list.ArrayListModel;
import com.pietschy.gwt.pectin.client.list.ListModelChangedHandler;
import com.pietschy.gwt.pectin.client.list.ListModelChangedEvent;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplay;
import com.pietschy.gwt.pectin.client.validation.component.StyleApplicator;
import com.pietschy.gwt.pectin.client.validation.ValidationResult;
import com.pietschy.gwt.pectin.client.metadata.HasEnabled;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public abstract class AbstractComboBoxWithOther<T> extends Composite
implements HasValue<T>, HasEnabled, ValidationDisplay
{
   private static final Object OTHER_OBJECT = new Object();

   private InternalListModel internalModel;
   private ComboBox<Object> comboBox;
   private HasValue<T> otherEditor;

   private HTML textFieldGap = new HTML("&nbsp;");
   protected FocusMonitor focusMonitor = new FocusMonitor();

   protected AbstractComboBoxWithOther(T... values)
   {
      this(new ArrayListModel<T>(values));
   }

   protected AbstractComboBoxWithOther(Collection<T> values)
   {
      this(new ArrayListModel<T>(values));
   }

   public AbstractComboBoxWithOther(ListModel<T> model)
   {
      internalModel = new InternalListModel(model);
      comboBox = new ComboBox<Object>(internalModel);
      setRenderer(ComboBox.DEFAULT_RENDERER); // important as it configures our delegating renderer...

      otherEditor = createOtherEditor();
      if (!(otherEditor instanceof Widget))
      {
         throw new IllegalStateException("createOtherEditor must return a subclass of Widget");
      }

      comboBox.addValueChangeHandler(new ValueChangeHandler<Object>()
      {
         public void onValueChange(ValueChangeEvent<Object> event)
         {
            handleComboValueChange();
         }
      });

      comboBox.addFocusHandler(focusMonitor);
      comboBox.addBlurHandler(focusMonitor);

      otherEditor.addValueChangeHandler(new ValueChangeHandler<T>()
      {
         public void onValueChange(ValueChangeEvent<T> event)
         {
            handleEditorValueChange();
         }
      });

      internalModel.addListModelChangedHandler(new ListModelChangedHandler<Object>()
      {
         public void onListDataChanged(ListModelChangedEvent<Object> event)
         {
            updateViewState();
         }
      });

      HorizontalPanel p = new HorizontalPanel();
      p.add(comboBox);
      p.add(textFieldGap);
      p.add((Widget) otherEditor);
      initWidget(p);
      setStylePrimaryName("gwt-pecting-ComboBoxWithOther");
      getElement().getStyle().setProperty("backgroundColor", "transparent");
      updateViewState();
   }

   private void handleComboValueChange()
   {
      updateViewState();
      fireValueChanged();
   }

   private void handleEditorValueChange()
   {
      fireValueChanged();
   }

   protected abstract HasValue<T> createOtherEditor();

   public void setRenderer(final ComboBox.Renderer<? super T> renderer)
   {
      comboBox.setRenderer(new ComboBox.Renderer<Object>()
      {
         public String toDisplayString(Object value)
         {
            return (OTHER_OBJECT.equals(value)) ? "Other..." : renderer.toDisplayString((T) value);
         }
      });
   }

   private void
   updateViewState()
   {
      boolean otherSelected = isOtherSelected();
      ((Widget) otherEditor).setVisible(otherSelected);
      textFieldGap.setVisible(otherSelected);
      
      // if the combo was focused we transfer the focus to the other
      // editor.  Make this check ensures we don't steal the focus if
      // the change was due to a model event.
      if (focusMonitor.isFocused())
      {
         if (otherSelected && otherEditor instanceof Focusable)
         {
            ((Focusable) otherEditor).setFocus(true);
         }
      }
   }

   private boolean
   isOtherSelected()
   {
      return OTHER_OBJECT.equals(comboBox.getValue());
   }

   @SuppressWarnings("unchecked")
   public T getValue()
   {
      return isOtherSelected() ? otherEditor.getValue() : (T) comboBox.getValue();
   }

   public void setValue(T value)
   {
      setValue(value, false);
   }

   public void setValue(T value, boolean fireEvents)
   {
      if (value == null)
      {
         comboBox.setValue(null);
      }
      else
      {
         // there is a value to set so we see if it matches
         // one of the values we were given at construction.
         if (isKnownValue(value))
         {
            comboBox.setValue(value);
         }
         else
         {
            // no match so it must be an other value...
            comboBox.setValue(OTHER_OBJECT);
            otherEditor.setValue(value);
         }
      }

      updateViewState();
      if (fireEvents)
      {
         fireValueChanged();
      }
   }

   public void setEnabled(boolean enabled)
   {
      comboBox.setEnabled(enabled);
      setOtherEditorEnabled(enabled);
   }

   protected void setOtherEditorEnabled(boolean enabled)
   {
      if (otherEditor instanceof HasEnabled)
      {
         ((HasEnabled) otherEditor).setEnabled(enabled);
      }
      else if (otherEditor instanceof FocusWidget)
      {
         ((FocusWidget) otherEditor).setEnabled(enabled);
      }
      else
      {
         throw new IllegalStateException("otherEditor doesn't extend FocusWidget or implement HasEnabled");
      }
   }

   public boolean isEnabled()
   {
      return comboBox.isEnabled();
   }

   public void setValidationResult(ValidationResult result)
   {
      StyleApplicator.defaultInstance().applyStyles(comboBox, result);
      StyleApplicator.defaultInstance().applyStyles((UIObject) otherEditor, result);
   }

   public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler)
   {
      return addHandler(handler, ValueChangeEvent.getType());
   }

   protected void fireValueChanged()
   {
      ValueChangeEvent.fire(this, getValue());
   }


   protected boolean
   isKnownValue(T value)
   {
      for (Object modelValue : internalModel)
      {
         if (modelValue.equals(value))
         {
            return true;
         }
      }

      return false;
   }

   public void
   tryFocus()
   {
      if (isOtherSelected() && otherEditor instanceof Focusable)
      {
         ((Focusable) otherEditor).setFocus(true);
      }
      else
      {
         comboBox.setFocus(true);
      }
   }

   private class InternalListModel extends ArrayListModel<Object>
   {
      private HandlerRegistration sourceListenerRegistration;
      private ListModel<T> sourceModel;

      private InternalListModel(ListModel<T> sourceModel)
      {
         setSourceModel(sourceModel);
      }

      private ListModelChangedHandler<T> sourceListener = new ListModelChangedHandler<T>()
      {
         public void onListDataChanged(ListModelChangedEvent<T> event)
         {
            rebuild();
         }
      };

      public void setSourceModel(ListModel<T> model)
      {
         if (sourceListenerRegistration != null)
         {
            sourceListenerRegistration.removeHandler();
         }

         sourceModel = model;

         sourceListenerRegistration = sourceModel.addListModelChangedHandler(sourceListener);

         rebuild();
      }

      private void rebuild()
      {
         ArrayList<Object> newContent = new ArrayList<Object>(sourceModel.asUnmodifiableList());
         newContent.add(OTHER_OBJECT);
         setElements(newContent);
      }
   }

   private class FocusMonitor implements FocusHandler, BlurHandler
   {
      private boolean focused = false;

      public void onFocus(FocusEvent event)
      {
         focused = true;
      }

      public void onBlur(BlurEvent event)
      {
         focused = false;
      }

      public boolean isFocused()
      {
         return focused;
      }
   }
}
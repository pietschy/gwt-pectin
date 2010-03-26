package com.pietschy.gwt.pectin.client.bean;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.pietschy.gwt.pectin.util.TestBean;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 26, 2010
 * Time: 11:44:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyModelRegistryTest
{
   private HashMap<BeanPropertyModelBase<TestBean>, ValueHolder<Boolean>> dirtyModels;
   private PropertyModelRegistry<TestBean> registry;
   private BeanPropertyValueModel<TestBean, String> stringValue;
   private BeanPropertyValueModel<TestBean, Object> objectValue;
   private BeanPropertyListModel<TestBean, String> stringList;
   private PropertyKey<String> stringValueKey;
   private PropertyKey<Object> objectValueKey;
   private PropertyKey<String> stringListKey;

   @BeforeTest
   public void setUp()
   {
      dirtyModels = new HashMap<BeanPropertyModelBase<TestBean>, ValueHolder<Boolean>>();
      registry = new PropertyModelRegistry<TestBean>();

      stringValue = createValueMock(String.class);
      objectValue = createValueMock(Object.class);
      stringList = createListMock(String.class);

      stringValueKey = new PropertyKey<String>(String.class, "string");
      objectValueKey = new PropertyKey<Object>(Object.class, "object");
      stringListKey = new PropertyKey<String>(String.class, "set");

   }

   @SuppressWarnings("unchecked")
   private <T> BeanPropertyValueModel<TestBean, T> createValueMock(Class<T> clazz)
   {
      BeanPropertyValueModel<TestBean, T> model = (BeanPropertyValueModel<TestBean, T>) Mockito.mock(BeanPropertyValueModel.class);
      ValueHolder<Boolean> dirty = new ValueHolder<Boolean>();
      dirtyModels.put(model, dirty);
      when(model.getDirtyModel()).thenReturn(dirty);
      return model;
   }

   @SuppressWarnings("unchecked")
   private <T> BeanPropertyListModel<TestBean, T> createListMock(Class<T> clazz)
   {
      BeanPropertyListModel<TestBean, T> model = (BeanPropertyListModel<TestBean, T>) Mockito.mock(BeanPropertyListModel.class);
      ValueHolder<Boolean> dirty = new ValueHolder<Boolean>();
      dirtyModels.put(model, dirty);
      when(model.getDirtyModel()).thenReturn(dirty);
      return model;
   }

   @Test
   public void ensureVisitorHonoursOrderOfAddition()
   {
      registry.add(stringValueKey, stringValue);
      registry.add(stringListKey, stringList);
      registry.add(objectValueKey, objectValue);

      final ArrayList<BeanPropertyModelBase<TestBean>> collection = new ArrayList<BeanPropertyModelBase<TestBean>>();

      registry.withEachModel(new PropertyModelVisitor<TestBean>()
      {
         public void visit(BeanPropertyModelBase<TestBean> model)
         {
            collection.add(model);
         }
      });

      assertEquals(collection.size(), 3);
      assertEquals(stringValue, collection.get(0));
      assertEquals(stringList, collection.get(1));
      assertEquals(objectValue, collection.get(2));
   }

   @Test
   public void getReturnsSameModelAsAdd()
   {
      registry.add(stringValueKey, stringValue);
      registry.add(stringListKey, stringList);
      registry.add(objectValueKey, objectValue);

      assertEquals(registry.getValueModel(stringValueKey), stringValue);
      assertEquals(registry.getValueModel(objectValueKey), objectValue);
      assertEquals(registry.getListModel(stringListKey), stringList);

   }

   @Test
   public void ensureDirtyDoesntChangeTillAfterVisit()
   {
      registry.add(stringValueKey, stringValue);
      registry.add(stringListKey, stringList);
      registry.add(objectValueKey, objectValue);

      final ValueModel<Boolean> dirtyModel = registry.getDirtyModel();
      final AtomicBoolean dirtyChanged = new AtomicBoolean(false);

      assertFalse(dirtyModel.getValue());

      dirtyModel.addValueChangeHandler(new ValueChangeHandler<Boolean>()
      {
         public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent)
         {
            dirtyChanged.set(true);
         }
      });

      registry.withEachModel(new PropertyModelVisitor<TestBean>()
      {
         public void visit(BeanPropertyModelBase<TestBean> model)
         {
            dirtyModels.get(model).setValue(true);

            // dirty shouldn't change while we're iterating.
            assertFalse(dirtyChanged.get());
            assertFalse(dirtyModel.getValue());
         }
      });

      assertTrue(dirtyChanged.get());
      assertTrue(dirtyModel.getValue());

   }


}

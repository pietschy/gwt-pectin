package com.pietschy.gwt.pectin.client.bean;

import com.pietschy.gwt.pectin.client.condition.OrFunction;
import com.pietschy.gwt.pectin.client.value.ReducingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 26, 2010
 * Time: 11:31:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyModelRegistry<B>
{
   private LinkedHashMap<PropertyKey<?>, BeanPropertyModelBase<B>> allModels = new LinkedHashMap<PropertyKey<?>, BeanPropertyModelBase<B>>();

   private HashMap<PropertyKey<?>, BeanPropertyValueModel<B, ?>> valueModels = new HashMap<PropertyKey<?>, BeanPropertyValueModel<B, ?>>();
   private HashMap<PropertyKey<?>, BeanPropertyListModel<B, ?>> listModels = new HashMap<PropertyKey<?>, BeanPropertyListModel<B, ?>>();

   private ReducingValueModel<Boolean, Boolean> dirtyModel = new ReducingValueModel<Boolean, Boolean>(new OrFunction());


   public ValueModel<Boolean> getDirtyModel()
   {
      return dirtyModel;
   }

   /**
    * Visits each model in the order it was registered.  This method also holds prevents
    * any changes to the dirty state until after the visitor has finished.  This is mainly
    * to prevent the dirty model from recomputing needlessly if the visitor is updating the
    * dirty state of each model.
    * 
    * @param visitor the visitor.
    */
   public void withEachModel(final PropertyModelVisitor<B> visitor)
   {
      // we only update the dirty model after we've finished updating all
      // the models, otherwise we'll get a lot of recomputing for nothing.
      dirtyModel.recomputeAfterRunning(new Runnable()
      {
         public void run()
         {
            // performance could be improved here if the dirty model went deaf for a bit.
            for (BeanPropertyModelBase<B> model : allModels.values())
            {
               visitor.visit(model);
            }
         }
      });
   }

   @SuppressWarnings("unchecked")
   public <T> BeanPropertyValueModel<B, T> getValueModel(PropertyKey<T> key)
   {
      return (BeanPropertyValueModel<B,T>) valueModels.get(key);
   }

   @SuppressWarnings("unchecked")
   public <T> BeanPropertyListModel<B, T> getListModel(PropertyKey<T> key)
   {
      return (BeanPropertyListModel<B,T>) listModels.get(key);
   }

   public <T> void add(PropertyKey<T> key, BeanPropertyListModel<B, T> listModel)
   {
      addCommon(key, listModel);
      listModels.put(key, listModel);
   }

   public <T> void add(PropertyKey<T> key, BeanPropertyValueModel<B, T> valueModel)
   {
      addCommon(key, valueModel);
      valueModels.put(key, valueModel);
   }

   private <T> void addCommon(PropertyKey<T> key, BeanPropertyModelBase<B> valueModel)
   {
      allModels.put(key, valueModel);
      dirtyModel.addSourceModel(valueModel.getDirtyModel());
   }

}

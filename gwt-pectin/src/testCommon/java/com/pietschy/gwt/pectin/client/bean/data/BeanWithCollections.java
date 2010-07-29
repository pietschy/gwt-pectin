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

package com.pietschy.gwt.pectin.client.bean.data;

import java.util.*;

/**
 * We extends AbstractTestBean so we ensure we pick up super class properties.
 */
public class BeanWithCollections
{
   private Collection<String> stringCollection;
   private Collection<Integer> integerCollection;
   private List<String> stringList;
   private SortedSet<String> stringSortedSet;
   private Collection untypedCollection;
   private Collection readOnlyUntypedCollection = Arrays.asList("abc", "def", "ghi");
   private ArrayList<String> unsupportedCollection;

   public Collection<String> getStringCollection()
   {
      return stringCollection;
   }

   public void setStringCollection(Collection<String> stringCollection)
   {
      this.stringCollection = stringCollection;
   }

   public Collection<Integer> getIntegerCollection()
   {
      return integerCollection;
   }

   public void setIntegerCollection(Collection<Integer> integerCollection)
   {
      this.integerCollection = integerCollection;
   }

   public List<String> getStringList()
   {
      return stringList;
   }

   public void setStringList(List<String> stringList)
   {
      this.stringList = stringList;
   }

   public SortedSet<String> getStringSortedSet()
   {
      return stringSortedSet;
   }

   public void setStringSortedSet(SortedSet<String> stringSortedSet)
   {
      this.stringSortedSet = stringSortedSet;
   }

   public Collection getUntypedCollection()
   {
      return untypedCollection;
   }

   public void setUntypedCollection(Collection untypedCollection)
   {
      this.untypedCollection = untypedCollection;
   }

   public Collection getReadOnlyUntypedCollection()
   {
      return readOnlyUntypedCollection;
   }

   public ArrayList<String> getUnsupportedCollection()
   {
      return unsupportedCollection;
   }

   public void setUnsupportedCollection(ArrayList<String> unsupportedCollection)
   {
      this.unsupportedCollection = unsupportedCollection;
   }
}
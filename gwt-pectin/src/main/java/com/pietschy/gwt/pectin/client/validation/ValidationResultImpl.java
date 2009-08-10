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

package com.pietschy.gwt.pectin.client.validation;

import com.pietschy.gwt.pectin.client.validation.message.ValidationMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 
 */
public class ValidationResultImpl 
implements ValidationResult, ValidationResultCollector
{
   private ArrayList<ValidationMessage> results = new ArrayList<ValidationMessage>();
   private HashMap<Severity, List<ValidationMessage>> severityMap = new HashMap<Severity, List<ValidationMessage>>();

   // this is lazily created using lazyGetIndexedResults
   private HashMap<Integer, ValidationResultImpl> indexedResults;

   
   public ValidationResultImpl()
   {
   }

   public void
   add(ValidationMessage message)
   {
      results.add(message);
      prepareSeverityList(message.getSeverity()).add(message);
   }

   public void
   addAll(Collection<ValidationMessage> messages)
   {
      for (ValidationMessage message : messages)
      {
         add(message);
      }
   }

   public boolean
   isEmpty()
   {
      return results.isEmpty();
   }

   public boolean
   contains(Severity severity)
   {
      return severityMap.containsKey(severity);
   }

   public List<ValidationMessage>
   getMessages()
   {
      return Collections.unmodifiableList(results);
   }

   public List<ValidationMessage>
   getMessages(Severity severity)
   {
      return Collections.unmodifiableList(prepareSeverityList(severity));
   }

   protected List<ValidationMessage>
   prepareSeverityList(Severity severity)
   {
      List<ValidationMessage> list = severityMap.get(severity);
      
      if (list == null)
      {
         list = new ArrayList<ValidationMessage>();
         severityMap.put(severity, list);
      }
      
      return list;
   }
}
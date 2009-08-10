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

import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * 
 */
public class IndexedValidationResultImpl 
implements IndexedValidationResult, IndexedValidationResultCollector
{
   private TreeMap<Integer, ValidationResult> results = new TreeMap<Integer, ValidationResult>();
   
   public IndexedValidationResultImpl()
   {
   }

   public void
   add(int index, ValidationMessage message)
   {
      prepareIndexedResult(index).add(message);
   }

   public boolean
   isEmpty()
   {
      return results.isEmpty();
   }

   public List<ValidationMessage> getMessages()
   {
      ArrayList<ValidationMessage> messages = new ArrayList<ValidationMessage>();
      for (ValidationResult result : results.values())
      {
         messages.addAll(result.getMessages());
      }
      return messages;
   }

   public List<ValidationMessage> getMessages(Severity severity)
   {
      ArrayList<ValidationMessage> messages = new ArrayList<ValidationMessage>();
      for (ValidationResult result : results.values())
      {
         messages.addAll(result.getMessages(severity));
      }
      return messages;
   }

   public boolean
   contains(Severity severity)
   {
      for (ValidationResult result : results.values())
      {
         if (result.contains(severity))
         {
            return true;
         }
      }
      
      return false;
   }

   public Set<Integer> getErrorIndicies()
   {
      return results.keySet();
   }

   public ValidationResult
   get(int index)
   {
      return prepareIndexedResult(index);
   }

   public int size()
   {
      return results.size();
   }

   protected ValidationResultImpl
   prepareIndexedResult(int index)
   {
      ValidationResultImpl result = (ValidationResultImpl) results.get(index);
      if (result == null)
      {
         result = new ValidationResultImpl();
         results.put(index, result);
      }
      
      return result;
   }

}
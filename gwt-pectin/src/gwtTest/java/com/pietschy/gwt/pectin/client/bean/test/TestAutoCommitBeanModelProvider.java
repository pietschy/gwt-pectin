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

package com.pietschy.gwt.pectin.client.bean.test;

import com.pietschy.gwt.pectin.client.bean.AutoCommitBeanModelProvider;

/**
 * this would normally be a static inner class but we need to be in a different
 * package to our created instance doesn't have any 'accidentally working' imports.
*/
public abstract class TestAutoCommitBeanModelProvider extends AutoCommitBeanModelProvider<TestBean>
{}
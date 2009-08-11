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

package com.pietschy.gwt.pectin.demo.client.domain;

import com.pietschy.gwt.pectin.client.bean.BeanPropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 19, 2008
 * Time: 1:43:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Person
implements BeanPropertySource
{
   private String givenName;
   private String surname;
   private String nickName;
   private Gender gender;
   private Integer age;
   private ArrayList<Wine> favoriteWines = new ArrayList<Wine>();
   private boolean wineLover = false;

   public Person()
   {
   }

   public String getGivenName()
   {
      return givenName;
   }

   public void setGivenName(String givenName)
   {
      this.givenName = givenName;
   }

   public String getSurname()
   {
      return surname;
   }

   public void setSurname(String surname)
   {
      this.surname = surname;
   }

   public String getNickName()
   {
      return nickName;
   }

   public void setNickName(String nickName)
   {
      this.nickName = nickName;
   }

   public Integer getAge()
   {
      return age;
   }

   public void setAge(Integer age)
   {
      this.age = age;
   }

   public Gender getGender()
   {
      return gender;
   }

   public void setGender(Gender gender)
   {
      this.gender = gender;
   }

   public List<Wine> getFavoriteWines()
   {
      return new ArrayList<Wine>(favoriteWines);
   }

   public void setFavoriteWines(List<Wine> favoriteWines)
   {
      this.favoriteWines = new ArrayList<Wine>(favoriteWines);
   }

   public boolean isWineLover()
   {
      return wineLover;
   }

   public void setWineLover(boolean wineLover)
   {
      this.wineLover = wineLover;
   }
}
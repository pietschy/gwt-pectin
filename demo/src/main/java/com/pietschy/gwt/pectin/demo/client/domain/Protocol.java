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

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Oct 28, 2009
 * Time: 8:56:04 AM
 * To change this template use File | Settings | File Templates.
 */
public enum Protocol
{
   FTP("FTP", 21),
   FTP_IMPLICIT_SSL("FTP with Implicit SSL", 990),
   FTP_TLS_SSL("FTP with TLS/SSL", 21),
   SFTP("SFTP", 22);

   private int defaultPort;
   private String displayName;

   Protocol(String displayName, int defaultPort)
   {
      this.defaultPort = defaultPort;
      this.displayName = displayName;
   }

   public int getDefaultPort()
   {
      return defaultPort;
   }

   public String getDisplayName()
   {
      return displayName;
   }
}

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.ivy.osgi.core;

import java.util.Map;

import org.apache.ivy.osgi.core.ExecutionEnvironmentProfileProvider;

import junit.framework.TestCase;

public class ExecutionEnvironmentProfileLoaderTest extends TestCase {

    public void testLoad() throws Exception {
        Map/* <String, ExecutionEnvironmentProfile> */ profiles = ExecutionEnvironmentProfileProvider.loadDefaultProfileList();
        assertEquals(9, profiles.size());

        assertEquals(0, ((ExecutionEnvironmentProfile) profiles.get("OSGI_MINIMUM-1.0")).getPkgNames().size());
        assertEquals(0, ((ExecutionEnvironmentProfile) profiles.get("OSGI_MINIMUM-1.1")).getPkgNames().size());
        assertEquals(1, ((ExecutionEnvironmentProfile) profiles.get("CDC-1.0_Foundation-1.0")).getPkgNames().size());
        assertEquals(3, ((ExecutionEnvironmentProfile) profiles.get("CDC-1.1_Foundation-1.1")).getPkgNames().size());
        assertEquals(24, ((ExecutionEnvironmentProfile) profiles.get("J2SE-1.2")).getPkgNames().size());
        assertEquals(40, ((ExecutionEnvironmentProfile) profiles.get("J2SE-1.3")).getPkgNames().size());
        assertEquals(95, ((ExecutionEnvironmentProfile) profiles.get("J2SE-1.4")).getPkgNames().size());
        assertEquals(120, ((ExecutionEnvironmentProfile) profiles.get("J2SE-1.5")).getPkgNames().size());
        assertEquals(154, ((ExecutionEnvironmentProfile) profiles.get("JavaSE-1.6")).getPkgNames().size());
    }
}

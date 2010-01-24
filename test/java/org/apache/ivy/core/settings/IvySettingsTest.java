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
package org.apache.ivy.core.settings;

import java.io.IOException;
import java.text.ParseException;

import junit.framework.TestCase;

import org.apache.ivy.Ivy;
import org.apache.ivy.plugins.resolver.DependencyResolver;

public class IvySettingsTest extends TestCase {

    public void testChangeDefaultResolver() throws ParseException, IOException {
        Ivy ivy = new Ivy();
        ivy.configureDefault();

        IvySettings settings = ivy.getSettings();
        DependencyResolver defaultResolver = settings.getDefaultResolver();
        
        assertNotNull(defaultResolver);
        assertEquals("default", defaultResolver.getName());
        assertSame("default resolver cached", defaultResolver, settings.getDefaultResolver());
        
        settings.setDefaultResolver("public");
        DependencyResolver newDefault = settings.getDefaultResolver();
        assertNotNull(newDefault);
        assertNotSame("default resolver has changed", defaultResolver, newDefault);
        assertEquals("resolver changed successfully", "public", newDefault.getName());
    }
    
}

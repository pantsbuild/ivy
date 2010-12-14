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
package org.apache.ivy.util.url;

import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

public class ArtifactoryListingTest extends TestCase {
    // remote.test
    
    public void testWicketListing() throws Exception {
        ApacheURLLister lister = new ApacheURLLister();
        
        List content = lister.listAll(new URL("http://repo.jfrog.org/artifactory/libs-releases-local/org/apache/wicket/wicket/"));
        assertNotNull(content);
        assertEquals(2, content.size());
    }
}

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
package org.apache.ivy.osgi.obr;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.ivy.core.module.descriptor.ModuleDescriptor;
import org.apache.ivy.osgi.obr.xml.OBRXMLParser;
import org.apache.ivy.osgi.repo.BundleRepoDescriptor;
import org.apache.ivy.osgi.repo.ModuleDescriptorWrapper;
import org.apache.ivy.util.CollectionUtils;
import org.apache.ivy.util.Message;

public class OBRParserTest extends TestCase {

    private File testObr = new File("test/test-obr");

    public void testParse() throws Exception {
        BundleRepoDescriptor repo = OBRXMLParser.parse(testObr.toURI(), new FileInputStream(
                new File(testObr, "obr.xml")));
        assertNotNull(repo);
        System.out.println(CollectionUtils.toList(repo.getModules()).size() + " bundles successfully parsed, "
                + Message.getProblems().size() + " errors");
        Iterator itPb = Message.getProblems().iterator();
        while (itPb.hasNext()) {
            Object error = itPb.next();
            System.err.println(error);
        }
        assertEquals("OBR/Releases", repo.getName());
        assertEquals("1253581430652", repo.getLastModified());
    }

    public void testParseSource() throws Exception {
        BundleRepoDescriptor repo = OBRXMLParser.parse(testObr.toURI(), new FileInputStream(
                new File(testObr, "sources.xml")));
        assertNotNull(repo);
        assertEquals(2, CollectionUtils.toList(repo.getModules()).size());
        Iterator itModule = repo.getModules();
        while (itModule.hasNext()) {
            ModuleDescriptor md = ((ModuleDescriptorWrapper) itModule.next()).getModuleDescriptor();
            if (md.getModuleRevisionId().getName().equals("org.apache.felix.eventadmin")) {
                assertEquals(1, md.getAllArtifacts().length);
            } else {
                assertEquals("org.apache.felix.bundlerepository", md.getModuleRevisionId()
                        .getName());
                assertEquals(2, md.getAllArtifacts().length);
                String url0 = md.getAllArtifacts()[0].getUrl().toExternalForm();
                String url1 = md.getAllArtifacts()[1].getUrl().toExternalForm();
                String jarUrl = "http://repo1.maven.org/maven2/org/apache/felix/"
                        + "org.apache.felix.bundlerepository/1.0.3/org.apache.felix.bundlerepository-1.0.3.jar";
                String srcUrl = "http://oscar-osgi.sf.net/obr2/org.apache.felix.bundlerepository/"
                        + "org.apache.felix.bundlerepository-1.0.3-src.jar";
                if (url0.equals(srcUrl)) {
                    assertEquals(jarUrl, url1);
                } else {
                    assertEquals(jarUrl, url0);
                    assertEquals(srcUrl, url1);
                }
            }
        }
        assertEquals("Felix-Releases", repo.getName());
        assertEquals("20120203022437.168", repo.getLastModified());
    }
}

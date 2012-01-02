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
package org.apache.ivy.osgi.updatesite;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.ivy.core.cache.CacheResourceOptions;
import org.apache.ivy.core.module.descriptor.ModuleDescriptor;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.osgi.repo.RepoDescriptor;
import org.apache.ivy.util.CacheCleaner;
import org.xml.sax.SAXException;

public class UpdateSiteLoaderTest extends TestCase {

    private UpdateSiteLoader loader;

    private File cache;

    protected void setUp() throws Exception {
        IvySettings ivySettings = new IvySettings();
        cache = new File("build/cache");
        cache.mkdirs();
        ivySettings.setDefaultCache(cache);
        CacheResourceOptions options = new CacheResourceOptions();
        loader = new UpdateSiteLoader(ivySettings.getDefaultRepositoryCacheManager(), null, options);
    }

    protected void tearDown() throws Exception {
        CacheCleaner.deleteDir(cache);
    }

    public void testIvyDE() throws IOException, ParseException, SAXException, URISyntaxException {
        RepoDescriptor site = loader.load(new URI(
                "http://www.apache.org/dist/ant/ivyde/updatesite/"));
        assertEquals(13, site.getModules().size());
    }

    public void testM2Eclipse() throws IOException, ParseException, SAXException,
            URISyntaxException {
        RepoDescriptor site = loader.load(new URI("http://m2eclipse.sonatype.org/sites/m2e/"));
        assertTrue(site.getModules().size() > 50);
        Iterator itModules = site.getModules().iterator();
        while (itModules.hasNext()) {
            ModuleDescriptor md = (ModuleDescriptor) itModules.next();
            String name = md.getModuleRevisionId().getName();
            assertTrue(name, name.indexOf("org.maven") != -1);
        }
    }

    public void _disabled_testHeliosEclipse() throws IOException, ParseException, SAXException,
            URISyntaxException {
        RepoDescriptor site = loader.load(new URI("http://download.eclipse.org/releases/helios/"));
        assertTrue(site.getModules().size() > 900);
    }

    public void testComposite() throws Exception {
        RepoDescriptor site = loader.load(new File("test/test-p2/composite/").toURI());
        assertEquals(8, site.getModules().size());

        // check that the url of the artifact is correctly resolved
        String path = new File("test/test-p2/ivyde-repo/").toURI().toURL().toExternalForm();
        ModuleDescriptor md = (ModuleDescriptor) site.getModules().iterator().next();
        assertTrue(md.getAllArtifacts()[0].getUrl().toExternalForm().startsWith(path));
    }
}

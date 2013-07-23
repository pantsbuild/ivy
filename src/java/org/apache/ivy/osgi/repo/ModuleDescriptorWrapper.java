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
package org.apache.ivy.osgi.repo;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.ivy.core.module.descriptor.DefaultArtifact;
import org.apache.ivy.core.module.descriptor.DefaultModuleDescriptor;
import org.apache.ivy.osgi.core.BundleInfo;
import org.apache.ivy.osgi.core.BundleInfoAdapter;
import org.apache.ivy.osgi.core.ExecutionEnvironmentProfileProvider;
import org.apache.ivy.osgi.core.OSGiManifestParser;

public class ModuleDescriptorWrapper {

    private BundleInfo bundleInfo;

    private DefaultModuleDescriptor md;

    private URI baseUri;

    private ExecutionEnvironmentProfileProvider profileProvider;

    private URI source;

    public ModuleDescriptorWrapper(BundleInfo bundleInfo, URI baseUri,
            ExecutionEnvironmentProfileProvider profileProvider) {
        this.bundleInfo = bundleInfo;
        this.baseUri = baseUri;
    }

    public void setSource(URI source) {
        this.source = source;
    }

    public BundleInfo getBundleInfo() {
        return bundleInfo;
    }

    public DefaultModuleDescriptor getModuleDescriptor() {
        if (md == null) {
            synchronized (this) {
                if (md != null) {
                    return md;
                }
                md = BundleInfoAdapter.toModuleDescriptor(OSGiManifestParser.getInstance(),
                    baseUri, bundleInfo, profileProvider);
                if (source != null) {
                    String compression = md.getAllArtifacts()[0].getExtraAttribute("compression");
                    DefaultArtifact sourceArtifact = BundleInfoAdapter.buildArtifact(
                        md.getModuleRevisionId(), baseUri, source, "source", compression);
                    md.addArtifact(BundleInfoAdapter.CONF_NAME_DEFAULT, sourceArtifact);
                }
            }
        }
        return md;
    }

    public static Collection/* <DefaultModuleDescriptor> */unwrap(Collection/*
                                                                             * <ModuleDescriptorWrapper
                                                                             * >
                                                                             */collection) {
        if (collection == null) {
            return null;
        }
        if (collection.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List/* <DefaultModuleDescriptor> */unwrapped = new ArrayList();
        Iterator itWrapped = collection.iterator();
        while (itWrapped.hasNext()) {
            unwrapped.add(((ModuleDescriptorWrapper) itWrapped.next()).getModuleDescriptor());
        }
        return unwrapped;
    }

    public int hashCode() {
        return bundleInfo.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ModuleDescriptorWrapper)) {
            return false;
        }
        return bundleInfo.equals(((ModuleDescriptorWrapper) obj).bundleInfo);
    }
}

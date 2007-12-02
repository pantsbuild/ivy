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
package org.apache.ivy.core.event.resolve;

import org.apache.ivy.core.module.descriptor.DependencyDescriptor;
import org.apache.ivy.core.resolve.ResolvedModuleRevision;
import org.apache.ivy.plugins.resolver.DependencyResolver;

public class EndResolveDependencyEvent extends ResolveDependencyEvent {
    public static final String NAME = "post-resolve-dependency";

    private ResolvedModuleRevision module;

    public EndResolveDependencyEvent(DependencyResolver resolver, DependencyDescriptor dd,
            ResolvedModuleRevision module) {
        super(NAME, resolver, dd);
        this.module = module;
        if (this.module != null) {
            // override revision from the dependency descriptor
            addAttribute("revision", this.module.getDescriptor().getResolvedModuleRevisionId()
                    .getRevision());
            addAttribute("resolved", "true");
        } else {
            addAttribute("resolved", "false");
        }
    }

    public ResolvedModuleRevision getModule() {
        return module;
    }

}

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
package org.apache.ivy.core.module.descriptor;

import java.util.Map;

/**
 *
 */
public abstract class AbstractArtifact implements Artifact {
    public AbstractArtifact() {
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Artifact)) {
            return false;
        }
        Artifact art = (Artifact) obj;
        return getModuleRevisionId().equals(art.getModuleRevisionId())
                && getPublicationDate() == null ? true : getPublicationDate().equals(
            art.getPublicationDate())
                && getName().equals(art.getName())
                && getExt().equals(art.getExt())
                && getType().equals(art.getType())
                && getExtraAttributes().equals(art.getExtraAttributes());
    }

    public int hashCode() {
        int hash = 33;
        hash = hash * 17 + getModuleRevisionId().hashCode();
        if (getPublicationDate() != null) {
            hash = hash * 17 + getPublicationDate().hashCode();
        }
        hash = hash * 17 + getName().hashCode();
        hash = hash * 17 + getExt().hashCode();
        hash = hash * 17 + getType().hashCode();
        hash = hash * 17 + getExtraAttributes().hashCode();
        return hash;
    }

    public String toString() {
        return getModuleRevisionId() + "/" + getName() + "." + getExt() + "[" + getType() + "]";
    }

    public String getAttribute(String attName) {
        return getId().getAttribute(attName);
    }

    public Map getAttributes() {
        return getId().getAttributes();
    }

    public String getExtraAttribute(String attName) {
        return getId().getExtraAttribute(attName);
    }

    public Map getExtraAttributes() {
        return getId().getExtraAttributes();
    }

    public String getStandardAttribute(String attName) {
        return getId().getStandardAttribute(attName);
    }

    public Map getStandardAttributes() {
        return getId().getStandardAttributes();
    }

}

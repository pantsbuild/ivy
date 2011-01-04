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
package org.apache.ivy.osgi.obr.filter;

import org.apache.ivy.osgi.obr.xml.RequirementFilter;

public abstract class UniOperatorFilter extends RequirementFilter {

    private final RequirementFilter subFilter;

    public UniOperatorFilter(RequirementFilter subFilter) {
        this.subFilter = subFilter;
    }

    abstract protected char operator();

    public void append(StringBuffer builder) {
        builder.append("(");
        builder.append(operator());
        builder.append(subFilter.toString());
        builder.append(")");
    }

    public RequirementFilter getSubFilter() {
        return subFilter;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subFilter == null) ? 0 : subFilter.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MultiOperatorFilter)) {
            return false;
        }
        UniOperatorFilter other = (UniOperatorFilter) obj;
        if (subFilter == null) {
            if (other.subFilter != null) {
                return false;
            }
        } else if (!subFilter.equals(other.subFilter)) {
            return false;
        }
        return true;
    }
}

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
package org.apache.ivy.core.retrieve;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class RetrieveReport {

    private Collection/*<File>*/ upToDateFiles = new HashSet();
    private Collection/*<File>*/ copiedFiles = new HashSet();

    private File retrieveRoot;

    /**
     * Returns the root directory to where the artifacts are retrieved.
     */
    public File getRetrieveRoot() {
        return retrieveRoot;
    }

    public void setRetrieveRoot(File retrieveRoot) {
        this.retrieveRoot = retrieveRoot;
    }

    public int getNbrArtifactsCopied() {
        return copiedFiles.size();
    }

    public int getNbrArtifactsUpToDate() {
        return upToDateFiles.size();
    }

    public void addCopiedFile(File file) {
        copiedFiles.add(file);
    }

    public void addUpToDateFile(File file) {
        upToDateFiles.add(file);
    }

    /**
     * Returns a collection of <tt>File</tt> objects who were actually copied during the retrieve process.
     */
    public Collection getCopiedFiles() {
        return new ArrayList(copiedFiles);
    }

    /**
     * Returns a collection of <tt>File</tt> objects who were actually copied during the retrieve process.
     */
    public Collection getUpToDateFiles() {
        return new ArrayList(upToDateFiles);
    }

    /**
     * Returns a collection of <tt>File</tt> objects who were retrieved during the retrieve process. This is
     * the union of the files being copied and the files that were up-to-date.
     */
    public Collection getRetrievedFiles() {
        Collection result = new ArrayList(upToDateFiles.size() + copiedFiles.size());
        result.addAll(upToDateFiles);
        result.addAll(copiedFiles);
        return result;
    }

}

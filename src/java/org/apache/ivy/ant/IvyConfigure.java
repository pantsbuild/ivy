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
package org.apache.ivy.ant;

import org.apache.ivy.Ivy;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * Configure Ivy with an ivysettings.xml file
 * 
 * @deprecated Use the IvyAntSettings instead.
 */
public class IvyConfigure extends IvyAntSettings {
    public void execute() throws BuildException {
        log("ivy:configure is deprecated, please use the data type ivy:settings instead",
            Project.MSG_WARN);
        super.execute();
    }

    public Ivy getIvyInstance() {
        return getConfiguredIvyInstance();
    }
}

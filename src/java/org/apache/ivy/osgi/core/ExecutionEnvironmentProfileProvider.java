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
package org.apache.ivy.osgi.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.ivy.osgi.repo.ExecutionEnvironmentProfile;
import org.apache.ivy.util.Message;

public class ExecutionEnvironmentProfileProvider {

    private static final String DEFAULT_PROFILES_FILE = "jvm-packages.properties";

    private static final String PACKAGE_PREFIX = "org/apache/ivy/osgi/core/";

    private Map/* <String, ExecutionEnvironmentProfile> */profileList;

    public ExecutionEnvironmentProfileProvider() throws IOException {
        profileList = loadDefaultProfileList();
    }

    public ExecutionEnvironmentProfile getProfile(String profile) {
        return (ExecutionEnvironmentProfile) profileList.get(profile);
    }

    public static Map/* <String, ExecutionEnvironmentProfile> */loadDefaultProfileList()
            throws IOException {
        ClassLoader loader = ExecutionEnvironmentProfileProvider.class.getClassLoader();
        InputStream defaultProfilesFile = loader.getResourceAsStream(PACKAGE_PREFIX
                + DEFAULT_PROFILES_FILE);
        if (defaultProfilesFile == null) {
            throw new FileNotFoundException(PACKAGE_PREFIX + DEFAULT_PROFILES_FILE
                    + " not found in the classpath");
        }
        Properties props = new Properties();
        try {
            props.load(defaultProfilesFile);
        } finally {
            defaultProfilesFile.close();
        }
        Map/* <String, ExecutionEnvironmentProfile> */profiles = new HashMap();
        Iterator itProp = props.entrySet().iterator();
        while (itProp.hasNext()) {
            Entry/* <String, String> */prop = (Entry) itProp.next();
            String propName = (String) prop.getKey();
            if (propName.endsWith(".pkglist")) {
                String profileName = propName.substring(0, propName.length() - 8);
                if (!profiles.containsKey(profileName)) {
                    loadProfile(props, profiles, profileName);
                }
            }
        }
        return profiles;
    }

    private static ExecutionEnvironmentProfile loadProfile(Properties props, Map/*
                                                                                * <String,
                                                                                * ExecutionEnvironmentProfile
                                                                                * >
                                                                                */profiles,
            String name) {

        ExecutionEnvironmentProfile profile = new ExecutionEnvironmentProfile(name);

        // load the package for the extended profile
        String extendedProfileName = props.getProperty(name + ".extends");
        if (extendedProfileName != null) {
            ExecutionEnvironmentProfile extendedProfile = (ExecutionEnvironmentProfile) profiles
                    .get(extendedProfileName);
            if (extendedProfile == null) {
                // not loaded yet, so load it now
                extendedProfile = loadProfile(props, profiles, extendedProfileName);
            }
            Iterator itExtended = extendedProfile.getPkgNames().iterator();
            while (itExtended.hasNext()) {
                profile.addPkgName((String) itExtended.next());
            }
        }

        // load the actual list
        String pkgList = props.getProperty(name + ".pkglist");
        String[] packages = pkgList.split(",");
        for (int i = 0; i < packages.length; i++) {
            String pkg = packages[i].trim();
            if (pkg.length() != 0) {
                profile.addPkgName(pkg);
            }
        }

        profiles.put(name, profile);

        Message.verbose("Execution environment profile " + profile.getName() + " loaded");

        return profile;
    }

}

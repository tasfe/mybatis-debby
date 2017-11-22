/**
 *    Copyright 2016-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session;

import java.util.Properties;
import java.util.Set;

/**
 * @author rocky.hu
 * @date Nov 20, 2017 4:30:52 PM
 */
public class XConfiguration {
    
    private boolean debugEnabled;
	private String tablePrefix = ""; 
    private Properties additionalDatabaseDialects;
    private org.apache.ibatis.session.Configuration configuration;
    
    public XConfiguration(org.apache.ibatis.session.Configuration configuration) {
        this.configuration = configuration;
    }

    public org.apache.ibatis.session.Configuration getConfiguration() {
		return configuration;
	}

	public Set<String> getLoadedResources() {
        return configuration.loadedResources;
    }

    public Properties getAdditionalDatabaseDialects() {
        return additionalDatabaseDialects;
    }

    public void setAdditionalDatabaseDialects(Properties additionalDatabaseDialects) {
        this.additionalDatabaseDialects = additionalDatabaseDialects;
    }
    
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	public void setDebugEnabled(boolean debugEnabled) {
		this.debugEnabled = debugEnabled;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
    
}

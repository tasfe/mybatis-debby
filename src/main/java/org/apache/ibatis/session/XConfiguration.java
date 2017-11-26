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

import org.mybatis.debby.codegen.keystrategy.XKeyStrategy;
import org.mybatis.debby.codegen.keystrategy.identity.XIdentityKeyStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author rocky.hu
 * @date Nov 20, 2017 4:30:52 PM
 */
public class XConfiguration {

	/** debug or not */
    private boolean debugEnabled;
    /** when debug mode is enabled, it will write the auto generated mapper xml to this directory */
    private String mapperXMLOuputDirectory;
	private String tablePrefix = ""; 
    private XKeyStrategy keyStrategy;

	private Configuration configuration;
    
    public XConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
		return configuration;
	}

	public Set<String> getLoadedResources() {
        return configuration.loadedResources;
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

	public String getMapperXMLOuputDirectory() {
		return mapperXMLOuputDirectory;
	}

	public void setMapperXMLOuputDirectory(String mapperXMLOuputDirectory) {
		this.mapperXMLOuputDirectory = mapperXMLOuputDirectory;
	}

	public XKeyStrategy getKeyStrategy() {
    	if (keyStrategy == null) {
    		return new XIdentityKeyStrategy();
		}
		return keyStrategy;
	}

	public void setKeyStrategy(XKeyStrategy keyStrategy) {
		this.keyStrategy = keyStrategy;
	}
}

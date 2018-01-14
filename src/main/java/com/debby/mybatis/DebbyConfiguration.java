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
package com.debby.mybatis;

import com.debby.mybatis.core.dialect.Dialect;
import com.debby.mybatis.exception.DebbyException;

/**
 * @author rocky.hu
 * @date 2017-11-27 10:14 PM
 */
public class DebbyConfiguration {

    private boolean debugEnabled;
    private String mapperXMLOutputDirectory;
    private String tablePrefix;
    private Dialect dialect;
    private boolean camelToUnderscore = false;

    public DebbyConfiguration() {
    }

    public DebbyConfiguration(Dialect dialect) {
        this.dialect = dialect;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public String getMapperXMLOutputDirectory() {
        return mapperXMLOutputDirectory;
    }

    public void setMapperXMLOutputDirectory(String mapperXMLOutputDirectory) {
        this.mapperXMLOutputDirectory = mapperXMLOutputDirectory;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

	public Dialect getDialect()
	{
	    if (dialect == null) {
	        throw new DebbyException("Dialect is required.");
        }
		return dialect;
	}

	public void setDialect(Dialect dialect)
	{
		this.dialect = dialect;
	}

    public boolean getCamelToUnderscore() {
        return camelToUnderscore;
    }

    public void setCamelToUnderscore(boolean camelToUnderscore) {
        this.camelToUnderscore = camelToUnderscore;
    }

}

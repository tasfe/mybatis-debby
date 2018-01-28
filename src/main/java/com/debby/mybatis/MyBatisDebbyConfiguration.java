/**
 *    Copyright 2017-2018 the original author or authors.
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

import com.debby.mybatis.core.DialectFactory;
import com.debby.mybatis.core.dialect.Dialect;
import com.debby.mybatis.exception.MyBatisDebbyException;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date 2017-11-27 10:14 PM
 */
public class MyBatisDebbyConfiguration {

	/** Enable debug mode to view the mapper xml which the framework auto generate */
    private boolean debugEnabled;
    /** The mapper xml output path */
    private String mapperXmlOutputPath;
    /** Global config for table prefix */
    private String tablePrefix;
    /** The dialect alias */
    private String dialect;
    /** Set the value to 'true', when use auto mapping, the property will be mapped to under score format */
    private boolean camelToUnderscore;

    public MyBatisDebbyConfiguration() {
    }

    public MyBatisDebbyConfiguration(String dialect) {
        this.dialect = dialect;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

	public String getMapperXmlOutputPath() {
		return mapperXmlOutputPath;
	}

	public void setMapperXmlOutputPath(String mapperXmlOutputPath) {
		this.mapperXmlOutputPath = mapperXmlOutputPath;
	}

	public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

	public Dialect getDialect() {
		if (StringUtils.isNullOrEmpty(dialect)) {
			throw new MyBatisDebbyException("Dialect is required.");
		}
		Dialect instance = DialectFactory.getDialect(dialect);
		return instance;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

    public boolean getCamelToUnderscore() {
        return camelToUnderscore;
    }

    public void setCamelToUnderscore(boolean camelToUnderscore) {
        this.camelToUnderscore = camelToUnderscore;
    }

}

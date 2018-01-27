/**
 * Copyright 2017-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.debby.mybatis.core.dialect;

import com.debby.mybatis.core.dom.xml.XmlElement;

/**
 * A dialect for Microsoft SQL Server 2008.
 * 
 * @author rocky.hu
 * @date Jan 10, 2018 10:00:38 AM
 */
public class SQLServer2008Dialect extends SQLServer2005Dialect {

	@Override
	public void processLimitPrefixSqlFragment(XmlElement parentElement) {
		super.processLimitPrefixSqlFragment(parentElement);
	}

	@Override
	public void processLimitSuffixSqlFragment(XmlElement parentElement) {
		super.processLimitSuffixSqlFragment(parentElement);
	}

}

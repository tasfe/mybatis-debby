/**
 * Copyright 2016-2017 the original author or authors.
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

import com.debby.mybatis.core.dialect.identity.IdentityColumnStrategy;
import com.debby.mybatis.core.dialect.identity.SQLServerIdentityColumnStrategy;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;

/**
 * A dialect for Microsoft SQL Server 2000
 * 
 * @author rocky.hu
 * @date 2017-12-16 9:06 PM
 */
public class SQLServerDialect extends Dialect {

    @Override
    public IdentityColumnStrategy getIdentityColumnStrategy() {
        return new SQLServerIdentityColumnStrategy();
    }

	@Override
	public void processLimitPrefixSqlFragment(XmlElement parentElement) {
		super.processLimitPrefixSqlFragment(parentElement);
		
		StringBuilder sb = new StringBuilder();
		sb.append(" TOP ");
		sb.append("${maxResults} ");
    	parentElement.addElement(new TextElement(sb.toString()));
	}

	@Override
	public void processLimitSuffixSqlFragment(XmlElement parentElement) {
		// do nothing
	}
    
}

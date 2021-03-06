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
package com.debby.mybatis.core.xmlmapper.elements.sql;

import com.debby.mybatis.core.IntrospectedContext;
import com.debby.mybatis.core.constant.Constants;
import com.debby.mybatis.core.dialect.Dialect;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * @author rocky.hu
 * @date Jan 8, 2018 3:44:00 PM
 */
public class SqlPaginationPrefixFragmentElementGenerator extends AbstractXmlElementGenerator {

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("sql");
		answer.addAttribute(new Attribute("id", Constants.PREFIX_PAGINATION_SQL_FRAGMENT_ID));

		IntrospectedContext introspectedContext = getIntrospectedContext();
		Dialect dialect = introspectedContext.getDebbyConfiguration().getDialect();
		dialect.processLimitPrefixSqlFragment(answer);

		parentElement.addElement(answer);
	}

}

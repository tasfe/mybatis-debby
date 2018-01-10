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

import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;

/**
 * A dialect for Microsoft SQL 2005.
 * 
 * @author rocky.hu
 * @date Jan 10, 2018 9:55:37 AM
 */
public class SQLServer2005Dialect extends SQLServerDialect {

	@Override
	public void processLimitPrefixSqlFragment(XmlElement parentElement) {
		
		StringBuilder sb = new StringBuilder();
		
		XmlElement chooseElement = new XmlElement("choose");
		XmlElement whenElement = new XmlElement("when");
		whenElement.addAttribute(new Attribute("test", "_parameter != null and firstResult > 0"));
		
		sb.append("WITH query AS (");
		whenElement.addElement(new TextElement(sb.toString()));
		
		sb.setLength(0);
		sb.append("SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) as __row_nr__ FROM ( ");
		whenElement.addElement(new TextElement(sb.toString()));
		
		whenElement.addElement(new TextElement("select"));
		
		XmlElement distinctIfElement = new XmlElement("if");
		distinctIfElement.addAttribute(new Attribute("test", "distinct"));
		distinctIfElement.addElement(new TextElement("distinct"));
		whenElement.addElement(distinctIfElement);
		
		XmlElement topIfElement = new XmlElement("if");
		topIfElement.addAttribute(new Attribute("test", "_parameter != null and entityOrderList != null and entityOrderList.size() > 0"));
		sb.setLength(0);
		sb.append(" TOP ");
		sb.append("(#{maxResults}+#{firstResult}-1)");
		topIfElement.addElement(new TextElement(sb.toString()));
		whenElement.addElement(topIfElement);
		
		XmlElement otherwiseElement = new XmlElement("otherwise");
		super.processLimitPrefixSqlFragment(otherwiseElement);
		
		chooseElement.addElement(whenElement);
		chooseElement.addElement(otherwiseElement);
		
		parentElement.addElement(chooseElement);
	}

	@Override
	public void processLimitSuffixSqlFragment(XmlElement parentElement) {
		StringBuilder sb = new StringBuilder();
		
		XmlElement ifElement = new XmlElement("if");
		ifElement.addAttribute(new Attribute("test", "_parameter != null and firstResult > 0"));
		
		sb.append(" ) inner_query ");
		sb.append(")");
		sb.append(" SELECT ");
		
		ifElement.addElement(new TextElement(sb.toString()));
		
		XmlElement includeElement = new XmlElement("include");
		includeElement.addAttribute(new Attribute("refid", "baseColumns"));
		ifElement.addElement(includeElement);
		
		sb.setLength(0);
		sb.append("FROM query WHERE __row_nr__ &gt;= #{firstResult} AND __row_nr__ &lt;(#{firstResult}+#{maxResults})");
		ifElement.addElement(new TextElement(sb.toString()));
		
		parentElement.addElement(ifElement);
	}

}

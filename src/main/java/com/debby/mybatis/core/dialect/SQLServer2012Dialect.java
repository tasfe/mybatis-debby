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
 * Microsoft SQL Server 2012 Dialect.
 * 
 * @author rocky.hu
 * @date Jan 10, 2018 10:01:28 AM
 */
public class SQLServer2012Dialect extends SQLServer2008Dialect {

	@Override
	public void processLimitPrefixSqlFragment(XmlElement parentElement) {
		// do nothing
	}

	@Override
	public void processLimitSuffixSqlFragment(XmlElement parentElement) {
		XmlElement chooseElement = new XmlElement("choose");
		
		StringBuilder sb = new StringBuilder();
		
		// when
		XmlElement whenElement = new XmlElement("when");
		whenElement.addAttribute(new Attribute("test", "_parameter != null and entityOrderList != null and entityOrderList.size() > 0"));
		
		XmlElement ifElement = new XmlElement("if");
		ifElement.addAttribute(new Attribute("test", "_parameter != null and maxResults != null and maxResults > 0"));
		
		XmlElement ofChooseElement = new XmlElement("choose");
		XmlElement ofWhenElement = new XmlElement("when");
		ofWhenElement.addAttribute(new Attribute("test", "_parameter != null and firstResult != null and firstResult > 0"));
		sb.append("offset #{firstResult} rows fetch next #{maxResults} rows only");
		ofWhenElement.addElement(new TextElement(sb.toString()));
		
		XmlElement ofOtherwiseElement = new XmlElement("otherwise");
		sb.setLength(0);
		sb.append("offset 0 rows fetch next #{maxResults} rows only");
		ofOtherwiseElement.addElement(new TextElement(sb.toString()));
		
		ofChooseElement.addElement(ofWhenElement);
		ofChooseElement.addElement(ofOtherwiseElement);
		
		ifElement.addElement(ofChooseElement);
		whenElement.addElement(ifElement);
		chooseElement.addElement(whenElement);
		
		parentElement.addElement(chooseElement);
	}

}

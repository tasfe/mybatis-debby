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
		XmlElement chooseElement = new XmlElement("choose");
		
		/**
		 * 1. has order by clause
		 */
		XmlElement whenElement = new XmlElement("when");
		whenElement.addAttribute(new Attribute("test", "_parameter != null and orderList != null and orderList.size() > 0"));
		whenElement.addElement(new TextElement("SELECT"));
    	XmlElement ifElement = new XmlElement("if");
    	ifElement.addAttribute(new Attribute("test", "distinct"));
    	ifElement.addElement(new TextElement("distinct"));
    	whenElement.addElement(ifElement);
		
    	/**
    	 * 2. not has order by clause
    	 */
		XmlElement otherwiseElement = new XmlElement("otherwise");
		super.processLimitPrefixSqlFragment(otherwiseElement);
		
		chooseElement.addElement(whenElement);
		chooseElement.addElement(otherwiseElement);
		parentElement.addElement(chooseElement);
	}

	@Override
	public void processLimitSuffixSqlFragment(XmlElement parentElement) {
		XmlElement chooseElement = new XmlElement("choose");
		
		StringBuilder sb = new StringBuilder();
		
		/**
		 * 1. has order by clause 
		 */
		// when
		XmlElement whenElement = new XmlElement("when");
		whenElement.addAttribute(new Attribute("test", "_parameter != null and orderList != null and orderList.size() > 0"));
		
		// 1.1  maxResults != null && maxResults > 0
		XmlElement ifElement = new XmlElement("if");
		ifElement.addAttribute(new Attribute("test", "_parameter != null and maxResults != null and maxResults > 0"));
		
		XmlElement ofChooseElement = new XmlElement("choose");
		// 1.1.1 firstResult > 0
		XmlElement ofWhenElement = new XmlElement("when");
		ofWhenElement.addAttribute(new Attribute("test", "_parameter != null and firstResult != null and firstResult > 0"));
		sb.append("offset #{firstResult} rows fetch next #{maxResults} rows only");
		ofWhenElement.addElement(new TextElement(sb.toString()));
		// 1.1.2 firstResult == 0
		XmlElement ofOtherwiseElement = new XmlElement("otherwise");
		sb.setLength(0);
		sb.append("offset 0 rows fetch next #{maxResults} rows only");
		ofOtherwiseElement.addElement(new TextElement(sb.toString()));
		
		ofChooseElement.addElement(ofWhenElement);
		ofChooseElement.addElement(ofOtherwiseElement);
		ifElement.addElement(ofChooseElement);
		
		whenElement.addElement(ifElement);
		chooseElement.addElement(whenElement);
		
		/**
		 * 2. not has order by clause
		 */
		// otherwise
		XmlElement otherwiseElement = new XmlElement("otherwise");
		super.processLimitSuffixSqlFragment(otherwiseElement);
		chooseElement.addElement(otherwiseElement);
		
		parentElement.addElement(chooseElement);
	}

}

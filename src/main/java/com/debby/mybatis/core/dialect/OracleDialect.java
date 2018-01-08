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
import com.debby.mybatis.exception.MappingException;

/**
 * @author rocky.hu
 * @date 2017-12-16 9:20 PM
 */
public class OracleDialect extends Dialect {

	@Override
	public String getSequenceNextValString(String sequenceName) throws MappingException {
		return "select " + sequenceName + ".nextval" + " from dual";
	}

	@Override
	public void processLimitPrefixSqlFragment(XmlElement parentElement) {
		XmlElement chooseElement = new XmlElement("choose");

		StringBuilder sb = new StringBuilder();

		XmlElement whenElement = new XmlElement("when");
		whenElement.addAttribute(new Attribute("test", "firstResult != null"));
		sb.append("select * from ( select row_.*, rownum rownum_ from ( ");
		whenElement.addElement(new TextElement(sb.toString()));

		XmlElement otherwiseElement = new XmlElement("otherwise");
		sb.setLength(0);
		sb.append("select * from ( ");
		otherwiseElement.addElement(new TextElement(sb.toString()));

		chooseElement.addElement(whenElement);
		chooseElement.addElement(otherwiseElement);

		parentElement.addElement(chooseElement);
	}

	@Override
	public void processLimitSuffixSqlFragment(XmlElement parentElement) {
		XmlElement chooseElement = new XmlElement("choose");

		StringBuilder sb = new StringBuilder();

		XmlElement whenElement = new XmlElement("when");
		whenElement.addAttribute(new Attribute("test", "firstResult != null"));
		sb.append(" ) row_ where rownum <= #{maxResults}) where rownum_ > #{firstResult}");
		whenElement.addElement(new TextElement(sb.toString()));

		XmlElement otherwiseElement = new XmlElement("otherwise");
		sb.setLength(0);
		sb.append(" ) where rownum <= #{maxResults}");
		otherwiseElement.addElement(new TextElement(sb.toString()));

		chooseElement.addElement(whenElement);
		chooseElement.addElement(otherwiseElement);

		parentElement.addElement(chooseElement);
	}

}

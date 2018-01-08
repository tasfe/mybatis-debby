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

import com.debby.mybatis.core.dialect.identity.DB2IdentityColumnStrategy;
import com.debby.mybatis.core.dialect.identity.IdentityColumnStrategy;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.exception.MappingException;

/**
 * @author rocky.hu
 * @date 2017-12-16 9:00 PM
 */
public class DB2Dialect extends Dialect {

	@Override
	public IdentityColumnStrategy getIdentityColumnStrategy() {
		return new DB2IdentityColumnStrategy();
	}

	@Override
	public String getSequenceNextValString(String sequenceName) throws MappingException {
		return "values nextval for " + sequenceName;
	}

	@Override
	public void processLimitPrefixSqlFragment(XmlElement parentElement) {
		XmlElement chooseElement = new XmlElement("choose");

		StringBuilder sb = new StringBuilder();
		
		XmlElement whenElement = new XmlElement("when");
		whenElement.addAttribute(new Attribute("test", "firstResult != null"));
		sb.append("select * from ( select inner2_.*, rownumber() over(order by order of inner2_) as rownumber_ from ( ");
		whenElement.addElement(new TextElement(sb.toString()));
		
		chooseElement.addElement(whenElement);
		parentElement.addElement(chooseElement);
	}

	@Override
	public void processLimitSuffixSqlFragment(XmlElement parentElement) {
		XmlElement chooseElement = new XmlElement("choose");

		StringBuilder sb = new StringBuilder();

		XmlElement whenElement = new XmlElement("when");
		whenElement.addAttribute(new Attribute("test", "firstResult != null"));
		sb.append(" fetch first ");
		sb.append("(#{maxResults}+#{firstResult})");
		sb.append(" rows only ) as inner2_ ) as inner1_ where rownumber_ > ");
		sb.append("#{firstResult}");
		sb.append(" order by rownumber_");
		whenElement.addElement(new TextElement(sb.toString()));

		XmlElement otherwiseElement = new XmlElement("otherwise");
		sb.setLength(0);
		sb.append(" fetch first ");
		sb.append("(#{maxResults}+#{firstResult})");
		sb.append(" rows only");
		otherwiseElement.addElement(new TextElement(sb.toString()));

		chooseElement.addElement(whenElement);
		chooseElement.addElement(otherwiseElement);
		parentElement.addElement(chooseElement);
	}

}

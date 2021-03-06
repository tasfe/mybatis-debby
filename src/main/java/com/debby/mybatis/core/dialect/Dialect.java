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
package com.debby.mybatis.core.dialect;

import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.exception.MappingException;

/**
 * @author rocky.hu
 * @date 2017-12-16 4:32 PM
 */
public abstract class Dialect {

	public boolean supportsIdentityColumns() {
		return false;
	}

	public String getSequenceNextValString(String sequenceName) throws MappingException {
        throw new MappingException( getClass().getName() + " does not support sequences" );
    }
    
    public void processLimitPrefixSqlFragment(XmlElement parentElement) {
    	
    	parentElement.addElement(new TextElement("SELECT"));
    	
    	XmlElement ifElement = new XmlElement("if");
    	ifElement.addAttribute(new Attribute("test", "distinct"));
    	ifElement.addElement(new TextElement("distinct"));
    	
    	parentElement.addElement(ifElement);
    }
    
    public abstract void processLimitSuffixSqlFragment(XmlElement parentElement);
}

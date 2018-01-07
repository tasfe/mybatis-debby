/**
 *    Copyright 2016-2017 the original author or authors.
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
package com.debby.mybatis.core.xmlmapper.elements;

import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.XmlElement;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 23, 2017 2:57:56 PM
 * @see 'org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ExampleWhereClauseElementGenerator'
 */
public class XSelectWhereSqlFragmentElementGenerator extends XAbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", "selectWhereSqlFragment"));

        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "_parameter != null and _parameter.criteriaList != null and _parameter.criteriaList.size() > 0"));
        
        XmlElement whereElement = new XmlElement("where");
        XmlElement forEachElement = new XmlElement("foreach");
        forEachElement.addAttribute(new Attribute("collection", "criteriaList"));
        forEachElement.addAttribute(new Attribute("item", "criteria"));
        forEachElement.addAttribute(new Attribute("separator", "or"));
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", "criteriaSqlFragment"));
        
        forEachElement.addElement(includeElement);
        whereElement.addElement(forEachElement);

        ifElement.addElement(whereElement);
        answer.addElement(ifElement);
        
        parentElement.addElement(answer);
    }

}

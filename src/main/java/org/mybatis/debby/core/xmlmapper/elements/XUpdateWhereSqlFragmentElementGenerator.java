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
package org.mybatis.debby.core.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 2:57:56 PM
 */
public class XUpdateWhereSqlFragmentElementGenerator extends XAbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", "updateWhereSqlFragment"));
        
        XmlElement whereElement = new XmlElement("where");
        XmlElement forEachElement = new XmlElement("foreach");
        forEachElement.addAttribute(new Attribute("collection", "updatedCriteria.oredCriteriaList"));
        forEachElement.addAttribute(new Attribute("item", "criteria"));
        forEachElement.addAttribute(new Attribute("separator", "or"));
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", "criteriaSqlFragment"));
        
        forEachElement.addElement(includeElement);
        whereElement.addElement(forEachElement);
        answer.addElement(whereElement);
        
        parentElement.addElement(answer);
    }

}

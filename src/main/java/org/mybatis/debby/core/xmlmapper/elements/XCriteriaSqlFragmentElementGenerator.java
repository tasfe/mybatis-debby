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
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 3:21:53 PM
 */
public class XCriteriaSqlFragmentElementGenerator extends XAbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", "criteriaSqlFragment"));
        
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "criteria.valid"));
        answer.addElement(ifElement);
        
        XmlElement trimElement = new XmlElement("trim");
        trimElement.addAttribute(new Attribute("prefix", "("));
        trimElement.addAttribute(new Attribute("suffix", ")"));
        trimElement.addAttribute(new Attribute("prefixOverrides", "AND"));
        ifElement.addElement(trimElement);
        
        XmlElement forEachElement = new XmlElement("foreach");
        forEachElement.addAttribute(new Attribute("collection", "criteria.criterions"));
        forEachElement.addAttribute(new Attribute("item", "criterion"));
        forEachElement.addAttribute(new Attribute("separator", "AND"));
        trimElement.addElement(forEachElement);
        
        XmlElement chooseElement = new XmlElement("choose");
        forEachElement.addElement(chooseElement);
        
        /** match */
        XmlElement when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criterion.matchMode != null"));
        when.addElement(new TextElement("${criterion.propertyName} LIKE"));
        
        XmlElement exactIfElement = new XmlElement("if");
        exactIfElement.addAttribute(new Attribute("test", "criterion.matchMode.getNotation() == 'EXACT'"));
        exactIfElement.addElement(new TextElement("#{criterion.value}"));
        when.addElement(exactIfElement);
        
        XmlElement startIfElement = new XmlElement("if");
        startIfElement.addAttribute(new Attribute("test", "criterion.matchMode.getNotation() == 'START'"));
        startIfElement.addElement(new TextElement("concat(concat('',#{criterion.value}),'%')"));
        when.addElement(startIfElement);
        
        XmlElement endIfElement = new XmlElement("if");
        endIfElement.addAttribute(new Attribute("test", "criterion.matchMode.getNotation() == 'END'"));
        endIfElement.addElement(new TextElement("concat(concat('%',#{criterion.value}),'')"));
        when.addElement(endIfElement);
        
        XmlElement anywhereIfElement = new XmlElement("if");
        anywhereIfElement.addAttribute(new Attribute("test", "criterion.matchMode.getNotation() == 'ANYWHERE'"));
        anywhereIfElement.addElement(new TextElement("concat(concat('%',#{criterion.value}),'%')"));
        when.addElement(anywhereIfElement);
        
        chooseElement.addElement(when);
        
        /** list value */
        when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criterion.listValue != null"));
        when.addElement(new TextElement("${criterion.propertyName} ${criterion.sqlOperators}"));
        
        XmlElement whenForeachElement = new XmlElement("foreach");
        whenForeachElement.addAttribute(new Attribute("collection", "criterion.listValue"));
        whenForeachElement.addAttribute(new Attribute("item", "listItem"));
        whenForeachElement.addAttribute(new Attribute("open", "("));
        whenForeachElement.addAttribute(new Attribute("close", ")"));
        whenForeachElement.addAttribute(new Attribute("separator", ","));
        whenForeachElement.addElement(new TextElement("#{listItem}"));
        when.addElement(whenForeachElement);
        
        chooseElement.addElement(when);
        
        /** value && secondValue */
        when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criterion.value != null and criterion.secondValue != null"));
        when.addElement(new TextElement("(${criterion.propertyName} ${criterion.sqlOperators} #{criterion.value} AND #{criterion.secondValue})"));
        chooseElement.addElement(when);
        
        /** value != null */
        when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criterion.value != null"));
        when.addElement(new TextElement("${criterion.propertyName} ${criterion.sqlOperators} #{criterion.value}"));
        chooseElement.addElement(when);
        
        /** value == null */
        when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criterion.value == null"));
        when.addElement(new TextElement("${criterion.propertyName} ${criterion.sqlOperators}"));
        chooseElement.addElement(when);
        
        parentElement.addElement(answer);
    }

}

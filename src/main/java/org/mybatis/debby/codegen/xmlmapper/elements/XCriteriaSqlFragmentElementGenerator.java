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
package org.mybatis.debby.codegen.xmlmapper.elements;

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
        
        XmlElement criteriaValidIfElement = new XmlElement("if");
        criteriaValidIfElement.addAttribute(new Attribute("test", "criteria.valid"));
        
        XmlElement trimElement = new XmlElement("trim");
        trimElement.addAttribute(new Attribute("prefix", "("));
        trimElement.addAttribute(new Attribute("prefixOverrides", "AND"));
        trimElement.addAttribute(new Attribute("suffix", ")"));
        
        XmlElement forEachElement = new XmlElement("foreach");
        forEachElement.addAttribute(new Attribute("collection", "criteria.criterions"));
        forEachElement.addAttribute(new Attribute("item", "criterion"));
        forEachElement.addAttribute(new Attribute("separator", "AND"));
        
        XmlElement chooseElement = new XmlElement("choose");
        
        XmlElement firstWhenElement = new XmlElement("when");
        firstWhenElement.addAttribute(new Attribute("test", "criterion.matchMode != null"));
        firstWhenElement.addElement(new TextElement("${criterion.propertyName} LIKE"));
        XmlElement exactIfElement = new XmlElement("if");
        exactIfElement.addAttribute(new Attribute("test", "criterion.matchMode.getNotation() == 'EXACT'"));
        exactIfElement.addElement(new TextElement("#{criterion.value}"));
        XmlElement startIfElement = new XmlElement("if");
        startIfElement.addAttribute(new Attribute("test", "criterion.matchMode.getNotation() == 'START'"));
        startIfElement.addElement(new TextElement("concat(concat('',#{criterion.value}),'%')"));
        XmlElement endIfElement = new XmlElement("if");
        endIfElement.addAttribute(new Attribute("test", "criterion.matchMode.getNotation() == 'END'"));
        endIfElement.addElement(new TextElement("concat(concat('%',#{criterion.value}),'')"));
        XmlElement anywhereIfElement = new XmlElement("if");
        anywhereIfElement.addAttribute(new Attribute("test", "criterion.matchMode.getNotation() == 'ANYWHERE'"));
        anywhereIfElement.addElement(new TextElement("concat(concat('%',#{criterion.value}),'%')"));
        firstWhenElement.addElement(exactIfElement);
        firstWhenElement.addElement(startIfElement);
        firstWhenElement.addElement(endIfElement);
        firstWhenElement.addElement(anywhereIfElement);
        
        XmlElement secondWhenElement = new XmlElement("when");
        secondWhenElement.addAttribute(new Attribute("test", "criterion.listValue != null"));
        secondWhenElement.addElement(new TextElement("${criterion.propertyName} ${criterion.sqlOperators}"));
        XmlElement secondWhenForeachElement = new XmlElement("foreach");
        secondWhenForeachElement.addAttribute(new Attribute("collection", "criterion.listValue"));
        secondWhenForeachElement.addAttribute(new Attribute("item", "listItem"));
        secondWhenForeachElement.addAttribute(new Attribute("open", "("));
        secondWhenForeachElement.addAttribute(new Attribute("close", ")"));
        secondWhenForeachElement.addAttribute(new Attribute("separator", ","));
        secondWhenForeachElement.addElement(new TextElement("#{listItem}"));
        secondWhenElement.addElement(secondWhenForeachElement);
        
        XmlElement thirdWhenElement = new XmlElement("when");
        thirdWhenElement.addAttribute(new Attribute("test", "criterion.value != null and criterion.secondValue != null"));
        thirdWhenElement.addElement(new TextElement("(${criterion.propertyName} ${criterion.sqlOperators} #{criterion.value} AND #{criterion.secondValue})"));
        
        XmlElement fourWhenElement = new XmlElement("when");
        fourWhenElement.addAttribute(new Attribute("test", "criterion.value != null"));
        fourWhenElement.addElement(new TextElement("${criterion.propertyName} ${criterion.sqlOperators} #{criterion.value}"));
        
        XmlElement fiveWhenElement = new XmlElement("when");
        fiveWhenElement.addAttribute(new Attribute("test", "criterion.value == null"));
        fiveWhenElement.addElement(new TextElement("${criterion.propertyName} ${criterion.sqlOperators}"));
        
        chooseElement.addElement(firstWhenElement);
        chooseElement.addElement(secondWhenElement);
        chooseElement.addElement(thirdWhenElement);
        chooseElement.addElement(fourWhenElement);
        chooseElement.addElement(fiveWhenElement);
        
        forEachElement.addElement(chooseElement);
        trimElement.addElement(forEachElement);
        criteriaValidIfElement.addElement(trimElement);
        answer.addElement(criteriaValidIfElement);
        
        parentElement.addElement(answer);
    }

}

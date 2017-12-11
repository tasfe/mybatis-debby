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
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ExampleWhereClauseElementGenerator
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
        trimElement.addElement(getMiddleForEachElement());

        ifElement.addElement(trimElement);

        parentElement.addElement(answer);
    }

    private XmlElement getMiddleForEachElement() {
        StringBuilder sb = new StringBuilder();

        XmlElement middleForEachElement = new XmlElement("foreach");
        middleForEachElement.addAttribute(new Attribute("collection", "criteria.criterions"));
        middleForEachElement.addAttribute(new Attribute("item", "criteria"));

        XmlElement chooseElement = new XmlElement("choose");
        middleForEachElement.addElement(chooseElement);

        XmlElement when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criteria.noValue"));
        when.addElement(new TextElement("and ${criteria.condition}"));
        chooseElement.addElement(when);

        when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criteria.singleValue"));
        sb.setLength(0);
        sb.append("and ${criteria.condition} ");
        when.addElement(new TextElement(sb.toString()));
        when.addElement(getParameterClauseElement(ValueMode.SINGLE));
        chooseElement.addElement(when);

        when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criteria.betweenValue"));
        sb.setLength(0);
        sb.append("and ${criteria.condition} ");
        when.addElement(new TextElement(sb.toString()));
        when.addElement(getParameterClauseElement(ValueMode.TWO));
        chooseElement.addElement(when);

        when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criteria.listValue"));
        when.addElement(new TextElement("and ${criteria.condition}"));
        XmlElement innerForEach = new XmlElement("foreach");
        innerForEach.addAttribute(new Attribute("collection", "criteria.value"));
        innerForEach.addAttribute(new Attribute("item", "listItem"));
        innerForEach.addAttribute(new Attribute("open", "("));
        innerForEach.addAttribute(new Attribute("close", ")"));
        innerForEach.addAttribute(new Attribute("separator", ","));
        innerForEach.addElement(getParameterClauseElement(ValueMode.LIST));
        when.addElement(innerForEach);
        chooseElement.addElement(when);

        return middleForEachElement;
    }

    private XmlElement getParameterClauseElement(ValueMode valueMode) {
        StringBuilder sb = new StringBuilder();
        XmlElement chooseElement = new XmlElement("choose");

        XmlElement when = new XmlElement("when");
        when.addAttribute(new Attribute("test", "criteria.typeHandler == null"));
        if (valueMode == ValueMode.SINGLE) {
            sb.append("#{criteria.value}");
        } else if (valueMode == ValueMode.TWO) {
            sb.append("#{criteria.value} and #{criteria.secondValue}");
        } else if (valueMode == ValueMode.LIST) {
            sb.append("#{listItem}");
        }
        when.addElement(new TextElement(sb.toString()));
        chooseElement.addElement(when);

        XmlElement otherwise = new XmlElement("otherwise");
        sb.setLength(0);
        if (valueMode == ValueMode.SINGLE) {
            sb.append("#{criteria.value, typeHandler = criteria.typeHandler}");
        } else if (valueMode == ValueMode.TWO) {
            sb.append("#{criteria.value, typeHandler = criteria.typeHandler} and #{criteria.secondValue, typeHandler = criteria.typeHandler}");
        } else if (valueMode == ValueMode.LIST) {
            sb.append("#{listItem, typeHandler = criteria.typeHandler}");
        }
        otherwise.addElement(new TextElement(sb.toString()));
        chooseElement.addElement(otherwise);

        return chooseElement;
    }

    private static enum ValueMode {
        SINGLE, TWO, LIST
    }

}

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

import org.mybatis.debby.codegen.XInternalStatements;
import org.mybatis.debby.criteria.EntityCriteria;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 4:27:04 PM
 */
public class XSelectCountByCriteriaElementGenerator extends XAbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", XInternalStatements.SELECT_COUNT_BY_CRITERIA.getId()));
        answer.addAttribute(new Attribute("resultType", "long"));
        answer.addAttribute(new Attribute("parameterType", EntityCriteria.class.getName()));
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(" select count(*) from ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));
        
        sb.setLength(0);
        sb.append("oredCriteriaList != null");
        
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", sb.toString()));
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", "selectWhereSqlFragment"));
        ifElement.addElement(includeElement);
        answer.addElement(ifElement);
        
        parentElement.addElement(answer);
    }

}

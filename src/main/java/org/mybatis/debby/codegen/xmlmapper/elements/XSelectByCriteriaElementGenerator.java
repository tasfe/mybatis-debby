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
 * @date Nov 23, 2017 2:10:36 PM
 */
public class XSelectByCriteriaElementGenerator extends XAbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", XInternalStatements.SELECT_BY_CRITERIA.getId()));
        answer.addAttribute(new Attribute("parameterType", EntityCriteria.class.getName()));
        answer.addAttribute(new Attribute("resultMap", "baseResultMap"));
        
        answer.addElement(new TextElement("select"));
        XmlElement ifElement = new XmlElement("if"); 
        ifElement.addAttribute(new Attribute("test", "distinct")); 
        ifElement.addElement(new TextElement("distinct"));
        answer.addElement(ifElement);
        
        StringBuilder sb = new StringBuilder();
        answer.addElement(getBaseColumnListElement() );
        
        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));
        
        XmlElement oredCriteriaListIfElement= new XmlElement("if");
        oredCriteriaListIfElement.addAttribute(new Attribute("test", "oredCriteriaList != null"));
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", "selectWhereSqlFragment"));
        oredCriteriaListIfElement.addElement(includeElement);
        answer.addElement(oredCriteriaListIfElement);
        
        XmlElement entityOrderListIfElement = new XmlElement("if");
        entityOrderListIfElement.addAttribute(new Attribute("test", "entityOrderList != null"));
        sb.setLength(0);
        sb.append(" ORDER BY ");
        entityOrderListIfElement.addElement(new TextElement(sb.toString()));
        
        XmlElement forEachElement = new XmlElement("foreach");
        forEachElement.addAttribute(new Attribute("collection", "entityOrderList"));
        forEachElement.addAttribute(new Attribute("item", "entityOrder"));
        forEachElement.addAttribute(new Attribute("separator", ","));
        forEachElement.addElement(new TextElement("${entityOrder}"));
        entityOrderListIfElement.addElement(forEachElement);
        
        answer.addElement(entityOrderListIfElement);
        
        parentElement.addElement(answer);
    }

}
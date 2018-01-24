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

import java.util.Iterator;

import org.apache.ibatis.mapping.ResultMapping;

import com.debby.mybatis.core.InternalStatements;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.util.FormattingUtilities;

/**
 * @author rocky.hu
 * @date Nov 21, 2017 5:01:12 PM
 */
public class UpdateSelectiveElementGenerator extends AbstractXmlElementGenerator {
    
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update");
        answer.addAttribute(new Attribute("id", InternalStatements.UPDATE_SELECTIVE.getId()));
        answer.addAttribute(new Attribute("parameterType", "map"));
        
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));
        
        XmlElement dynamicElement = new XmlElement("set");
        answer.addElement(dynamicElement);
        
        Iterator<ResultMapping> iter = getPropertyResultMappings(introspectedContext.getResultMap()).iterator();
        while (iter.hasNext()) {

            ResultMapping resultMapping = iter.next();

            if (isIdResultMapping(resultMapping)) {
                continue;
            }
            
            XmlElement isNotNullElement = new XmlElement("if");
            isNotNullElement.addAttribute(new Attribute("test", FormattingUtilities.getPropertyClause(resultMapping)));
            dynamicElement.addElement(isNotNullElement);
            
            sb.setLength(0);
            sb.append(FormattingUtilities.getEscapedColumnName(resultMapping));
            sb.append(" = "); 
            sb.append(FormattingUtilities.getParameterClause(resultMapping, "record."));
            sb.append(',');
            
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
        
        answer.addElement(getUpdateWhereSqlFragment());
        
        parentElement.addElement(answer);
    }

}

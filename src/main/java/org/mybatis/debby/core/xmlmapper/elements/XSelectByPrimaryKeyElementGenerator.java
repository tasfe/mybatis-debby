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

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.debby.core.XInternalStatements;
import org.mybatis.debby.core.util.XMyBatis3FormattingUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 11:22:10 AM
 */
public class XSelectByPrimaryKeyElementGenerator extends XAbstractXmlElementGenerator {
    
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", XInternalStatements.SELECT_BY_PRIMARY_KEY.getId()));
        answer.addAttribute(new Attribute("resultMap", "baseResultMap"));

        ResultMap resultMap = introspectedContext.getResultMap();
        if (idResultCount(resultMap) > 1) {
            logger.warn("[SelectByPrimaryKey] [{}] : Composite keys is not supported by Mybatis-Debby!", resultMap.getId());
            return;
        } else if (idResultCount(resultMap) == 0) {
            logger.warn("[SelectByPrimaryKey] [{}] : No primary key found!", resultMap.getId());
            return;
        }
        
        ResultMapping idResultMapping = resultMap.getIdResultMappings().get(0);
        answer.addAttribute(new Attribute("parameterType", idResultMapping.getJavaType().getName()));
        
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());

        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));
        
        sb.setLength(0);
        sb.append(" where ");
        sb.append(XMyBatis3FormattingUtilities.getEscapedColumnName(idResultMapping));
        sb.append("=");
        sb.append(XMyBatis3FormattingUtilities.getParameterClause(idResultMapping, null));
        answer.addElement(new TextElement(sb.toString()));
        
        parentElement.addElement(answer);
    }

}

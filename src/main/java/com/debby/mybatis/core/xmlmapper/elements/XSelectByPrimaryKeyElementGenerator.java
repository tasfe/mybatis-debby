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

import com.debby.mybatis.core.constant.XConstants;
import org.apache.ibatis.mapping.ResultMap;

import com.debby.mybatis.core.XInternalStatements;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 23, 2017 11:22:10 AM
 * @see 'org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByPrimaryKeyElementGenerator'
 */
public class XSelectByPrimaryKeyElementGenerator extends XAbstractXmlElementGenerator {
    
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", XInternalStatements.SELECT_BY_PRIMARY_KEY.getId()));
        answer.addAttribute(new Attribute("resultMap", XConstants.BASE_RESULT_MAP_ID));

        ResultMap resultMap = introspectedContext.getResultMap();
        if (getIdResultMappingsCount(resultMap) == 0) {
        	logger.info("Debby-Info : No primary key found, [selectByPrimaryKey] statement will not be generated for [{}]", getMapperInterfaceName(resultMap));
            return;
        }
        
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
        sb.append(getPrimaryKeyParameterClause(resultMap));

        answer.addElement(new TextElement(sb.toString()));
        
        parentElement.addElement(answer);
    }

}

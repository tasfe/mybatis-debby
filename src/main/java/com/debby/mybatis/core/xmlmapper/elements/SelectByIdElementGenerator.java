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

import com.debby.mybatis.core.constant.Constants;
import org.apache.ibatis.mapping.ResultMap;

import com.debby.mybatis.core.InternalStatements;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 11:22:10 AM
 */
public class SelectByIdElementGenerator extends AbstractXmlElementGenerator {
    
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", InternalStatements.SELECT_BY_ID.getId()));
        answer.addAttribute(new Attribute("resultMap", Constants.BASE_RESULT_MAP_ID));

        ResultMap resultMap = introspectedContext.getResultMap();
        if (getIdResultMappingsCount(resultMap) == 0) {
        	logger.info("Debby-Info : No primary key found, [selectById] statement will not be generated for [{}]", getMapperInterfaceName(resultMap));
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());

        sb.setLength(0);
        sb.append("FROM ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));
        
        sb.setLength(0);
        sb.append(" WHERE ");
        sb.append(getPrimaryKeyParameterClause(resultMap));

        answer.addElement(new TextElement(sb.toString()));
        
        parentElement.addElement(answer);
    }

}

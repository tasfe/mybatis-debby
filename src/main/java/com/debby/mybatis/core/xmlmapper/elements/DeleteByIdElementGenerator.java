/**
 *    Copyright 2017-2018 the original author or authors.
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

import org.apache.ibatis.mapping.ResultMap;

import com.debby.mybatis.core.InternalStatements;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 4:46:06 PM
 */
public class DeleteByIdElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete");
        answer.addAttribute(new Attribute("id", InternalStatements.DELETE_BY_ID.getId()));

        ResultMap resultMap = introspectedContext.getResultMap();
        if (getIdResultMappingsCount(resultMap) == 0) {
        	logger.info("Debby-Info : No primary key found, [deleteById] statement will not be generated for [{}]", getMapperInterfaceName(resultMap));
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(" DELETE FROM ");
        sb.append(introspectedContext.getTableName());
        sb.append(" WHERE ");
        sb.append(getPrimaryKeyParameterClause(resultMap));

        answer.addElement(new TextElement(sb.toString()));
        
        parentElement.addElement(answer);
    }

}

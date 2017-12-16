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
import org.mybatis.debby.core.XInternalStatements;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 23, 2017 4:46:06 PM
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.DeleteByPrimaryKeyElementGenerator
 */
public class XDeleteByPrimaryKeyElementGenerator extends XAbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete");
        answer.addAttribute(new Attribute("id", XInternalStatements.DELETE_BY_PRIMARY_KEY.getId()));

        ResultMap resultMap = introspectedContext.getResultMap();
        if (getIdResultMappingsCount(resultMap) == 0) {
            logger.warn("[DeleteByPrimaryKey] : No primary key found and we don't generate 'deleteByPrimaryKey' statement for [{}]!",
                    resultMap.getId().replace(".baseResultMap", ""));
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ");
        sb.append(introspectedContext.getTableName());
        sb.append(" where ");
        sb.append(getPrimaryKeyParameterClause(resultMap));

        answer.addElement(new TextElement(sb.toString()));
        
        parentElement.addElement(answer);
    }

}

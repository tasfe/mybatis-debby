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

import java.util.Iterator;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

import com.debby.mybatis.core.InternalStatements;
import com.debby.mybatis.core.dom.OutputUtilities;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.util.FormattingUtilities;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 9:04:54 PM
 */
public class UpdateByIdElementGenerator extends AbstractXmlElementGenerator {

	@Override
	public void addElements(XmlElement parentElement) {
		
		XmlElement answer = new XmlElement("update");
		answer.addAttribute(new Attribute("id", InternalStatements.UPDATE_BY_ID.getId()));
		answer.addAttribute(new Attribute("parameterType", introspectedContext.getResultMap().getType().getName()));

        ResultMap resultMap = introspectedContext.getResultMap();
        if (getIdResultMappingsCount(resultMap) == 0) {
        	logger.info("Debby-Info : No primary key found, [updateById] statement will not be generated for [{}]", getMapperInterfaceName(resultMap));
            return;
        }

		StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append("SET ");

        Iterator<ResultMapping> iter = getPropertyResultMappings(resultMap).iterator();
        while (iter.hasNext()) {

            ResultMapping resultMapping = iter.next();

            if (isIdResultMapping(resultMapping)) {
                continue;
            }

            sb.append(FormattingUtilities.getEscapedColumnName(resultMapping));
            sb.append(" = "); 
            sb.append(FormattingUtilities.getParameterClause(resultMapping));

            if (iter.hasNext()) {
                sb.append(',');
            }

            answer.addElement(new TextElement(sb.toString()));

            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtilities.xmlIndent(sb, 1);
            }
        }

        sb.setLength(0);
        sb.append(" WHERE ");
        sb.append(getPrimaryKeyParameterClauseForUpdate(resultMap));
        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);
	}

}

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

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.debby.core.XInternalStatements;
import org.mybatis.debby.core.util.XMyBatis3FormattingUtilities;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import com.google.common.base.Strings;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 9:04:54 PM
 */
public class XUpdateByPrimaryKeyElementGenerator extends XAbstractXmlElementGenerator {

	@Override
	public void addElements(XmlElement parentElement) {
		
		XmlElement answer = new XmlElement("update");
		answer.addAttribute(new Attribute("id", XInternalStatements.UPDATE_BY_PRIMARY_KEY.getId()));
		answer.addAttribute(new Attribute("parameterType", introspectedContext.getResultMap().getType().getName()));

        ResultMap resultMap = introspectedContext.getResultMap();
        if (idResultCount(resultMap) > 1) {
            logger.warn("[UpdateByPrimaryKey] [{}] : Composite keys is not supported by Mybatis-Debby!", resultMap.getId());
            return;
        } else if (idResultCount(resultMap) == 0) {
            logger.warn("[UpdateByPrimaryKey] [{}] : No primary key found!", resultMap.getId());
            return;
        }

		StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append("set ");

        Iterator<ResultMapping> iter = introspectedContext.getResultMap().getPropertyResultMappings().iterator();
        while (iter.hasNext()) {

            ResultMapping resultMapping = iter.next();

            if (resultMapping.getFlags() != null && resultMapping.getFlags().size() > 0 || !Strings.isNullOrEmpty(resultMapping.getNestedQueryId()) || 
                    !Strings.isNullOrEmpty(resultMapping.getNestedResultMapId())) {
                continue;
            }

            sb.append(XMyBatis3FormattingUtilities.getEscapedColumnName(resultMapping));
            sb.append(" = "); 
            sb.append(XMyBatis3FormattingUtilities.getParameterClause(resultMapping));

            if (iter.hasNext()) {
                sb.append(',');
            }

            answer.addElement(new TextElement(sb.toString()));

            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtilities.xmlIndent(sb, 1);
            }
        }
        
        ResultMapping idResultMapping = introspectedContext.getResultMap().getIdResultMappings().get(0);

        sb.setLength(0);
        sb.append(" where ");
        sb.append(XMyBatis3FormattingUtilities.getEscapedColumnName(idResultMapping));
        sb.append("=");
        sb.append(XMyBatis3FormattingUtilities.getParameterClause(idResultMapping, null));
        answer.addElement(new TextElement(sb.toString()));
        
        parentElement.addElement(answer);
	}

}

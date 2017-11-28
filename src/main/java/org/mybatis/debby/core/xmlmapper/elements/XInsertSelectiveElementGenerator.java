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

import com.google.common.base.Strings;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.debby.core.XInternalStatements;
import org.mybatis.debby.core.util.XMyBatis3FormattingUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.Iterator;
import java.util.List;

/**
 * @author rocky.hu
 * @date Nov 20, 2017 10:14:03 AM
 */
public class XInsertSelectiveElementGenerator extends XAbstractXmlElementGenerator {
    
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", XInternalStatements.INSERT_SELECTIVE.getId()));
        
        ResultMap resultMap = introspectedContext.getResultMap();
        answer.addAttribute(new Attribute("parameterType", resultMap.getType().getName()));

        addSelectKey(resultMap, answer);

        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement insertTrimElement = new XmlElement("trim");
        insertTrimElement.addAttribute(new Attribute("prefix", "("));
        insertTrimElement.addAttribute(new Attribute("suffix", ")"));
        insertTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        answer.addElement(insertTrimElement);

        XmlElement valuesTrimElement = new XmlElement("trim");
        valuesTrimElement.addAttribute(new Attribute("prefix", "values ("));
        valuesTrimElement.addAttribute(new Attribute("suffix", ")"));
        valuesTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        answer.addElement(valuesTrimElement);

        Iterator<ResultMapping> iter = resultMap.getPropertyResultMappings().iterator();
        while (iter.hasNext()) {
        	ResultMapping resultMapping = iter.next();
        	if (!Strings.isNullOrEmpty(resultMapping.getNestedQueryId()) || !Strings.isNullOrEmpty(resultMapping.getNestedResultMapId())) {
        	    continue;
        	}

            if (resultMapping.getFlags() != null && resultMapping.getFlags().contains(ResultFlag.CONSTRUCTOR)) {
        	    continue;
            }

            if (resultMapping.getFlags() != null && resultMapping.getFlags().size() == 1 && resultMapping.getFlags().contains(ResultFlag.ID)) {
                sb.setLength(0);
                sb.append(XMyBatis3FormattingUtilities.getEscapedColumnName(resultMapping));
                sb.append(',');
                insertTrimElement.addElement(new TextElement(sb.toString()));

                sb.setLength(0);
                sb.append(XMyBatis3FormattingUtilities.getParameterClause(resultMapping));
                sb.append(',');
                valuesTrimElement.addElement(new TextElement(sb.toString()));

                continue;
            }

            XmlElement insertNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(resultMapping.getProperty());
            sb.append(" != null");
            insertNotNullElement.addAttribute(new Attribute("test", sb.toString()));

            sb.setLength(0);
            sb.append(XMyBatis3FormattingUtilities.getEscapedColumnName(resultMapping));
            sb.append(',');
            insertNotNullElement.addElement(new TextElement(sb.toString()));
            insertTrimElement.addElement(insertNotNullElement);

            XmlElement valuesNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(resultMapping.getProperty());
            sb.append(" != null");
            valuesNotNullElement.addAttribute(new Attribute("test", sb.toString()));

            sb.setLength(0);
            sb.append(XMyBatis3FormattingUtilities.getParameterClause(resultMapping));
            sb.append(',');
            valuesNotNullElement.addElement(new TextElement(sb.toString()));
            valuesTrimElement.addElement(valuesNotNullElement);
        }
        
        parentElement.addElement(answer);
    }

}

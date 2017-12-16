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
import com.debby.mybatis.core.XInternalStatements;
import com.debby.mybatis.core.util.XMyBatis3FormattingUtilities;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 21, 2017 5:01:12 PM
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByExampleWithoutBLOBsElementGenerator
 */
public class XUpdateByCriteriaElementGenerator extends XAbstractXmlElementGenerator {
    
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update");
        answer.addAttribute(new Attribute("id", XInternalStatements.UPDATE_BY_CRITERIA.getId()));
        answer.addAttribute(new Attribute("parameterType", "map"));
        
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append("set ");

        Iterator<ResultMapping> iter = getPropertyResultMappings(introspectedContext.getResultMap()).iterator();
        while (iter.hasNext()) {

            ResultMapping resultMapping = iter.next();

            if (isIdResultMapping(resultMapping)) {
                continue;
            }

            sb.append(XMyBatis3FormattingUtilities.getEscapedColumnName(resultMapping));
            sb.append(" = "); 
            sb.append(XMyBatis3FormattingUtilities.getParameterClause(resultMapping, "record."));

            if (iter.hasNext()) {
                sb.append(',');
            }

            answer.addElement(new TextElement(sb.toString()));

            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtilities.xmlIndent(sb, 1);
            }
        }
        
        answer.addElement(getUpdateByCriteriaIfElement());
        
        parentElement.addElement(answer);
    }

}

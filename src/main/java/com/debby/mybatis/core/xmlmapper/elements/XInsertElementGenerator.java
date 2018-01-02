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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.mapping.ResultMap;
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
 * @date Nov 20, 2017 10:14:03 AM
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.InsertElementGenerator
 */
public class XInsertElementGenerator extends XAbstractXmlElementGenerator {
    
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", XInternalStatements.INSERT.getId()));
        
        ResultMap resultMap = introspectedContext.getResultMap();
        answer.addAttribute(new Attribute("parameterType", resultMap.getType().getName()));

        addSelectKey(resultMap, answer);
        
        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();
        
        insertClause.append(" insert into ");
        insertClause.append(introspectedContext.getTableName());
        insertClause.append(" (");
        
        valuesClause.append(" values(");
        
        List<String> valuesClauses = new ArrayList<String>();
        Iterator<ResultMapping> iter = getPropertyResultMappings(resultMap).iterator();
        while (iter.hasNext()) {
        	ResultMapping resultMapping = iter.next();
        	insertClause.append(XMyBatis3FormattingUtilities.getEscapedColumnName(resultMapping));
        	valuesClause.append(XMyBatis3FormattingUtilities.getParameterClause(resultMapping));
        	
        	if (iter.hasNext()) {
                insertClause.append(", "); 
                valuesClause.append(", "); 
            }
        	
        	if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }
        
        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }
        
        parentElement.addElement(answer);
    }

}
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
package org.mybatis.debby.codegen.xmlmapper.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.XConfiguration;
import org.mybatis.debby.codegen.XInternalStatements;
import org.mybatis.debby.codegen.util.XMyBatis3FormattingUtilities;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.db.DatabaseDialects;

import com.google.common.base.Strings;

/**
 * @author rocky.hu
 * @date Nov 20, 2017 10:14:03 AM
 */
public class XInsertElementGenerator extends XAbstractXmlElementGenerator {
    
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", XInternalStatements.INSERT.getId()));
        
        ResultMap resultMap = introspectedContext.getResultMap();
        answer.addAttribute(new Attribute("parameterType", resultMap.getType().getName()));
        
        StringBuilder sb = new StringBuilder();
        
        // Handle <SelectKey> element.
        // Composite keys is not supported.
        int index = 0;
        for (ResultMapping resultMapping : resultMap.getIdResultMappings()) {
            if (!Strings.isNullOrEmpty(resultMapping.getColumn()) && !Strings.isNullOrEmpty(resultMapping.getProperty())) {
                index++;
            }
        }
        
        if (index > 1) {
            logger.warn("Coposite keys is not supported. ResultMap[{}]", resultMap.getId());
            return; 
        } else if (index == 1) {
            ResultMapping idMappings = resultMap.getIdResultMappings().get(0);
            XmlElement selectKeyElement = new XmlElement("selectKey");
            selectKeyElement.addAttribute(new Attribute("keyProperty", idMappings.getProperty()));
            selectKeyElement.addAttribute(new Attribute("resultType", idMappings.getJavaType().getName()));
            selectKeyElement.addAttribute(new Attribute("order", "BEFORE"));
            
            DatabaseDialects[] databaseDialects = DatabaseDialects.values();
            for (int i = 0; i < databaseDialects.length; i++) {
                DatabaseDialects databaseDialect = databaseDialects[i];
                
                sb.setLength(0);
                sb.append("_databaseId == '" + databaseDialect.name().toLowerCase() + "'");
                
                XmlElement ifElement = new XmlElement("if");
                ifElement.addAttribute(new Attribute("test", sb.toString()));
                ifElement.addElement(new TextElement(databaseDialect.getIdentityRetrievalStatement()));
                
                selectKeyElement.addElement(ifElement);
            }
            
            XConfiguration xConfiguration = introspectedContext.getxConfiguration();
            Properties additionalDatabaseDialects =  xConfiguration.getAdditionalDatabaseDialects();
            if (additionalDatabaseDialects != null) {
                for (Map.Entry<Object, Object> property : additionalDatabaseDialects.entrySet()) {
                    String db = (String) property.getKey();
                    String runtimeSqlStatement = (String) property.getValue();
                    
                    sb.setLength(0);
                    sb.append("_databaseId == '" + db.toLowerCase() + "'");
                    
                    XmlElement ifElement = new XmlElement("if");
                    ifElement.addAttribute(new Attribute("test", sb.toString()));
                    ifElement.addElement(new TextElement(runtimeSqlStatement));
                    
                    selectKeyElement.addElement(ifElement);
                }
            }
            
            answer.addElement(selectKeyElement);
        }
        
        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();
        
        insertClause.append(" insert into ");
        insertClause.append(introspectedContext.getTableName());
        insertClause.append(" (");
        
        valuesClause.append(" values(");
        
        List<String> valuesClauses = new ArrayList<String>();
        List<ResultMapping> propertyResultMappings = resultMap.getPropertyResultMappings();
        for (int i=0; i < propertyResultMappings.size(); i++) {
            
        	ResultMapping resultMapping = propertyResultMappings.get(i);
        	if (!Strings.isNullOrEmpty(resultMapping.getNestedQueryId()) || !Strings.isNullOrEmpty(resultMapping.getNestedResultMapId())) {
        	    continue;
        	}
        	
        	insertClause.append(XMyBatis3FormattingUtilities.getEscapedColumnName(resultMapping));
        	valuesClause.append(XMyBatis3FormattingUtilities.getParameterClause(resultMapping));
        	
        	if (i + 1 < propertyResultMappings.size()) {
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

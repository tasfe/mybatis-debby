package org.mybatis.debby.codegen.xmlmapper.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.debby.codegen.XConfiguration;
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
    
    public XInsertElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", "insert"));
        
        ResultMap resultMap = introspectedContext.getResultMap();
        answer.addAttribute(new Attribute("parameterType", resultMap.getType().getName()));
        
        List<ResultMapping> idResultMappingList = new ArrayList<ResultMapping>();
        Iterator<ResultMapping> iter = resultMap.getIdResultMappings().iterator();
        int index = 0;
        while (iter.hasNext()) {
            ResultMapping resultMapping = iter.next();
            if (!Strings.isNullOrEmpty(resultMapping.getColumn()) && !Strings.isNullOrEmpty(resultMapping.getProperty())) {
                idResultMappingList.add(resultMapping);
                index++;
            }
        }
        
        StringBuilder sb = new StringBuilder();
        
        if (index > 1) {
            return; // Composite keys is not supported.
        } else if (index == 1) {
            ResultMapping idMappings = idResultMappingList.get(0);
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
        
        

        
        
        
        parentElement.addElement(answer);
    }

}

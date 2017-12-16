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

import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 17, 2017 2:33:02 PM
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.BaseColumnListElementGenerator
 */
public class XBaseColumnListElementGenerator extends XAbstractXmlElementGenerator {
    
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", "baseColumns"));
        
        StringBuilder sb = new StringBuilder();
        Iterator<ResultMapping> iter = getPropertyResultMappings(introspectedContext.getResultMap()).iterator();
        while (iter.hasNext()) {
            ResultMapping resultMapping = iter.next();
            sb.append(resultMapping.getColumn());
            
            if (iter.hasNext()) {
                sb.append(", ");
            }
            
            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }
        
        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }

        parentElement.addElement(answer);
       
    }

}

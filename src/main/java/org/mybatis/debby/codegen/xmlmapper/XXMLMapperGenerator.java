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
package org.mybatis.debby.codegen.xmlmapper;

import org.mybatis.debby.codegen.XAbstractXmlGenerator;
import org.mybatis.debby.codegen.xmlmapper.elements.XAbstractXmlElementGenerator;
import org.mybatis.debby.codegen.xmlmapper.elements.XBaseColumnListElementGenerator;
import org.mybatis.debby.codegen.xmlmapper.elements.XInsertElementGenerator;
import org.mybatis.debby.codegen.xmlmapper.elements.XTableNameElementGenerator;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;

/**
 * @author rocky.hu
 * @date Nov 17, 2017 11:40:50 AM
 */
public class XXMLMapperGenerator extends XAbstractXmlGenerator {

    public XXMLMapperGenerator() {
        super();
    }

    protected XmlElement getSqlMapElement() {
        XmlElement answer = new XmlElement("mapper");
        
        addTableNameElement(answer);
        addBaseColumnListElement(answer);
        addInsertElement(answer);
        addUpdateByCriteriaElement(answer);
        return answer;
    }
    
    protected void addTableNameElement(XmlElement parentElement) {
        XAbstractXmlElementGenerator elementGenerator = new XTableNameElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addBaseColumnListElement(XmlElement parentElement) {
        XAbstractXmlElementGenerator elementGenerator = new XBaseColumnListElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addInsertElement(XmlElement parentElement) {
        XAbstractXmlElementGenerator elementGenerator = new XInsertElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void initializeAndExecuteGenerator(XAbstractXmlElementGenerator elementGenerator, XmlElement parentElement) {
        elementGenerator.setIntrospectedContext(introspectedContext);
        elementGenerator.addElements(parentElement);
    }

    @Override
    public Document getDocument() {
        Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        document.setRootElement(getSqlMapElement());
        return document;
    }

}

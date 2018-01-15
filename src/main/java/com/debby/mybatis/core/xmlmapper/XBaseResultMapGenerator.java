/**
 * Copyright 2016-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.debby.mybatis.core.xmlmapper;

import com.debby.mybatis.core.XAbstractXmlGenerator;
import com.debby.mybatis.core.dom.XmlConstants;
import com.debby.mybatis.core.dom.xml.Document;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.xmlmapper.elements.XAbstractXmlElementGenerator;
import com.debby.mybatis.core.xmlmapper.elements.XBaseResultMapElementGenerator;

/**
 * @author rocky.hu
 * @date 2018-01-13 6:05 PM
 */
public class XBaseResultMapGenerator extends XAbstractXmlGenerator {

    protected XmlElement getBaseResultMapElement() {
        XmlElement answer = new XmlElement("mapper");
        addBaseResultMapElement(answer);
        return answer;
    }

    protected void addBaseResultMapElement(XmlElement parentElement) {
        XAbstractXmlElementGenerator elementGenerator = new XBaseResultMapElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    protected void initializeAndExecuteGenerator(XAbstractXmlElementGenerator elementGenerator, XmlElement parentElement) {
        elementGenerator.setIntrospectedContext(introspectedContext);
        elementGenerator.addElements(parentElement);
    }

    @Override
    public Document getDocument() {
        Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        document.setRootElement(getBaseResultMapElement());
        return document;
    }
}

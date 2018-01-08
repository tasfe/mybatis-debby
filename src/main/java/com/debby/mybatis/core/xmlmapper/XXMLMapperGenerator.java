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
package com.debby.mybatis.core.xmlmapper;

import com.debby.mybatis.core.XAbstractXmlGenerator;
import com.debby.mybatis.core.XInternalStatements;
import com.debby.mybatis.core.dom.XmlConstants;
import com.debby.mybatis.core.dom.xml.Document;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.xmlmapper.elements.*;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 17, 2017 11:40:50 AM
 * @see 'org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator'
 */
public class XXMLMapperGenerator extends XAbstractXmlGenerator {

    public XXMLMapperGenerator() {
        super();
    }

    protected XmlElement getSqlMapElement() {
        XmlElement answer = new XmlElement("mapper");
        
        addBaseColumnListElement(answer);
        addCriteriaSqlFragmentElement(answer);
        addSelectWhereSqlFragmentElement(answer);
        addUpdateWhereSqlFragmentElement(answer);
        addOrderBySqlFragmentElement(answer);
        addPaginationPrefixSqlFragmentElement(answer);
        addPaginationSuffixSqlFragmentElement(answer);
        addInsertElement(answer);
        addInsertSelectiveElement(answer);
        addUpdateByPrimaryKeyElement(answer);
        addUpdateByPrimaryKeySelectiveElement(answer);
        addUpdateByCriteriaElement(answer);
        addUpdateByCriteriaSelectiveElement(answer);
        addSelectByPrimaryKeyElement(answer);
        addSelectByCriteriaElement(answer);
        addSelectByCriteriaForPaginationElement(answer);
        addSelectPaginationByCriteriaElement(answer);
        addSelectCountByCriteriaElement(answer);
        addDeleteByPrimaryKeyElement(answer);
        addDeleteByCriteriaElement(answer);
        return answer;
    }
    
    protected void addBaseColumnListElement(XmlElement parentElement) {
        XAbstractXmlElementGenerator elementGenerator = new XBaseColumnListElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addCriteriaSqlFragmentElement(XmlElement parentElement) {
        XAbstractXmlElementGenerator elementGenerator = new XCriteriaSqlFragmentElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addSelectWhereSqlFragmentElement(XmlElement parentElement) {
        XAbstractXmlElementGenerator elementGenerator = new XSelectWhereSqlFragmentElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addUpdateWhereSqlFragmentElement(XmlElement parentElement) {
        XAbstractXmlElementGenerator elementGenerator = new XUpdateWhereSqlFragmentElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    protected void addOrderBySqlFragmentElement(XmlElement parentElement) {
        XAbstractXmlElementGenerator elementGenerator = new XOrderBySqlFragmentElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addPaginationPrefixSqlFragmentElement(XmlElement parentElement) {
    	XAbstractXmlElementGenerator elementGenerator = new XPaginationPrefixSqlFragmentElementGenerator();
    	initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addPaginationSuffixSqlFragmentElement(XmlElement parentElement) {
    	XAbstractXmlElementGenerator elementGenerator = new XPaginationSuffixSqlFragmentElementGenerator();
    	initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addInsertElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.INSERT)) {
            XAbstractXmlElementGenerator elementGenerator = new XInsertElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertSelectiveElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.INSERT_SELECTIVE)) {
            XAbstractXmlElementGenerator elementGenerator = new XInsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addUpdateByPrimaryKeyElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.UPDATE_BY_PRIMARY_KEY)) {
            XAbstractXmlElementGenerator elementGenerator = new XUpdateByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByPrimaryKeySelectiveElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.UPDATE_BY_PRIMARY_KEY_SELECTIVE)) {
            XAbstractXmlElementGenerator elementGenerator = new XUpdateByPrimaryKeySelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addUpdateByCriteriaElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.UPDATE_BY_CRITERIA)) {
            XAbstractXmlElementGenerator elementGenerator = new XUpdateByCriteriaElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByCriteriaSelectiveElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.UPDATE_BY_CRITERIA_SELECTIVE)) {
            XAbstractXmlElementGenerator elementGenerator = new XUpdateByCriteriaSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.SELECT_BY_PRIMARY_KEY)) {
            XAbstractXmlElementGenerator elementGenerator = new XSelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addSelectByCriteriaElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.SELECT_BY_CRITERIA)) {
            XAbstractXmlElementGenerator elementGenerator = new XSelectByCriteriaElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectByCriteriaForPaginationElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.INTERNAL_SELECT_PAGINATION_BY_CRITERIA)) {
            XAbstractXmlElementGenerator elementGenerator = new XSelectByCriteriaForPaginationElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectPaginationByCriteriaElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.SELECT_PAGINATION_BY_CRITERIA)) {
            XAbstractXmlElementGenerator elementGenerator = new XSelectPaginationByCriteriaElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addSelectCountByCriteriaElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.SELECT_COUNT_BY_CRITERIA)) {
            XAbstractXmlElementGenerator elementGenerator = new XSelectCountByCriteriaElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addDeleteByPrimaryKeyElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.DELETE_BY_PRIMARY_KEY)) {
            XAbstractXmlElementGenerator elementGenerator = new XDeleteByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addDeleteByCriteriaElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(XInternalStatements.DELETE_BY_CRITERIA)) {
            XAbstractXmlElementGenerator elementGenerator = new XDeleteByCriteriaElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
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

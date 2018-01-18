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

import com.debby.mybatis.core.AbstractXmlGenerator;
import com.debby.mybatis.core.InternalStatements;
import com.debby.mybatis.core.dom.XmlConstants;
import com.debby.mybatis.core.dom.xml.Document;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.xmlmapper.elements.*;
import com.debby.mybatis.core.xmlmapper.elements.sql.SqlCriteriaFragmentElementGenerator;
import com.debby.mybatis.core.xmlmapper.elements.sql.SqlOrderByFragmentElementGenerator;
import com.debby.mybatis.core.xmlmapper.elements.sql.SqlPaginationPrefixFragmentElementGenerator;
import com.debby.mybatis.core.xmlmapper.elements.sql.SqlPaginationSuffixFragmentElementGenerator;
import com.debby.mybatis.core.xmlmapper.elements.sql.SqlSelectWhereFragmentElementGenerator;
import com.debby.mybatis.core.xmlmapper.elements.sql.SqlUpdateWhereFragmentElementGenerator;
import com.debby.mybatis.core.xmlmapper.elements.sql.SqlBaseColumnsElementGenerator;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 17, 2017 11:40:50 AM
 * @see 'org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator'
 */
public class XMLMapperGenerator extends AbstractXmlGenerator {

    public XMLMapperGenerator() {
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
        addUpdateByIdElement(answer);
        addUpdateByIdSelectiveElement(answer);
        addUpdateElement(answer);
        addUpdateSelectiveElement(answer);
        addSelectByIdElement(answer);
        addSelectListElement(answer);
        addSelectListForPagingElement(answer);
        addSelectPageElement(answer);
        addSelectCountElement(answer);
        addDeleteByIdElement(answer);
        addDeleteElement(answer);
        return answer;
    }
    
    protected void addBaseColumnListElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new SqlBaseColumnsElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addCriteriaSqlFragmentElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new SqlCriteriaFragmentElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addSelectWhereSqlFragmentElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new SqlSelectWhereFragmentElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addUpdateWhereSqlFragmentElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new SqlUpdateWhereFragmentElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    protected void addOrderBySqlFragmentElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new SqlOrderByFragmentElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    
    protected void addPaginationPrefixSqlFragmentElement(XmlElement parentElement) {
    	if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.INTERNAL_SELECT_LIST_FOR_PAGING)) {
    		AbstractXmlElementGenerator elementGenerator = new SqlPaginationPrefixFragmentElementGenerator();
        	initializeAndExecuteGenerator(elementGenerator, parentElement);
    	}
    }
    
    protected void addPaginationSuffixSqlFragmentElement(XmlElement parentElement) {
    	if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.INTERNAL_SELECT_LIST_FOR_PAGING)) {
    		AbstractXmlElementGenerator elementGenerator = new SqlPaginationSuffixFragmentElementGenerator();
        	initializeAndExecuteGenerator(elementGenerator, parentElement);
    	}
    }
    
    protected void addInsertElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.INSERT)) {
            AbstractXmlElementGenerator elementGenerator = new InsertElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertSelectiveElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.INSERT_SELECTIVE)) {
            AbstractXmlElementGenerator elementGenerator = new InsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addUpdateByIdElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.UPDATE_BY_ID)) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByIdElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByIdSelectiveElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.UPDATE_BY_ID_SELECTIVE)) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByIdSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addUpdateElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.UPDATE)) {
            AbstractXmlElementGenerator elementGenerator = new UpdateElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateSelectiveElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.UPDATE_SELECTIVE)) {
            AbstractXmlElementGenerator elementGenerator = new UpdateSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addSelectByIdElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.SELECT_BY_ID)) {
            AbstractXmlElementGenerator elementGenerator = new SelectByIdElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addSelectListElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.SELECT_LIST)) {
            AbstractXmlElementGenerator elementGenerator = new SelectListElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectListForPagingElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.INTERNAL_SELECT_LIST_FOR_PAGING)) {
            AbstractXmlElementGenerator elementGenerator = new SelectListForPagingElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectPageElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.SELECT_PAGE)) {
            AbstractXmlElementGenerator elementGenerator = new SelectPageElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addSelectCountElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.SELECT_COUNT)) {
            AbstractXmlElementGenerator elementGenerator = new SelectCountElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addDeleteByIdElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.DELETE_BY_ID)) {
            AbstractXmlElementGenerator elementGenerator = new DeleteByIdElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void addDeleteElement(XmlElement parentElement) {
        if (!introspectedContext.getAlreadyOwnedInternalStatements().contains(InternalStatements.DELETE)) {
            AbstractXmlElementGenerator elementGenerator = new DeleteElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
    
    protected void initializeAndExecuteGenerator(AbstractXmlElementGenerator elementGenerator, XmlElement parentElement) {
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

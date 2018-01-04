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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

import com.debby.mybatis.core.XAbstractGenerator;
import com.debby.mybatis.core.dialect.Dialect;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.util.XMyBatis3FormattingUtilities;
import com.debby.mybatis.exception.IdConfigException;
import com.debby.mybatis.util.BeanUtils;
import com.debby.mybatis.util.ReflectUtils;
import com.debby.mybatis.util.StringUtils;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 17, 2017 2:20:48 PM
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator
 */
public abstract class XAbstractXmlElementGenerator extends XAbstractGenerator {
    
    public abstract void addElements(XmlElement parentElement);

    /**
     * Get "baseColumns" include element.
     *
     * @return
     */
    protected XmlElement getBaseColumnListElement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", "baseColumns")); 
        return answer;
    }
    
    /**
     * Get "if" element for "updateByCriteria" statement.
     *
     * @return
     */
    protected XmlElement getUpdateByCriteriaIfElement() {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "_parameter != null and _parameter.updatedCriteria != null"));

        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", "updateWhereSqlFragment"));
        ifElement.addElement(includeElement);

        return ifElement;
    }

    /**
     * Determine if is a id result mapping.
     *
     * @param resultMapping
     * @return
     */
    protected boolean isIdResultMapping(ResultMapping resultMapping) {
        if (resultMapping.getFlags() != null && resultMapping.getFlags().size() == 1 && resultMapping.getFlags().contains(ResultFlag.ID)) {
            return true;
        }

        return false;
    }

    /**
     * Get <id/> result mappings size.
     *
     * @param resultMap
     * @return
     */
    protected int getIdResultMappingsCount(ResultMap resultMap) {
        return getIdResultMappings(resultMap).size();
    }

    /**
     * Get <id/> result mappings.
     *
     * @param resultMap
     * @return
     */
    protected List<ResultMapping> getIdResultMappings(ResultMap resultMap) {
        List<ResultMapping> idResultMappings = new ArrayList<ResultMapping>();
        if (resultMap.getIdResultMappings() != null && resultMap.getIdResultMappings().size() > 0) {
            Iterator<ResultMapping> iter = resultMap.getIdResultMappings().iterator();
            while (iter.hasNext()) {
                ResultMapping idResultMapping = iter.next();
                if (idResultMapping.getFlags() != null && idResultMapping.getFlags().size() == 1 && idResultMapping.getFlags().contains(ResultFlag.ID)) {
                    idResultMappings.add(idResultMapping);
                }
            }
        }
        return idResultMappings;
    }

    /**
     * Get property result mappings.
     * <p>
     *     Exclude nested result mappings.
     * </p>
     * @param resultMap
     * @return
     */
    protected List<ResultMapping> getPropertyResultMappings(ResultMap resultMap) {
        List<ResultMapping> propertyResultMappings = new ArrayList<ResultMapping>();
        if (resultMap.getPropertyResultMappings() != null && resultMap.getPropertyResultMappings().size() > 0) {
            Iterator<ResultMapping> iter = resultMap.getPropertyResultMappings().iterator();
            while (iter.hasNext()) {
                ResultMapping propertyResultMapping = iter.next();
                if (!StringUtils.isNullOrEmpty(propertyResultMapping.getNestedQueryId()) || !StringUtils.isNullOrEmpty(propertyResultMapping.getNestedResultMapId())) {
                    continue;
                }
                propertyResultMappings.add(propertyResultMapping);
            }
        }

        return propertyResultMappings;
    }

    /**
     * Get primary key parameter clause.
     * <p>
     *     For "select", "delete".
     * </p>
     *
     * @param resultMap
     * @return
     */
    protected String getPrimaryKeyParameterClause(ResultMap resultMap) {
        StringBuilder sb = new StringBuilder();
        List<ResultMapping> idResultMappingList = getIdResultMappings(resultMap);
        for (int i=0; i<idResultMappingList.size();i++) {
            if (i > 0) {
                sb.append(" and ");
            }
            sb.append(XMyBatis3FormattingUtilities.getEscapedColumnName(idResultMappingList.get(i)));
            sb.append("=");

            String propertyName = idResultMappingList.get(i).getProperty();
            if (propertyName.indexOf(".") != -1) {
                propertyName = propertyName.substring(propertyName.lastIndexOf(".") + 1);
            }
            sb.append(XMyBatis3FormattingUtilities.getParameterClause(idResultMappingList.get(i), propertyName, null));
        }
        return sb.toString();
    }

    /**
     * Get primary key parameter clause for update statement.
     *
     * @param resultMap
     * @return
     */
    protected String getPrimaryKeyParameterClauseForUpdate(ResultMap resultMap) {
        StringBuilder sb = new StringBuilder();
        List<ResultMapping> idResultMappingList = getIdResultMappings(resultMap);
        for (int i=0; i<idResultMappingList.size();i++) {
            if (i > 0) {
                sb.append(" and ");
            }
            sb.append(XMyBatis3FormattingUtilities.getEscapedColumnName(idResultMappingList.get(i)));
            sb.append("=");
            sb.append(XMyBatis3FormattingUtilities.getParameterClause(idResultMappingList.get(i)));
        }
        return sb.toString();
    }
    
    protected String getMapperInterfaceName(ResultMap baseResultMap) {
    	return baseResultMap.getId().replace(".baseResultMap", "");
    }

    /**
     * Add 'SelectKey' element for insert statement.
     *
     * @param resultMap
     * @param parentElement
     */
    protected void addSelectKey(ResultMap resultMap, XmlElement parentElement) {
        if (getIdResultMappingsCount(resultMap) > 0) {

            Class<?> entityType = resultMap.getType();

            List<ResultMapping> idResultMappingList = getIdResultMappings(resultMap);
            
            String idProperty = null;
            
            Field idField = null;
            if (idResultMappingList.size() == 1) {
            	ResultMapping idResultMapping = idResultMappingList.get(0);
            	idProperty = idResultMapping.getProperty();
            	idField = ReflectUtils.findField(entityType, idProperty);
            	Id id = idField.getAnnotation(Id.class);
            	if (id == null) {
            		return;
            	}
            } else if (idResultMappingList.size() > 1) {// composite key
            	Field embeddedIdField = BeanUtils.findField(entityType, EmbeddedId.class);
            	if (embeddedIdField == null) {
            		return;
            	}
        		Class<?> idType = embeddedIdField.getType();
        		Embeddable embeddable = idType.getAnnotation(Embeddable.class);
        		if (embeddable == null) {
                    throw new IdConfigException("The [" + idType + "] must be annotated with @Embeddable!");
        		}
        		idField = BeanUtils.findField(idType, GeneratedValue.class);
        		if (idField == null) {
        			return;
        		}
        		
        		idProperty = embeddedIdField.getName() + "." + idField.getName();
            }
            
            GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
            if (generatedValue == null) {
            	return;
            }
            
            GenerationType generationType = generatedValue.strategy();
            if (generationType == null) {
            	generationType = GenerationType.IDENTITY;
            }
            if (generationType != GenerationType.IDENTITY && generationType != GenerationType.SEQUENCE) {
            	throw new IdConfigException("Only supports IDENTITY or SEQUENCE strategy for id generation!");
            }
            
        	Dialect dialect = introspectedContext.getDebbyConfiguration().getDialect();
            
            XmlElement selectKeyElement = new XmlElement("selectKey");
            selectKeyElement.addAttribute(new Attribute("keyProperty", idProperty));
            selectKeyElement.addAttribute(new Attribute("resultType", idField.getType().getName()));
            if (generationType == GenerationType.IDENTITY) {
                selectKeyElement.addAttribute(new Attribute("order", "AFTER" ));
                selectKeyElement.addElement(new TextElement(dialect.getIdentityColumnStrategy().getIdentitySelectString()));
            } else if (generationType == GenerationType.SEQUENCE){
            	String generator = generatedValue.generator();
            	SequenceGenerator sequenceGenerator = idField.getAnnotation(SequenceGenerator.class);
            	if (StringUtils.isNullOrEmpty(generator) || sequenceGenerator == null || !generator.equals(sequenceGenerator.name())) {
            		throw new IdConfigException("No sequence generator is configed for '" + idProperty + "'");
            	}
            	String sequenceName = sequenceGenerator.sequenceName();
                selectKeyElement.addAttribute(new Attribute("order", "BEFORE" ));
                selectKeyElement.addElement(new TextElement(dialect.getSequenceNextValString(sequenceName)));
            }
            
            parentElement.addElement(selectKeyElement);
            
        }
    }

}

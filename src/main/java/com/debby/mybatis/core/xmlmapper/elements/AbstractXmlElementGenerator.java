/**
 *    Copyright 2017-2018 the original author or authors.
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

import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

import com.debby.mybatis.annotation.KeyGenerationStrategy;
import com.debby.mybatis.annotation.KeySequenceGenerator;
import com.debby.mybatis.annotation.MappingId;
import com.debby.mybatis.core.constant.Constants;
import com.debby.mybatis.core.dialect.Dialect;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.helper.EntityHelper;
import com.debby.mybatis.core.util.FormattingUtilities;
import com.debby.mybatis.core.xmlmapper.AbstractGenerator;
import com.debby.mybatis.exception.MappingException;
import com.debby.mybatis.util.ReflectUtils;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date Nov 17, 2017 2:20:48 PM
 */
public abstract class AbstractXmlElementGenerator extends AbstractGenerator {
    
    public abstract void addElements(XmlElement parentElement);

    /**
     * Get base columns include element.
     *
     * @return
     */
    protected XmlElement getBaseColumnListElement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", Constants.BASE_COLUMNS_ID));
        return answer;
    }

    protected XmlElement getUpdateWhereSqlFragment() {
        XmlElement updateWhereElement = new XmlElement("include");
        updateWhereElement.addAttribute(new Attribute("refid", Constants.UPDATE_WHERE_SQL_FRAGMENT_ID));
        return updateWhereElement;
    }

    protected XmlElement getOrderBySqlFragment() {
        XmlElement orderByElement = new XmlElement("include");
        orderByElement.addAttribute(new Attribute("refid", Constants.ORDER_BY_SQL_FRAGMENT_ID));
        return orderByElement;
    }

    protected XmlElement getWhereSqlFragment() {
        XmlElement whereElement = new XmlElement("include");
        whereElement.addAttribute(new Attribute("refid", Constants.WHERE_SQL_FRAGMENT_ID));
        return whereElement;
    }
    
    protected XmlElement getPaginationPrefixSqlFragment() {
    	XmlElement element = new XmlElement("include");
    	element.addAttribute(new Attribute("refid", Constants.PREFIX_PAGINATION_SQL_FRAGMENT_ID));
        return element;
    }
    
    protected XmlElement getPaginationSuffixSqlFragment() {
    	XmlElement element = new XmlElement("include");
    	element.addAttribute(new Attribute("refid", Constants.SUFFIX_PAGINATION_SQL_FRAGMENT_ID));
        return element;
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
            sb.append(FormattingUtilities.getEscapedColumnName(idResultMappingList.get(i)));
            sb.append("=");

            String propertyName = idResultMappingList.get(i).getProperty();
            if (propertyName.indexOf(".") != -1) {
                propertyName = propertyName.substring(propertyName.lastIndexOf(".") + 1);
            }
            sb.append(FormattingUtilities.getParameterClause(idResultMappingList.get(i), propertyName, null));
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
            sb.append(FormattingUtilities.getEscapedColumnName(idResultMappingList.get(i)));
            sb.append("=");
            sb.append(FormattingUtilities.getParameterClause(idResultMappingList.get(i)));
        }
        return sb.toString();
    }
    
    protected String getMapperInterfaceName(ResultMap baseResultMap) {
    	return baseResultMap.getId().replace("." + Constants.BASE_RESULT_MAP_ID, "");
    }

    /**
     * Handle Generated Identifier.
     *
     * @param resultMap
     * @param parentElement
     */
    protected void addGeneratedIdentifier(ResultMap resultMap, XmlElement parentElement) {
        if (getIdResultMappingsCount(resultMap) > 0) {
        	List<ResultMapping> idResultMappingList = getIdResultMappings(resultMap);
            Class<?> entityType = resultMap.getType();
            String idColumn = null;
            String idProperty = null;
            Field idField = null;
            if (idResultMappingList.size() == 1) {
            	ResultMapping idResultMapping = idResultMappingList.get(0);
            	idProperty = idResultMapping.getProperty();
            	idColumn = idResultMapping.getColumn();
            	idField = ReflectUtils.findField(entityType, idProperty);
            	MappingId mappingId = idField.getAnnotation(MappingId.class);
            	if (mappingId == null) {
            		throw new MappingException("No MappingId annotation found on + [" + idProperty + "]");
            	}
            } else if (idResultMappingList.size() > 1) {
            	Field compositeIdField = EntityHelper.getCompositeIdField(entityType);
            	if (compositeIdField == null) {
            		throw new MappingException("No MappingCompositeId annotation found in [" + entityType.getName() + "]");
            	}
        		idField = EntityHelper.getGeneratedValueField(compositeIdField.getType());
        		if (idField == null) {
                    throw new MappingException("No MappingId annotation found in + [" + entityType.getName() + "]");
        		}

        		idProperty = compositeIdField.getName() + "." + idField.getName();
        		for (ResultMapping resultMapping : idResultMappingList) {
        			if (idProperty.equals(resultMapping.getProperty())) {
        				idColumn = resultMapping.getColumn();
        				break;
        			}
        		}
            }

            MappingId mappingId = idField.getAnnotation(MappingId.class);
            boolean generatedValue = mappingId.generatedValue();
            if (!generatedValue) {
                return;
            }

            KeyGenerationStrategy keyGenerationStrategy = mappingId.generationStrategy();

        	Dialect dialect = introspectedContext.getDebbyConfiguration().getDialect();

            if (keyGenerationStrategy == KeyGenerationStrategy.IDENTITY) {
            	if (!dialect.supportsIdentityColumns()) {
            		throw new MappingException(dialect.getClass().getName() + " does not support identity" );
            	}
            	
            	parentElement.addAttribute(new Attribute("keyColumn", idColumn));
            	parentElement.addAttribute(new Attribute("keyProperty", idProperty));
            	parentElement.addAttribute(new Attribute("useGeneratedKeys", "true"));
            } else if (keyGenerationStrategy == KeyGenerationStrategy.SEQUENCE){
            	XmlElement selectKeyElement = new XmlElement("selectKey");
                selectKeyElement.addAttribute(new Attribute("keyProperty", idProperty));
                selectKeyElement.addAttribute(new Attribute("resultType", idField.getType().getName()));
            	String generator = mappingId.generator();
            	KeySequenceGenerator sequenceGenerator = idField.getAnnotation(KeySequenceGenerator.class);
            	if (StringUtils.isNullOrEmpty(generator) || sequenceGenerator == null || !generator.equals(sequenceGenerator.name())) {
            		throw new MappingException("No sequence generator is configed for '" + idProperty + "'");
            	}
            	String sequenceName = sequenceGenerator.sequenceName();
                selectKeyElement.addAttribute(new Attribute("order", "BEFORE" ));
                selectKeyElement.addElement(new TextElement(dialect.getSequenceNextValString(sequenceName)));
                parentElement.addElement(selectKeyElement);
            }

        }
    }

}

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
package com.debby.mybatis.criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debby.mybatis.core.ResultMapRegistry;
import com.debby.mybatis.core.helper.EntityHelper;
import com.debby.mybatis.criteria.filter.PropertyFilterMode;

/**
 * @author rocky.hu
 * @date 2017-12-09 3:40 PM
 */
public class EntityCriteria {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityCriteria.class);

	private final Class<?> entityType;
	
    private Integer maxResults;
    private Integer firstResult; // the index of the first result, which is start with 0
    private Boolean distinct;
    private List<Criteria> criteriaList = new ArrayList<Criteria>();
    private List<Order> orderList = new ArrayList<Order>();
    
    /** property query filter */
    private PropertyFilterMode propertyFilterMode = PropertyFilterMode.EXCLUDE;
    private List<String> propertyFilterList = new ArrayList<String>();

    public EntityCriteria(Class<?> clazz) {
    	this.entityType = clazz;
    }
    
    public static EntityCriteria forEntity(Class<?> clazz) {
    	return new EntityCriteria(clazz);
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (criteriaList.size() == 0) {
            criteriaList.add(criteria);
        }
        return criteria;
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        criteriaList.add(criteria);
        return criteria;
    }

    private Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(entityType.getName());
        return criteria;
    }

    public EntityCriteria addOrder(Order ordering) {
        String propertyName = ordering.getPropertyName();
        ResultMapping resultMapping = ResultMapRegistry.getResultMapping(entityType.getName(), propertyName);
        String column = resultMapping.getColumn();
        ordering.setPropertyName(column);
        orderList.add(ordering);
        return this;
    }
    
    public EntityCriteria addFilterProperty(String property) {
    	propertyFilterList.add(property);
    	return this;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    public List<Criteria> getCriteriaList() {
        return criteriaList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

	public PropertyFilterMode getPropertyFilterMode() {
		return propertyFilterMode;
	}

	public void setPropertyFilterMode(PropertyFilterMode propertyFilterMode) {
		this.propertyFilterMode = propertyFilterMode;
	}

	public List<String> getPropertyFilterList() {
		return propertyFilterList;
	}

	public String getColumns() {
		StringBuilder sb = new StringBuilder();
		List<ResultMapping> resultMappingList = EntityHelper.getPropertyResultMappings(entityType);
		List<String> columnList = new ArrayList<String>();
		for (int i=0; i<resultMappingList.size(); i++) {
			ResultMapping resultMapping = resultMappingList.get(i);
			String propertyName = resultMapping.getProperty();
			String column = resultMapping.getColumn();
			
			if(propertyName.contains(".")) {
				propertyName = propertyName.substring(0, propertyName.indexOf("."));
			}
			
			if (propertyFilterMode == PropertyFilterMode.EXCLUDE) {
				if (propertyFilterList.contains(propertyName)) {
					continue;
				}
			} else {
				if (!propertyFilterList.contains(propertyName)) {
					continue;
				}
			}
			
			columnList.add(column);
		}
		
		Iterator<String> iter = columnList.iterator();
		while (iter.hasNext()) {
			sb.append(iter.next());
			if (iter.hasNext()) {
				sb.append(", ");
			}
		}
		
		LOGGER.debug("[{}][COLUMNS]: [{}]", entityType.getName(), sb.toString());
		return sb.toString();
	}

}

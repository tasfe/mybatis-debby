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

import org.apache.ibatis.mapping.ResultMapping;
import com.debby.mybatis.core.XResultMapRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rocky.hu
 * @date 2017-12-09 3:40 PM
 */
public class EntityCriteria {

    private final String entityOrClassName;

    private Integer maxResults;
    private Integer firstResult;// the index of the first result, which is start with 0
    private Boolean distinct;

    private List<Criteria> criteriaList = new ArrayList<Criteria>();
    private List<Order> orderList = new ArrayList<Order>();

    public EntityCriteria(String entityOrClassName) {
        this.entityOrClassName = entityOrClassName;
    }

    public EntityCriteria(Class<?> clazz) {
        this.entityOrClassName = clazz.getName();
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
        Criteria criteria = new Criteria(entityOrClassName);
        return criteria;
    }

    public EntityCriteria addOrder(Order ordering) {
        String propertyName = ordering.getPropertyName();
        ResultMapping resultMapping = XResultMapRegistry.getResultMapping(entityOrClassName, propertyName);
        String column = resultMapping.getColumn();
        ordering.setPropertyName(column);
        orderList.add(ordering);
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

}

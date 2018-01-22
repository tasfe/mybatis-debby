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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debby.mybatis.criteria.filter.PropertyFilter;
import com.debby.mybatis.criteria.filter.PropertyFilterMode;
import com.debby.mybatis.criteria.limit.RowLimiter;
import com.debby.mybatis.criteria.sort.Order;
import com.debby.mybatis.criteria.sort.OrderBy;

/**
 * @author rocky.hu
 * @date 2017-12-09 3:40 PM
 */
public class EntityCriteria {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityCriteria.class);

	private PropertyFilter propertyFilter;
	private OrderBy orderBy;
	private RowLimiter rowLimiter;
	
	private String criteriaSql;
	
	private Boolean distinct;
	private List<Criteria> criteriaList = new ArrayList<Criteria>();

	public EntityCriteria(Class<?> entityType) {
		this.propertyFilter = new PropertyFilter(entityType);
		this.orderBy = new OrderBy(entityType);
		this.rowLimiter = new RowLimiter();
	}

	public static EntityCriteria forEntity(Class<?> entityType) {
		return new EntityCriteria(entityType);
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

	public void isNull(String propertyName) {
		
	}
	
	public void filter(String[] properties) {
		propertyFilter.add(properties);
	}
	
	public void filter(PropertyFilterMode propertyFilterMode, String[] properties) {
		if (propertyFilterMode != null) {
			propertyFilter.setFilterMode(propertyFilterMode);
		}
		propertyFilter.add(properties);
	}
	
	public void orderBy(Order... orders) {
		if (orders != null && orders.length > 0) {
			for (int i=0; i<orders.length; i++) {
				orderBy.addOrder(orders[i]);
			}
		}
	}
	
	public void limit(int firstRowIndex, int maxRows) {
		rowLimiter.setFirstResult(firstRowIndex);
		rowLimiter.setMaxResults(maxRows);
	}
	
	public void limit(int maxRows) {
		rowLimiter.setMaxResults(maxRows);
	}

	public EntityCriteria build() {
		return this;
	}

}

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

import com.debby.mybatis.criteria.filter.PropertyFilter;
import com.debby.mybatis.criteria.filter.PropertyFilterMode;
import com.debby.mybatis.criteria.limit.RowLimiter;
import com.debby.mybatis.criteria.sort.Order;
import com.debby.mybatis.criteria.sort.OrderBy;

/**
 * @author rocky.hu
 * @date Jan 23, 2018 4:48:15 PM
 */
public class EntityCriteriaBuilder {
	
	private PropertyFilter propertyFilter;
	private OrderBy orderBy;
	private RowLimiter rowLimiter;
	private CriteriaBuilder criteriaBuilder;

	private Boolean distinct;
	private List<Criteria> criteriaList = new ArrayList<Criteria>();

	public EntityCriteriaBuilder(Class<?> entityType) {
		this.propertyFilter = new PropertyFilter(entityType);
		this.orderBy = new OrderBy(entityType);
		this.rowLimiter = new RowLimiter();
		this.criteriaBuilder = new CriteriaBuilder(entityType);
	}

	public static EntityCriteriaBuilder forEntity(Class<?> entityType) {
		return new EntityCriteriaBuilder(entityType);
	}

	/**
	 * Filter properties.
	 * 
	 * @param properties
	 * @return
	 */
	public EntityCriteriaBuilder filter(String[] properties) {
		propertyFilter.add(properties);
		return this;
	}

	/**
	 * Filter properties with filter mode.
	 * 
	 * @param propertyFilterMode
	 * @param properties
	 * @return
	 */
	public EntityCriteriaBuilder filter(PropertyFilterMode propertyFilterMode, String[] properties) {
		if (propertyFilterMode != null) {
			propertyFilter.setFilterMode(propertyFilterMode);
		}
		propertyFilter.add(properties);
		return this;
	}

	/**
	 * Multiple Criterias will be finally associated by SQL "OR" operator.
	 * 
	 * @param criterias
	 * @return
	 */
	public EntityCriteriaBuilder where(Criteria... criterias) {
		if (criterias != null && criterias.length > 0) {
			for (Criteria criteria : criterias) {
				criteriaList.add(criteria);
			}
		}
		return this;
	}
	
	public EntityCriteriaBuilder where() {
		return this;
	}
	
	public EntityCriteriaBuilder and() {
		
	}
	
	public EntityCriteriaBuilder or() {
		
	}

	/**
	 * Set distinct query.
	 * 
	 * @return
	 */
	public EntityCriteriaBuilder distinct() {
		distinct = true;
		return this;
	}

	/**
	 * Set order by clause.
	 * 
	 * @param orders
	 * @return
	 */
	public EntityCriteriaBuilder orderBy(Order... orders) {
		if (orders != null && orders.length > 0) {
			for (int i = 0; i < orders.length; i++) {
				orderBy.addOrder(orders[i]);
			}
		}
		return this;
	}

	/**
	 * Limit the position and count to select.
	 * 
	 * @param firstRow
	 * @param maxRows
	 * @return
	 */
	public EntityCriteriaBuilder limit(int firstRow, int maxRows) {
		rowLimiter.setFirstResult(firstRow);
		rowLimiter.setMaxResults(maxRows);
		return this;
	}

	/**
	 * Limit the max row to select.
	 * 
	 * @param maxRows
	 * @return
	 */
	public EntityCriteriaBuilder limit(int maxRows) {
		rowLimiter.setMaxResults(maxRows);
		return this;
	}
	
	public EntityCriteria bulid() {
		EntityCriteria entityCriteria = new EntityCriteria();
		entityCriteria.setColumns(propertyFilter.getColumns());
		entityCriteria.setCriteriaList(this.criteriaList);
		entityCriteria.setDistinct(distinct);
		entityCriteria.setFirstResult(rowLimiter.getFirstResult());
		entityCriteria.setMaxResults(rowLimiter.getMaxResults());
		entityCriteria.setOrderList(orderBy.getOrderList());
		
		return entityCriteria;
	}

}

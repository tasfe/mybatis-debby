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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debby.mybatis.criteria.criterion.Criterion;
import com.debby.mybatis.criteria.criterion.Junction;
import com.debby.mybatis.criteria.criterion.LogicalCriterion;
import com.debby.mybatis.criteria.criterion.SimpleCriterion;
import com.debby.mybatis.criteria.filter.PropertyFilter;
import com.debby.mybatis.criteria.limit.RowLimiter;
import com.debby.mybatis.criteria.sort.Order;
import com.debby.mybatis.criteria.sort.OrderBy;
import com.debby.mybatis.util.Asserts;

/**
 * @author rocky.hu
 * @date Jan 23, 2018 4:48:15 PM
 */
public class EntityCriteriaBuilder {
	
	private final Logger LOGGER = LoggerFactory.getLogger(EntityCriteriaBuilder.class);
	
	private final Class<?> entityType;
	private Boolean distinct;
	private PropertyFilter propertyFilter;
	private OrderBy orderBy;
	private RowLimiter rowLimiter;
	private List<Criterion> criterionList = new ArrayList<Criterion>();
	
	public EntityCriteriaBuilder(Class<?> entityType) {
		this.entityType = entityType;
		this.propertyFilter = new PropertyFilter(entityType);
		this.orderBy = new OrderBy(entityType);
		this.rowLimiter = new RowLimiter();
	}

	/**
	 * Filter properties.
	 * 
	 * @param exclude
	 * @param properties
	 * @return
	 */
	public EntityCriteriaBuilder filter(boolean exclude, String[] properties) {
		propertyFilter.setExclude(exclude);
		propertyFilter.add(properties);
		return this;
	}

	/**
	 * Filter by a restriction.
	 * 
	 * @param criterions
	 * @return
	 */
	public EntityCriteriaBuilder where(Criterion... criterions) {
		Asserts.notNull(criterions);
		criterionList.addAll(Arrays.asList(criterions));
		return this;
	}
	
	
	/**
	 * Add a restriction.
	 * 
	 * @param criterion
	 * @return
	 */
	public EntityCriteriaBuilder add(Criterion criterion) {
		Asserts.notNull(criterion);
		criterionList.add(criterion);
		return this;
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
		Asserts.notEmpty(orders);
		for (int i = 0; i < orders.length; i++) {
			orderBy.addOrder(orders[i]);
		}
		return this;
	}
	
	/**
	 * Add order by element.
	 * 
	 * @param order
	 * @return
	 */
	public EntityCriteriaBuilder addOrder(Order order) {
		Asserts.notNull(order);
		orderBy.addOrder(order);
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
	
	public PropertyFilter getPropertyFilter() {
		return propertyFilter;
	}

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public RowLimiter getRowLimiter() {
		return rowLimiter;
	}

	public List<Criterion> getCriterionList() {
		return criterionList;
	}

	public Integer getFirstResult() {
		return rowLimiter.getFirstResult();
	}
	
	public void setFirstResult(Integer firstResult) {
		rowLimiter.setFirstResult(firstResult);
	}
	
	public Integer getMaxResults() {
		return rowLimiter.getMaxResults();
	}
	
	public void setMaxResults(Integer maxResults) {
		rowLimiter.setMaxResults(maxResults);
	}
	
	public Boolean getDistinct() {
		return distinct;
	}
	
	public void setDistinct(Boolean distinct) {
		this.distinct = distinct;
	}

	public List<Order> getOrders() {
		return orderBy.getOrderList();
	}
	
	public String getColumns() {
		return propertyFilter.getColumns();
	}
	
	public Class<?> getEntityType() {
		return entityType;
	}

	public EntityCriteria build() {
		
		EntityCriteria entityCriteria = new EntityCriteria();
		entityCriteria.setColumns(propertyFilter.getColumns());
		entityCriteria.setDistinct(getDistinct());
		entityCriteria.setFirstResult(rowLimiter.getFirstResult());
		entityCriteria.setMaxResults(rowLimiter.getMaxResults());
		entityCriteria.setOrders(orderBy.getOrderList());
		
		// build where sql
		StringBuilder whereSQL = new StringBuilder();
		Iterator<Criterion> iter = criterionList.iterator();
		while (iter.hasNext()) {
			Criterion criterion = iter.next();
			whereSQL.append(criterion.toSqlString(entityType));
			if (iter.hasNext()) {
				whereSQL.append(" AND ");
			}
		}
		entityCriteria.setWhereSql(whereSQL.toString());
		
		// build SimpleCriterion array
		List<SimpleCriterion> simpleCriterionList = new ArrayList<SimpleCriterion>();
		for (Criterion criterion : criterionList) {
			recurive(simpleCriterionList, criterion);
		}
		if (simpleCriterionList.size() > 0) {
			SimpleCriterion[] simpleCriterions = new SimpleCriterion[simpleCriterionList.size()];
			for (int i=0; i<simpleCriterionList.size();i++) {
				SimpleCriterion simpleCriterion = simpleCriterionList.get(i);
				simpleCriterion.setIndex(i);
				simpleCriterions[i] = simpleCriterion;
			}
			entityCriteria.setCriterions(simpleCriterions);
		} 
		
		return entityCriteria;
	}
	
	private void recurive(List<SimpleCriterion> simpleCriterions, Criterion criterion) {
		if (criterion instanceof SimpleCriterion) {
			simpleCriterions.add((SimpleCriterion)criterion);
		} else if (criterion instanceof LogicalCriterion){
			Criterion lhs = ((LogicalCriterion)criterion).getLhs();
			Criterion rhs = ((LogicalCriterion)criterion).getRhs();
			recurive(simpleCriterions, lhs);
			recurive(simpleCriterions, rhs);
		} else {
			Iterator<Criterion> iter = ((Junction) criterion).criterions().iterator();
			while (iter.hasNext()) {
				recurive(simpleCriterions, iter.next());
			}
		}
	}

}

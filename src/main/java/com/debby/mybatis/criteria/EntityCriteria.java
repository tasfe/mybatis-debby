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

import com.debby.mybatis.criteria.criterion.SimpleCriterion;
import com.debby.mybatis.criteria.sort.Order;

/**
 * @author rocky.hu
 * @date Jan 23, 2018 4:48:15 PM
 */
public class EntityCriteria {

	/** Distinct the record - just for SELECT */
	private Boolean distinct;
	/** The columns that to select - just for SELECT */
	private String columns;
	/** First result index for paging - just for SELECT */
	private Integer firstResult;
	/** Max result count for paging - just for SELECT */
	private Integer maxResults;
	/** Order by elements - just for SELECT */
	private List<Order> orders = new ArrayList<Order>();
	/** Where SQL */
	private String whereSql;
	/** Where conditions */
	private SimpleCriterion[] criterions;

	EntityCriteria() {
	}

	public static EntityCriteriaBuilder forEntity(Class<?> entityType) {
		return new EntityCriteriaBuilder(entityType);
	}

	public Boolean getDistinct() {
		return distinct;
	}

	public void setDistinct(Boolean distinct) {
		this.distinct = distinct;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}
	
	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getWhereSql() {
		return whereSql;
	}

	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	public SimpleCriterion[] getCriterions() {
		return criterions;
	}

	public void setCriterions(SimpleCriterion[] criterions) {
		this.criterions = criterions;
	}

}

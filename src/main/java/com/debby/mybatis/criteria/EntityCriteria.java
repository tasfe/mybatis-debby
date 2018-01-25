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

import java.util.List;

import com.debby.mybatis.criteria.criterion.simple.SimpleCriterion;
import com.debby.mybatis.criteria.sort.Order;

/**
 * @author rocky.hu
 * @date 2017-12-09 3:40 PM
 */
public class EntityCriteria {
	
	private Integer firstResult;
	private Integer maxResults;
	private Boolean distinct;
	private String columns;
	private SimpleCriterion[] criterions;
	private List<Order> orders;

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

	public SimpleCriterion[] getCriterions() {
		return criterions;
	}

	public void setCriterions(SimpleCriterion[] criterions) {
		this.criterions = criterions;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public static EntityCriteriaBuilder forEntity(final Class<?> entityType) {
		return new EntityCriteriaBuilder(entityType);
	}

}

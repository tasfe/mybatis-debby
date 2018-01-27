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
package com.debby.mybatis.criteria.sort;

import java.util.ArrayList;
import java.util.List;

import com.debby.mybatis.core.helper.EntityHelper;

/**
 * @author rocky.hu
 * @date Jan 22, 2018 4:59:49 PM
 */
public class OrderBy {
	
	private final Class<?> entityType;
	private List<Order> orderList = new ArrayList<Order>();
	
	public OrderBy(Class<?> entityType) {
		this.entityType = entityType;
	}

	public List<Order> getOrderList() {
		return orderList;
	}
	
	public void addOrder(Order order) {
		order.setPropertyName(EntityHelper.getColumn(entityType, order.getPropertyName()));
		orderList.add(order);
	}
	
}

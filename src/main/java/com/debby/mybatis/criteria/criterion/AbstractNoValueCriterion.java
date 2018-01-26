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
package com.debby.mybatis.criteria.criterion;

import com.debby.mybatis.sql.SqlOperator;

/**
 * @author rocky.hu
 * @date Jan 23, 2018 5:23:54 PM
 */
public abstract class AbstractNoValueCriterion extends SimpleCriterion {
	
	protected AbstractNoValueCriterion(String property, SqlOperator sqlOperator) {
		super(property, sqlOperator);
	}

	@Override
	public ValueMode getValueMode() {
		return ValueMode.NO;
	}

	@Override
	public String toSqlString(Class<?> entityType) {
		StringBuilder sb = new StringBuilder();
		sb.append(getColumn(entityType));
		sb.append(" ");
		sb.append(getSqlOperator());
		return sb.toString();
	}

	@Override
	public String toString() {
		return getProperty() + " " + getSqlOperator();
	}
	
}

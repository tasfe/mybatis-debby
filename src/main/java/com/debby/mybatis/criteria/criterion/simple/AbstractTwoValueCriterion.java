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
package com.debby.mybatis.criteria.criterion.simple;

import java.util.Collection;
import java.util.Map;

import com.debby.mybatis.criteria.criterion.simple.mode.ValueMode;
import com.debby.mybatis.exception.MyBatisDebbyException;
import com.debby.mybatis.sql.SqlLogicalOperator;
import com.debby.mybatis.sql.SqlOperator;

/**
 * @author rocky.hu
 * @date Jan 23, 2018 5:25:41 PM
 */
public abstract class AbstractTwoValueCriterion extends SimpleCriterion {
	
	protected AbstractTwoValueCriterion(String property, Object firstValue, Object secondValue, SqlOperator sqlOperator) {
		super(property, new Object[] {firstValue, secondValue}, sqlOperator);
		if (firstValue instanceof Collection || secondValue instanceof Map) {
			throw new MyBatisDebbyException("The firstValue cann't be a Collecation or Map.");
		}
		if (secondValue instanceof Collection || secondValue instanceof Map) {
			throw new MyBatisDebbyException("The secondValue cann't be a Collecation or Map.");
		}
	}
	
	@Override
	public ValueMode getValueMode() {
		return ValueMode.TWO;
	}
	
	@Override
	public String toSqlString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getColumn());
		sb.append(" ");
		sb.append(getSqlOperator().getNotation());
		sb.append(" ");
		sb.append("#{{}.value[0]}");
		sb.append(" ");
		sb.append(SqlLogicalOperator.AND);
		sb.append("#{{}.value[1]}");
		return sb.toString();
	}
	
}

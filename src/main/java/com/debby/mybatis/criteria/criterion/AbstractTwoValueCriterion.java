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

import com.debby.mybatis.sql.SqlLogicalOperator;
import com.debby.mybatis.sql.SqlOperator;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date Jan 23, 2018 5:25:41 PM
 */
public abstract class AbstractTwoValueCriterion extends SimpleCriterion {
	
	protected AbstractTwoValueCriterion(String property, Object firstValue, Object secondValue, SqlOperator sqlOperator) {
		super(property, new Object[] {firstValue, secondValue}, sqlOperator);
	}
		
	@Override
	public ValueMode getValueMode() {
		return ValueMode.TWO;
	}
	
	@Override
	public String toSqlString(Class<?> entityType) {
		
		String typeHandler = getTypeHandler(entityType);
		
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(getColumn(entityType));
		sb.append(" ");
		sb.append(getSqlOperator());
		sb.append(" ");
		sb.append(getValue(0, typeHandler));
		sb.append(" ");
		sb.append(SqlLogicalOperator.AND);
		sb.append(" ");
		sb.append(getValue(1, typeHandler));
		sb.append(")");
		return sb.toString();
	}

	private String getValue(int i, String typeHandler) {
		if (StringUtils.isNullOrEmpty(typeHandler)) {
			return "#{criterions[" + getIndex() + "].value[" + i + "]}";
		} else {
			return "#{criterions[" + getIndex() + "].value[" + i + "], typeHandler="+ typeHandler + "}";
		}
	}
	
	@Override
	public String toString() {
		return getProperty() + " " + getSqlOperator() + ((Object[]) getValue())[0] + " "+ SqlLogicalOperator.AND + " " + ((Object[]) getValue())[1];
	}
	
}

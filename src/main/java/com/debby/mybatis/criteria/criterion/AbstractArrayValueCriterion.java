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

import java.util.List;

import com.debby.mybatis.sql.SqlOperator;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date Jan 23, 2018 5:23:08 PM
 */
public abstract class AbstractArrayValueCriterion extends SimpleCriterion {
	
	protected AbstractArrayValueCriterion(String property, Object[] value, SqlOperator sqlOperator) {
		super(property, value, sqlOperator);
	}

	@Override
	public ValueMode getValueMode() {
		return ValueMode.ARRAY;
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
		sb.append("(");

		int length = ((Object[])getValue()).length;
		for (int i=0; i<length; i++) {
			if (StringUtils.isNullOrEmpty(typeHandler)) {
				sb.append("#{criterions[" + getIndex() + "].value[" + i + "]}");
			} else {
				sb.append("#{criterions[" + getIndex() + "].value[" + i + "], typeHandler=" + typeHandler + "}");
			}
			
			if (i+1 < length) {
				sb.append(",");
				sb.append(" ");
			}
		}

		sb.append(")");
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = ((List<?>)getValue()).size();
		for (int i=0; i<size; i++) {
			sb.append(((Object[])getValue())[i]);
			if (i+1 < size) {
				sb.append(",");
				sb.append(" ");
			}
		}
		
		return getProperty() + " " + getSqlOperator() + "(" + sb.toString() + ")";
	}
	
}

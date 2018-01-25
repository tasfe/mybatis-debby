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
import java.util.Iterator;

import com.debby.mybatis.criteria.criterion.simple.mode.ValueMode;
import com.debby.mybatis.sql.SqlOperator;

/**
 * @author rocky.hu
 * @date Jan 23, 2018 5:23:08 PM
 */
public abstract class AbstractListValueCriterion extends SimpleCriterion {
	
	protected AbstractListValueCriterion(String property, Collection<?> value, SqlOperator sqlOperator) {
		super(property, value, sqlOperator);
	}

	@Override
	public ValueMode getValueMode() {
		return ValueMode.LIST;
	}

	@Override
	public String toSqlString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getColumn());
		sb.append(" ");
		sb.append(getSqlOperator().getNotation());
		sb.append(" ");
		sb.append("(");
		
		Iterator<?> iter = ((Collection<?>)getValue()).iterator();
		int index = 0;
		while (iter.hasNext()) {
			sb.append("#{{}.value.get("+ index + ")}");
			
			if (iter.hasNext()) {
				sb.append(",");
			}
			
			index++;
		}
			
		sb.append(")");
		return sb.toString();
	}
	
}

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
 * @date Jan 23, 2018 5:21:37 PM
 */
public abstract class AbstractCriterion {
	
	private String property;
	private SqlOperator sqlOperator;
	private String typeHandler;
	private Object value;
	private ValueMode valueMode;
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	
	public SqlOperator getSqlOperator() {
		return sqlOperator;
	}
	
	public void setSqlOperator(SqlOperator sqlOperator) {
		this.sqlOperator = sqlOperator;
	}
	
	public String getTypeHandler() {
		return typeHandler;
	}
	
	public void setTypeHandler(String typeHandler) {
		this.typeHandler = typeHandler;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}

	public ValueMode getValueMode() {
		return valueMode;
	}

	public void setValueMode(ValueMode valueMode) {
		this.valueMode = valueMode;
	}

}

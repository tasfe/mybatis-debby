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

import com.debby.mybatis.criteria.EntityCriteria;

/**
 * @author rocky.hu
 * @date Jan 26, 2018 10:24:04 AM
 */
public class LogicalCriterion implements Criterion {
	
	private final Criterion lhs;
	private final Criterion rhs;
	private final String op;
	
	protected LogicalCriterion(Criterion lhs, Criterion rhs, String op) {
		this.lhs = lhs;
		this.rhs = rhs;
		this.op = op;
	}

	public String getOp() {
		return op;
	}
	
	public Criterion getLhs() {
		return lhs;
	}

	public Criterion getRhs() {
		return rhs;
	}

	@Override
	public String toSqlString(Class<?> entityType) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(lhs.toSqlString(entityType));
		sb.append(" ");
		sb.append(getOp());
		sb.append(" ");
		sb.append(rhs.toSqlString(entityType));
		sb.append(")");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return lhs.toString() + ' ' + getOp() + ' ' + rhs.toString();
	}

}

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
package com.debby.mybatis.sql;

/**
 * Here is a list of all the comparison operators available in SQL.
 *
 * @author rocky.hu
 * @date Sep 08, 2017, 09:12:26 PM
 * @see <a href="http://www.tutorialspoint.com/sql/sql-operators.htm">SQL
 *      Comparison Operators</a>
 */
public enum SQLComparisonOperator implements SqlOperator {

	eq("="), ne("<>"), gt(">"), lt("<"), ge(">="), le("<=");

	private String notation;

	private SQLComparisonOperator(String notation) {
		this.notation = notation;
	}

	@Override
	public String getNotation() {
		return notation;
	}

	public void setNotation(String notation) {
		this.notation = notation;
	}

	@Override
	public String toString() {
		return this.getNotation();
	}
}

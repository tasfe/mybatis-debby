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
 * Operators are used to specify conditions in an SQL statement and to serve as conjunctions for
 * multiple conditions in a statement.
 *
 * @author rocky.hu
 * @date Sep 08, 2017, 09:53:18 PM
 */
public interface SqlOperator {
	
	String getNotation();
	
}

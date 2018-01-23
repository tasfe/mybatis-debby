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

import java.util.Collection;

/**
 * @author rocky.hu
 * @date 2017-12-09 5:13 PM
 */
public class InCriterion extends Criterion {

	public InCriterion(String propertyName, Collection<Object> values) {
        super(propertyName + "&" + SqlLogicalOperator.IN, values);
    }

	public InCriterion(String propertyName, Collection<Object> values, boolean not) {
        super(propertyName + "&" + (not ? SqlLogicalOperator.NOT_IN : SqlLogicalOperator.IN), values);
    }

}

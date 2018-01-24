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
package com.debby.mybatis.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.debby.mybatis.criteria.criterion.AbstractCriterion;
import com.debby.mybatis.criteria.criterion.BetweenCriterion;
import com.debby.mybatis.criteria.criterion.ComparisonCriterion;
import com.debby.mybatis.criteria.criterion.InCriterion;
import com.debby.mybatis.criteria.criterion.LikeCriterion;
import com.debby.mybatis.criteria.criterion.NotBetweenCriterion;
import com.debby.mybatis.criteria.criterion.NotInCriterion;
import com.debby.mybatis.criteria.criterion.NotLikeCriterion;
import com.debby.mybatis.criteria.criterion.NotNullCriterion;
import com.debby.mybatis.criteria.criterion.NullCriterion;
import com.debby.mybatis.criteria.criterion.mode.MatchMode;
import com.debby.mybatis.sql.SQLComparisonOperator;
import com.debby.mybatis.util.Asserts;

/**
 * @author rocky.hu
 * @date 2017-12-09 10:47 PM
 */
public class Criteria {

	private List<AbstractCriterion> criterions = new ArrayList<AbstractCriterion>();

	public Criteria() {
	}

	public List<AbstractCriterion> getCriterions() {
		return criterions;
	}

	public boolean isValid() {
		return criterions.size() > 0;
	}

	public Criteria eq(String propertyName, Object value) {
		return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.eq));
    }

    public Criteria eqOrIsNull(String propertyName, Object value) {
        return value == null ? isNull(propertyName) : eq(propertyName, value);
    }

    public Criteria ne(String propertyName, Object value) {
    	return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.ne));
    }

    public Criteria neOrIsNotNull(String propertyName, Object value) {
        return value == null
                ? isNotNull(propertyName)
                : ne(propertyName, value);
    }

    public Criteria gt(String propertyName, Object value) {
        return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.gt));
    }

    public Criteria lt(String propertyName, Object value) {
        return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.lt));
    }

    public Criteria le(String propertyName, Object value) {
        return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.le));
    }

    public Criteria ge(String propertyName, Object value) {
        return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.ge));
    }

    public Criteria like(String propertyName, String value) {
        return addCriterion(new LikeCriterion(propertyName, String.valueOf(value), MatchMode.EXACT));
    }
    
    public Criteria like(String propertyName, String value, MatchMode matchMode) {
        return addCriterion(new LikeCriterion(propertyName, value, matchMode));
    }

    public Criteria notLike(String propertyName, String value) {
        return addCriterion(new NotLikeCriterion(propertyName, String.valueOf(value), MatchMode.EXACT));
    }

    public Criteria notLike(String propertyName, String value, MatchMode matchMode) {
        return addCriterion(new NotLikeCriterion(propertyName, value, matchMode));
    }

    public Criteria between(String propertyName, Object low, Object high) {
        return addCriterion(new BetweenCriterion(propertyName, low, high));
    }

    public Criteria notBetween(String propertyName, Object low, Object high) {
        return addCriterion(new NotBetweenCriterion(propertyName, low, high));
    }

    public Criteria in(String propertyName, Object... values) {
    	Asserts.notEmpty(values);
        return addCriterion(new InCriterion(propertyName, Arrays.asList(values)));
    }

    public Criteria in(String propertyName, Collection<Object> valueList) {
        Asserts.notEmpty(valueList);
        return addCriterion(new InCriterion(propertyName, valueList));
    }
    
    public Criteria notIn(String propertyName, Object... values) {
    	Asserts.notEmpty(values);
        return addCriterion(new NotInCriterion(propertyName, Arrays.asList(values)));
    }

    public Criteria notIn(String propertyName, Collection<Object> valueList) {
    	Asserts.notEmpty(valueList);
        return addCriterion(new NotInCriterion(propertyName, valueList));
    }

    public Criteria isNull(String propertyName) {
        return addCriterion(new NullCriterion(propertyName));
    }

    public Criteria isNotNull(String propertyName) {
        return addCriterion(new NotNullCriterion(propertyName));
    }
    
    private Criteria addCriterion(AbstractCriterion criterion) {
    	Asserts.notNull(criterion, "Criterion cannot be null.");
		criterions.add(criterion);
		return this;
	}

}

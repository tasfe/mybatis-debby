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

import com.debby.mybatis.criteria.criterion.simple.SimpleCriterion;
import com.debby.mybatis.criteria.criterion.simple.BetweenCriterion;
import com.debby.mybatis.criteria.criterion.simple.ComparisonCriterion;
import com.debby.mybatis.criteria.criterion.simple.InCriterion;
import com.debby.mybatis.criteria.criterion.simple.LikeCriterion;
import com.debby.mybatis.criteria.criterion.simple.NotBetweenCriterion;
import com.debby.mybatis.criteria.criterion.simple.NotInCriterion;
import com.debby.mybatis.criteria.criterion.simple.NotLikeCriterion;
import com.debby.mybatis.criteria.criterion.simple.NotNullCriterion;
import com.debby.mybatis.criteria.criterion.simple.NullCriterion;
import com.debby.mybatis.criteria.criterion.simple.mode.MatchMode;
import com.debby.mybatis.sql.SQLComparisonOperator;
import com.debby.mybatis.util.Asserts;

/**
 * @author rocky.hu
 * @date 2017-12-09 10:47 PM
 */
public class Criteria1 {

	private List<SimpleCriterion> criterions = new ArrayList<SimpleCriterion>();

	public Criteria1() {
	}

	public List<SimpleCriterion> getCriterions() {
		return criterions;
	}

	public boolean isValid() {
		return criterions.size() > 0;
	}

	public Criteria1 eq(String propertyName, Object value) {
		return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.eq));
    }

    public Criteria1 eqOrIsNull(String propertyName, Object value) {
        return value == null ? isNull(propertyName) : eq(propertyName, value);
    }

    public Criteria1 ne(String propertyName, Object value) {
    	return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.ne));
    }

    public Criteria1 neOrIsNotNull(String propertyName, Object value) {
        return value == null
                ? isNotNull(propertyName)
                : ne(propertyName, value);
    }

    public Criteria1 gt(String propertyName, Object value) {
        return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.gt));
    }

    public Criteria1 lt(String propertyName, Object value) {
        return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.lt));
    }

    public Criteria1 le(String propertyName, Object value) {
        return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.le));
    }

    public Criteria1 ge(String propertyName, Object value) {
        return addCriterion(new ComparisonCriterion(propertyName, value, SQLComparisonOperator.ge));
    }

    public Criteria1 like(String propertyName, String value) {
        return addCriterion(new LikeCriterion(propertyName, String.valueOf(value), MatchMode.EXACT));
    }
    
    public Criteria1 like(String propertyName, String value, MatchMode matchMode) {
        return addCriterion(new LikeCriterion(propertyName, value, matchMode));
    }

    public Criteria1 notLike(String propertyName, String value) {
        return addCriterion(new NotLikeCriterion(propertyName, String.valueOf(value), MatchMode.EXACT));
    }

    public Criteria1 notLike(String propertyName, String value, MatchMode matchMode) {
        return addCriterion(new NotLikeCriterion(propertyName, value, matchMode));
    }

    public Criteria1 between(String propertyName, Object low, Object high) {
        return addCriterion(new BetweenCriterion(propertyName, low, high));
    }

    public Criteria1 notBetween(String propertyName, Object low, Object high) {
        return addCriterion(new NotBetweenCriterion(propertyName, low, high));
    }

    public Criteria1 in(String propertyName, Object... values) {
    	Asserts.notEmpty(values);
        return addCriterion(new InCriterion(propertyName, Arrays.asList(values)));
    }

    public Criteria1 in(String propertyName, Collection<Object> valueList) {
        Asserts.notEmpty(valueList);
        return addCriterion(new InCriterion(propertyName, valueList));
    }
    
    public Criteria1 notIn(String propertyName, Object... values) {
    	Asserts.notEmpty(values);
        return addCriterion(new NotInCriterion(propertyName, Arrays.asList(values)));
    }

    public Criteria1 notIn(String propertyName, Collection<Object> valueList) {
    	Asserts.notEmpty(valueList);
        return addCriterion(new NotInCriterion(propertyName, valueList));
    }

    public Criteria1 isNull(String propertyName) {
        return addCriterion(new NullCriterion(propertyName));
    }

    public Criteria1 isNotNull(String propertyName) {
        return addCriterion(new NotNullCriterion(propertyName));
    }
    
    private Criteria1 addCriterion(SimpleCriterion criterion) {
    	Asserts.notNull(criterion, "Criterion cannot be null.");
		criterions.add(criterion);
		return this;
	}

}

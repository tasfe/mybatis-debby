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

import java.util.Arrays;
import java.util.Collection;

import com.debby.mybatis.criteria.criterion.Criterion;
import com.debby.mybatis.criteria.criterion.combined.CombinedCriterion;
import com.debby.mybatis.criteria.criterion.combined.CombinedCriterionConnector;
import com.debby.mybatis.criteria.criterion.simple.BetweenCriterion;
import com.debby.mybatis.criteria.criterion.simple.ComparisonCriterion;
import com.debby.mybatis.criteria.criterion.simple.InCriterion;
import com.debby.mybatis.criteria.criterion.simple.LikeCriterion;
import com.debby.mybatis.criteria.criterion.simple.NotBetweenCriterion;
import com.debby.mybatis.criteria.criterion.simple.NotInCriterion;
import com.debby.mybatis.criteria.criterion.simple.NotLikeCriterion;
import com.debby.mybatis.criteria.criterion.simple.NotNullCriterion;
import com.debby.mybatis.criteria.criterion.simple.NullCriterion;
import com.debby.mybatis.criteria.criterion.simple.SimpleCriterion;
import com.debby.mybatis.criteria.criterion.simple.mode.MatchMode;
import com.debby.mybatis.sql.SQLComparisonOperator;
import com.debby.mybatis.util.Asserts;

/**
 * @author rocky.hu
 * @date Aug 24, 2016, 07:52:34 PM
 */
public class Restrictions {

    public static SimpleCriterion eq(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.eq);
    }

    public static SimpleCriterion eqOrIsNull(String property, Object value) {
        return value == null
                ? isNull(property)
                : eq(property, value);
    }

    public static SimpleCriterion ne(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.ne);
    }

    public static SimpleCriterion neOrIsNotNull(String property, Object value) {
        return value == null
                ? isNotNull(property)
                : ne(property, value);
    }

    public static SimpleCriterion gt(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.gt);
    }

    public static SimpleCriterion lt(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.lt);
    }

    public static SimpleCriterion le(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.le);
    }

    public static SimpleCriterion ge(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.ge);
    }

    public static SimpleCriterion like(String property, String value) {
        return new LikeCriterion(property, String.valueOf(value), MatchMode.EXACT);
    }
    
    public static SimpleCriterion like(String property, String value, MatchMode matchMode) {
        return new LikeCriterion(property, value, matchMode);
    }

    public static SimpleCriterion notLike(String property, String value) {
        return new NotLikeCriterion(property, String.valueOf(value), MatchMode.EXACT);
    }

    public static SimpleCriterion notLike(String property, String value, MatchMode matchMode) {
        return new NotLikeCriterion(property, value, matchMode);
    }

    public static SimpleCriterion between(String property, Object low, Object high) {
        return new BetweenCriterion(property, low, high);
    }

    public static SimpleCriterion notBetween(String property, Object low, Object high) {
        return new NotBetweenCriterion(property, low, high);
    }

    public static SimpleCriterion in(String property, Object... values) {
    	Asserts.notEmpty(values);
        return new InCriterion(property, Arrays.asList(values));
    }
    
    public static SimpleCriterion in(String property, Collection<Object> valueList) {
    	Asserts.notEmpty(valueList);
        return new InCriterion(property, valueList);
    }
    
    public static SimpleCriterion notIn(String property, Object... values) {
    	Asserts.notEmpty(values);
        return new NotInCriterion(property, Arrays.asList(values));
    }

    public static SimpleCriterion notIn(String property, Collection<Object> valueList) {
    	Asserts.notEmpty(valueList);
        return new NotInCriterion(property, valueList);
    }

    public static SimpleCriterion isNull(String property) {
        return new NullCriterion(property);
    }

    public static SimpleCriterion isNotNull(String property) {
        return new NotNullCriterion(property);
    }
    
    public static CombinedCriterion and(Criterion... criterions) {
    	return new CombinedCriterion(CombinedCriterionConnector.AND, criterions);
    }
    
    public static CombinedCriterion or(Criterion... criterions) {
    	return new CombinedCriterion(CombinedCriterionConnector.OR, criterions);
    }

}
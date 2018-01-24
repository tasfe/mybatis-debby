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
 * @date Aug 24, 2016, 07:52:34 PM
 */
public class Restrictions {

    public static AbstractCriterion eq(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.eq);
    }

    public static AbstractCriterion eqOrIsNull(String property, Object value) {
        return value == null
                ? isNull(property)
                : eq(property, value);
    }

    public static AbstractCriterion ne(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.ne);
    }

    public static AbstractCriterion neOrIsNotNull(String property, Object value) {
        return value == null
                ? isNotNull(property)
                : ne(property, value);
    }

    public static AbstractCriterion gt(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.gt);
    }

    public static AbstractCriterion lt(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.lt);
    }

    public static AbstractCriterion le(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.le);
    }

    public static AbstractCriterion ge(String property, Object value) {
        return new ComparisonCriterion(property, value, SQLComparisonOperator.ge);
    }

    public static AbstractCriterion like(String property, String value) {
        return new LikeCriterion(property, String.valueOf(value), MatchMode.EXACT);
    }
    
    public static AbstractCriterion like(String property, String value, MatchMode matchMode) {
        return new LikeCriterion(property, value, matchMode);
    }

    public static AbstractCriterion notLike(String property, String value) {
        return new NotLikeCriterion(property, String.valueOf(value), MatchMode.EXACT);
    }

    public static AbstractCriterion notLike(String property, String value, MatchMode matchMode) {
        return new NotLikeCriterion(property, value, matchMode);
    }

    public static AbstractCriterion between(String property, Object low, Object high) {
        return new BetweenCriterion(property, low, high);
    }

    public static AbstractCriterion notBetween(String property, Object low, Object high) {
        return new NotBetweenCriterion(property, low, high);
    }

    public static AbstractCriterion in(String property, Object... values) {
    	Asserts.notEmpty(values);
        return new InCriterion(property, Arrays.asList(values));
    }
    
    public static AbstractCriterion in(String property, Collection<Object> valueList) {
    	Asserts.notEmpty(valueList);
        return new InCriterion(property, valueList);
    }
    
    public static AbstractCriterion notIn(String property, Object... values) {
    	Asserts.notEmpty(values);
        return new NotInCriterion(property, Arrays.asList(values));
    }

    public static AbstractCriterion notIn(String property, Collection<Object> valueList) {
    	Asserts.notEmpty(valueList);
        return new NotInCriterion(property, valueList);
    }

    public static AbstractCriterion isNull(String property) {
        return new NullCriterion(property);
    }

    public static AbstractCriterion isNotNull(String property) {
        return new NotNullCriterion(property);
    }

}
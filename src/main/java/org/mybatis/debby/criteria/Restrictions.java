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
package org.mybatis.debby.criteria;

import org.mybatis.debby.criteria.MatchMode;
import org.mybatis.debby.sql.SQLComparisonOperator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author rocky.hu
 * @date Aug 24, 2016, 07:52:34 PM
 */
public class Restrictions {

    public static Criterion eq(String propertyName, Object value) {
        return new ComparisonCriterion(propertyName, value, SQLComparisonOperator.eq);
    }

    public static Criterion eqOrIsNull(String propertyName, Object value) {
        return value == null
                ? isNull(propertyName)
                : eq(propertyName, value);
    }

    public static Criterion ne(String propertyName, Object value) {
        return new ComparisonCriterion(propertyName, value, SQLComparisonOperator.ne);
    }

    public static Criterion neOrIsNotNull(String propertyName, Object value) {
        return value == null
                ? isNotNull(propertyName)
                : ne(propertyName, value);
    }

    public static Criterion gt(String propertyName, Object value) {
        return new ComparisonCriterion(propertyName, value, SQLComparisonOperator.gt);
    }

    public static Criterion lt(String propertyName, Object value) {
        return new ComparisonCriterion(propertyName, value, SQLComparisonOperator.lt);
    }

    public static Criterion le(String propertyName, Object value) {
        return new ComparisonCriterion(propertyName, value, SQLComparisonOperator.le);
    }

    public static Criterion ge(String propertyName, Object value) {
        return new ComparisonCriterion(propertyName, value, SQLComparisonOperator.ge);
    }

    public static Criterion like(String propertyName, String value) {
        return new LikeCriterion(propertyName, String.valueOf(value), MatchMode.EXACT);
    }

    public static Criterion notLike(String propertyName, String value) {
        return new LikeCriterion(propertyName, String.valueOf(value), MatchMode.EXACT, true);
    }

    public static Criterion like(String propertyName, String value, MatchMode matchMode) {
        return new LikeCriterion(propertyName, value, matchMode);
    }

    public static Criterion notLike(String propertyName, String value, MatchMode matchMode) {
        return new LikeCriterion(propertyName, value, matchMode, true);
    }

    public static Criterion between(String propertyName, Object low, Object high) {
        return new BetweenCriterion(propertyName, low, high);
    }

    public static Criterion notBetween(String propertyName, Object low, Object high) {
        return new BetweenCriterion(propertyName, low, high, true);
    }

    public static Criterion in(String propertyName, Object... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Values passed to in cannot be null or empty.");
        }
        List<Object> valueList = new ArrayList<Object>(values.length);
        for (Object value : values) {
            valueList.add(value);
        }
        return new InCriterion(propertyName, valueList);
    }

    public static Criterion in(String propertyName, Collection<Object> valueList) {
        if (valueList == null || valueList.size() == 0) {
            throw new IllegalArgumentException("Values passed to in cannot be null or empty.");
        }
        return new InCriterion(propertyName, valueList);
    }

    public static Criterion isNull(String propertyName) {
        return new NullCriterion(propertyName);
    }

    public static Criterion isNotNull(String propertyName) {
        return new NotNullCriterion(propertyName);
    }

}
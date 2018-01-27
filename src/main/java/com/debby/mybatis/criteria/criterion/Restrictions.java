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
package com.debby.mybatis.criteria.criterion;

import java.util.Collection;

import com.debby.mybatis.sql.SQLComparisonOperator;
import com.debby.mybatis.sql.SqlLogicalOperator;
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
        return new InCriterion(property, values);
    }
    
    public static SimpleCriterion in(String property, Collection values) {
    	Asserts.notEmpty(values);
        return new InCriterion(property, values.toArray());
    }
    
    public static SimpleCriterion notIn(String property, Object... values) {
    	Asserts.notEmpty(values);
        return new NotInCriterion(property, values);
    }

    public static SimpleCriterion notIn(String property, Collection values) {
    	Asserts.notEmpty(values);
        return new NotInCriterion(property, values.toArray());
    }

    public static SimpleCriterion isNull(String property) {
        return new NullCriterion(property);
    }

    public static SimpleCriterion isNotNull(String property) {
        return new NotNullCriterion(property);
    }
    
    public static LogicalCriterion and(Criterion lhs, Criterion rhs) {
    	return new LogicalCriterion(lhs, rhs, SqlLogicalOperator.AND.getNotation());
    }
    
    public static Conjunction and(Criterion... predicates) {
		return conjunction( predicates );
	}
    
    public static LogicalCriterion or(Criterion lhs, Criterion rhs) {
    	return new LogicalCriterion(lhs, rhs, SqlLogicalOperator.OR.getNotation());
    }
    
    public static Disjunction or(Criterion... predicates) {
		return disjunction( predicates );
	}
    
    public static Conjunction conjunction() {
		return new Conjunction();
	}
    
    public static Conjunction conjunction(Criterion... conditions) {
		return new Conjunction( conditions );
	}
    
    public static Disjunction disjunction() {
		return new Disjunction();
	}
    
    public static Disjunction disjunction(Criterion... conditions) {
		return new Disjunction( conditions );
	}
    
    protected Restrictions() {
	}

}
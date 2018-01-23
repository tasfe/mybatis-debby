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
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;

import com.debby.mybatis.core.ResultMapRegistry;
import com.debby.mybatis.criteria.criterion.BetweenCriterion;
import com.debby.mybatis.criteria.criterion.ComparisonCriterion;
import com.debby.mybatis.criteria.criterion.Criterion;
import com.debby.mybatis.criteria.criterion.InCriterion;
import com.debby.mybatis.criteria.criterion.LikeCriterion;
import com.debby.mybatis.criteria.criterion.NotNullCriterion;
import com.debby.mybatis.criteria.criterion.NullCriterion;
import com.debby.mybatis.criteria.criterion.like.MatchMode;
import com.debby.mybatis.sql.SQLComparisonOperator;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date 2017-12-09 10:47 PM
 */
public class Criteria {

	private final Class<?> entityType;
	private List<Criterion> criterions = new ArrayList<Criterion>();

	public Criteria(Class<?> entityType) {
		this.entityType = entityType;
	}

	public List<Criterion> getCriterions() {
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

    public Criteria notLike(String propertyName, String value) {
        return addCriterion(new LikeCriterion(propertyName, String.valueOf(value), MatchMode.EXACT, true));
    }

    public Criteria like(String propertyName, String value, MatchMode matchMode) {
        return addCriterion(new LikeCriterion(propertyName, value, matchMode));
    }

    public Criteria notLike(String propertyName, String value, MatchMode matchMode) {
        return addCriterion(new LikeCriterion(propertyName, value, matchMode, true));
    }

    public Criteria between(String propertyName, Object low, Object high) {
        return addCriterion(new BetweenCriterion(propertyName, low, high));
    }

    public Criteria notBetween(String propertyName, Object low, Object high) {
        return addCriterion(new BetweenCriterion(propertyName, low, high, true));
    }

    public Criteria in(String propertyName, Object... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Values passed to in cannot be null or empty.");
        }
        List<Object> valueList = new ArrayList<Object>(values.length);
        for (Object value : values) {
            valueList.add(value);
        }
        return addCriterion(new InCriterion(propertyName, valueList));
    }

    public Criteria in(String propertyName, Collection<Object> valueList) {
        if (valueList == null || valueList.size() == 0) {
            throw new IllegalArgumentException("Values passed to in cannot be null or empty.");
        }
        return addCriterion(new InCriterion(propertyName, valueList));
    }

    public Criteria isNull(String propertyName) {
        return addCriterion(new NullCriterion(propertyName));
    }

    public Criteria isNotNull(String propertyName) {
        return addCriterion(new NotNullCriterion(propertyName));
    }
    
    private Criteria addCriterion(Criterion criterion) {
		if (criterion == null) {
			throw new IllegalArgumentException("Criterion cannot be null.");
		}

		// convert the property name to column name
		String condition = criterion.getCondition();
		if (StringUtils.isNullOrEmpty(condition)) {
			throw new IllegalArgumentException("condition is required for " + criterion.getClass().getName());
		}
		String[] segment = condition.split("&");
		if (segment == null || segment.length != 2) {
			throw new IllegalArgumentException("Illegal format for " + criterion.getClass().getName());
		}
		if (StringUtils.isNullOrEmpty(segment[0])) {
			throw new IllegalArgumentException("Property name is required for " + criterion.getClass().getName());
		}
		if (StringUtils.isNullOrEmpty(segment[1])) {
			throw new IllegalArgumentException("Sql operator is required for " + condition.getClass().getName());
		}

		String propertyName = segment[0];
		String sqlOperator = segment[1];

		ResultMapping resultMapping = ResultMapRegistry.getResultMapping(entityType.getName(), propertyName);
		String column = resultMapping.getColumn();
		String typeHandler = resultMapping.getTypeHandler().getClass().getName();

		StringBuilder sb = new StringBuilder();
		sb.append(column);
		sb.append(" ");
		sb.append(sqlOperator);
		criterion.setCondition(sb.toString());

		criterion.setTypeHandler(typeHandler);

		criterions.add(criterion);

		return this;
	}

}

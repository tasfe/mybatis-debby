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

import java.util.List;

import org.mybatis.debby.sql.SQLComparisonOperators;
import org.mybatis.debby.sql.SqlLogicalOperators;

/**
 * @author rocky.hu
 * @date Aug 24, 2016, 07:52:34 PM
 */
public class Restrictions {
    
    /**
     * Apply an "equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return
     */
    public static Criterion eq(String propertyName, Object value)
    {
        if (value == null) {
            throw new IllegalArgumentException("Value for " + propertyName + " cannot be null");
        }
        return new Criterion(propertyName, SQLComparisonOperators.eq, value, null, null);
    }

    /**
     * Apply an "equal" constraint to the named property.  If the value
     * is null, instead apply "is null".
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @return The Criterion
     * @see #eq
     * @see #isNull
     */
    public static Criterion eqOrIsNull(String propertyName, Object value)
    {
        return value == null
                ? isNull(propertyName)
                : eq(propertyName, value);
    }

    /**
     * Apply a "not equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return
     */
    public static Criterion ne(String propertyName, Object value)
    {
        if (value == null) {
            throw new IllegalArgumentException("Value for " + propertyName + " cannot be null");
        }
        return new Criterion(propertyName, SQLComparisonOperators.ne, value, null, null);
    }

    /**
     * Apply a "not equal" constraint to the named property.  If the value
     * is null, instead apply "is not null".
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @return The Criterion
     * @see #ne
     * @see #isNotNull
     */
    public static Criterion neOrIsNotNull(String propertyName, Object value)
    {
        return value == null
                ? isNotNull(propertyName)
                : ne(propertyName, value);
    }

    /**
     * Apply a "like" constraint to the named property
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @return The Criterion
     */
    public static Criterion like(String propertyName, Object value)
    {
        if (value == null) {
            throw new IllegalArgumentException("Value for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SqlLogicalOperators.LIKE, value, null, null);
    }

    /**
     * Apply a "like" constraint to the named property using the provided match mode
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @param matchMode    The match mode to use in comparison
     * @return The Criterion
     */
    public static Criterion like(String propertyName, String value, MatchMode matchMode)
    {
        if (value == null) {
            throw new IllegalArgumentException("Value for " + propertyName + " cannot be null");
        }

        if (matchMode == null) {
            throw new IllegalArgumentException("MatchMode for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SqlLogicalOperators.LIKE, value, null, null, matchMode);
    }

    /**
     * Apply a "greater than" constraint to the named property
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @return The Criterion
     */
    public static Criterion gt(String propertyName, Object value)
    {
        if (value == null) {
            throw new IllegalArgumentException("Value for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SQLComparisonOperators.gt, value, null, null);
    }

    /**
     * Apply a "less than" constraint to the named property
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @return The Criterion
     */
    public static Criterion lt(String propertyName, Object value)
    {
        if (value == null) {
            throw new IllegalArgumentException("Value for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SQLComparisonOperators.lt, value, null, null);
    }

    /**
     * Apply a "less than or equal" constraint to the named property
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @return The Criterion
     */
    public static Criterion le(String propertyName, Object value)
    {
        if (value == null) {
            throw new IllegalArgumentException("Value for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SQLComparisonOperators.le, value, null, null);
    }

    /**
     * Apply a "greater than or equal" constraint to the named property
     *
     * @param propertyName The name of the property
     * @param value        The value to use in comparison
     * @return The Criterion
     */
    public static Criterion ge(String propertyName, Object value)
    {
        if (value == null) {
            throw new IllegalArgumentException("Value for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SQLComparisonOperators.ge, value, null, null);
    }

    /**
     * Apply a "between" constraint to the named property
     *
     * @param propertyName The name of the property
     * @param lo           The low value
     * @param hi           The high value
     * @return The Criterion
     */
    public static Criterion between(String propertyName, Object lo, Object hi)
    {
        if (lo == null || hi == null) {
            throw new IllegalArgumentException("Values for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SqlLogicalOperators.BETWEEN, lo, hi, null);
    }

    /**
     * Apply a "not between" constraint to the named property
     *
     * @param propertyName The name of the property
     * @param lo           The low value
     * @param hi           The high value
     * @return The Criterion
     */
    public static Criterion notBetween(String propertyName, Object lo, Object hi)
    {
        if (lo == null || hi == null) {
            throw new IllegalArgumentException("Values for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SqlLogicalOperators.NOT_BETWEEN, lo, hi, null);
    }

    /**
     * Apply an "in" constraint to the named property.
     *
     * @param propertyName The name of the property
     * @param values       The literal values to use in the IN restriction
     * @return The Criterion
     */
    public static Criterion in(String propertyName, List<?> values)
    {
        if (values == null || values.size() == 0) {
            throw new IllegalArgumentException("Values for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SqlLogicalOperators.IN, null, null, values);
    }

    /**
     * Apply an "not in" constraint to the named property.
     *
     * @param propertyName The name of the property
     * @param values       The literal values to use in the IN restriction
     * @return The Criterion
     */
    public static Criterion notIn(String propertyName, List<?> values)
    {
        if (values == null || values.size() == 0) {
            throw new IllegalArgumentException("Values for " + propertyName + " cannot be null");
        }

        return new Criterion(propertyName, SqlLogicalOperators.NOT_IN, null, null, values);
    }

    /**
     * Apply an "is not null" constraint to the named property
     *
     * @param propertyName The property name
     * @return The Criterion
     */
    public static Criterion isNotNull(String propertyName)
    {
        return new Criterion(propertyName, SqlLogicalOperators.IS_NOT_NULL, null, null, null);
    }

    /**
     * Apply an "is null" constraint to the named property.
     *
     * @param property
     * @return
     */
    public static Criterion isNull(String property)
    {
        return new Criterion(property, SqlLogicalOperators.IS_NULL, null, null, null);
    }

}

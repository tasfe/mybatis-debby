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

import org.mybatis.debby.helper.EntityHelper;
import org.mybatis.debby.sql.SqlOperators;

/**
 * @author rocky.hu
 * @date Aug 6, 2016 10:07:28 PM
 */
public class Criterion {
    
    private SqlOperators sqlOperators;
    private String propertyName;
    private Object value;
    private Object secondValue;
    private List<?> listValue;
    private MatchMode matchMode;// only for SqlLogicalOperators.LIKE

    /**
     * @param propertyName
     * @param sqlOperators
     * @param value
     * @param secondValue
     * @param listValue
     */
    public Criterion(String propertyName, SqlOperators sqlOperators, Object value, Object secondValue, List<?>
            listValue)
    {
        if (propertyName == null || "".equals(propertyName.trim())) {
            throw new IllegalArgumentException("PropertyName for Criterion cannot be null");
        }

        if (sqlOperators == null) {
            throw new IllegalArgumentException("Sql Operators for Criterion cannot be null");
        }

        this.propertyName = EntityHelper.camelToUnderscore(propertyName);
        this.sqlOperators = sqlOperators;
        this.value = value;
        this.secondValue = secondValue;
        this.listValue = listValue;
    }

    /**
     * This constructor is only for SqlLogicalOperators.LIKE
     *
     * @param propertyName
     * @param sqlOperators
     * @param value
     * @param secondValue
     * @param listValue
     * @param matchMode
     */
    public Criterion(String propertyName, SqlOperators sqlOperators, Object value, Object secondValue, List<?>
            listValue, MatchMode matchMode)
    {
        if (propertyName == null || "".equals(propertyName.trim())) {
            throw new IllegalArgumentException("PropertyName for Criterion cannot be null");
        }

        if (sqlOperators == null) {
            throw new IllegalArgumentException("Sql Operators for Criterion cannot be null");
        }

        if (value == null) {
            throw new IllegalArgumentException("Value for Criterion cannot be null");
        }

        if (matchMode == null) {
            throw new IllegalArgumentException("MatchMode for Criterion cannot be null");
        }

        this.propertyName = EntityHelper.camelToUnderscore(propertyName);
        this.sqlOperators = sqlOperators;
        this.value = value;
        this.secondValue = null;
        this.listValue = null;
        this.matchMode = matchMode;
    }

    public SqlOperators getSqlOperators()
    {
        return sqlOperators;
    }

    public List<?> getListValue()
    {
        return listValue;
    }

    public void setSqlOperators(SqlOperators sqlOperators)
    {
        this.sqlOperators = sqlOperators;
    }

    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public void setSecondValue(Object secondValue)
    {
        this.secondValue = secondValue;
    }

    public void setListValue(List<?> listValue)
    {
        this.listValue = listValue;
    }

    public void setMatchMode(MatchMode matchMode)
    {
        this.matchMode = matchMode;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public Object getValue()
    {
        return value;
    }

    public Object getSecondValue()
    {
        return secondValue;
    }

    public MatchMode getMatchMode()
    {
        return matchMode;
    }

    /**
     * If we don't need to map the camel case to underscore for the propertyName, we should call this method.
     */
    public Criterion unMapCamelCaseToUnderscore()
    {
        this.setPropertyName(EntityHelper.underscoreToCamel(this.propertyName));
        return this;
    }

}

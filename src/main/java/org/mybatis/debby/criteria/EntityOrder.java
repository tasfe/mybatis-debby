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

import java.io.Serializable;

import org.mybatis.debby.helper.EntityHelper;

/**
 * Represents an ordering imposed upon the results of a EntityCriteria.
 *
 * @author rocky.hu
 * @date Aug 6, 2016 10:27:27 PM
 */
public class EntityOrder implements Serializable {
    
    private static final long serialVersionUID = 2052922741217525175L;

    private boolean ascending;
    private String propertyName;

    /**
     * Ascending order
     *
     * @param propertyName The property to order on
     * @return The build EntityOrder instance
     */
    public static EntityOrder asc(String propertyName)
    {
        return new EntityOrder(EntityHelper.camelToUnderscore(propertyName), true);
    }

    /**
     * Descending order.
     *
     * @param propertyName The property to order on
     * @return The build EntityOrder instance
     */
    public static EntityOrder desc(String propertyName)
    {
        return new EntityOrder(EntityHelper.camelToUnderscore(propertyName), false);
    }

    /**
     * Constructor for EntityOrder.  EntityOrder instances are generally created by factory methods.
     *
     * @see #asc
     * @see #desc
     */
    protected EntityOrder(String propertyName, boolean ascending)
    {
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isAscending() {
        return ascending;
    }

    public EntityOrder unMapCamelCaseToUnderscore()
    {
        this.propertyName = EntityHelper.underscoreToCamel(this.propertyName);
        return this;
    }

    @Override
    public String toString()
    {
        return propertyName + ' ' + (ascending ? "asc" : "desc");
    }

}

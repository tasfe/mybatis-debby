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

import com.google.common.base.Strings;
import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.debby.core.XResultMapRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rocky.hu
 * @date 2017-12-09 10:47 PM
 */
public class Criteria {

    private final String entityOrClassName;
    private List<Criterion> criterions = new ArrayList<Criterion>();

    Criteria(String entityOrClassName) {
        this.entityOrClassName = entityOrClassName;
    }

    public Criteria addCriterion(Criterion criterion) {
        if (criterion == null) {
            throw new IllegalArgumentException("Criterion cannot be null.");
        }

        // convert the property name to column name
        String condition = criterion.getCondition();
        if (Strings.isNullOrEmpty(condition)) {
            throw new IllegalArgumentException("condition is required for " + criterion.getClass().getName());
        }
        String[] segment = condition.split("&");
        if (segment == null || segment.length != 2) {
            throw new IllegalArgumentException("Illegal format for " + criterion.getClass().getName());
        }
        if (Strings.isNullOrEmpty(segment[0])) {
            throw new IllegalArgumentException("Property name is required for " + criterion.getClass().getName());
        }
        if (Strings.isNullOrEmpty(segment[1])) {
            throw new IllegalArgumentException("Sql operator is required for " + condition.getClass().getName());
        }

        String propertyName = segment[0];
        String sqlOperator = segment[1];

        ResultMapping resultMapping = XResultMapRegistry.getResultMapping(entityOrClassName, propertyName);
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

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public boolean isValid() {
        return criterions.size() > 0;
    }

}

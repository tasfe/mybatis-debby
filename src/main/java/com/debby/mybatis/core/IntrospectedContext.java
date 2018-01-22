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
package com.debby.mybatis.core;

import com.debby.mybatis.annotation.MappingTable;
import com.debby.mybatis.util.StringUtils;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import com.debby.mybatis.MyBatisDebbyConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rocky.hu
 * @date Nov 17, 2017 11:48:16 AM
 */
public class IntrospectedContext {

    private Class<?> entityType;
    private ResultMap resultMap;
    private Configuration configuration;
    private MyBatisDebbyConfiguration myBatisDebbyConfiguration;
    private List<InternalStatements> alreadyOwnedInternalStatements;

    public IntrospectedContext(Configuration configuration, MyBatisDebbyConfiguration myBatisDebbyConfiguration) {
        this.configuration = configuration;
        this.myBatisDebbyConfiguration = myBatisDebbyConfiguration;
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public void setEntityType(Class<?> entityType) {
        this.entityType = entityType;
    }

    /**
     * Parse the table name if the entity class is annotated with MappingTable annotation.
     * @return
     */
    public String getTableName() {
        String tableName = "";
        String tablePrefix = "";
        Class<?> type = resultMap.getType();
        MappingTable mappingTable = type.getAnnotation(MappingTable.class);
        if (mappingTable != null) {
            tableName = mappingTable.name();
        } else {
            if (!StringUtils.isNullOrEmpty(myBatisDebbyConfiguration.getTablePrefix())) {
                tablePrefix = myBatisDebbyConfiguration.getTablePrefix();
            }
            tableName = tablePrefix + StringUtils.camelToUnderscore(type.getSimpleName(), false);
        }

        return tableName;
    }

    public ResultMap getResultMap() {
        return resultMap;
    }

    public void setResultMap(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public MyBatisDebbyConfiguration getDebbyConfiguration() {
        return myBatisDebbyConfiguration;
    }

    public void setDebbyConfiguration(MyBatisDebbyConfiguration myBatisDebbyConfiguration) {
        this.myBatisDebbyConfiguration = myBatisDebbyConfiguration;
    }

    public List<InternalStatements> getAlreadyOwnedInternalStatements() {
        if (alreadyOwnedInternalStatements == null) {
            return new ArrayList<InternalStatements>();
        }
        return alreadyOwnedInternalStatements;
    }

    public void setAlreadyOwnedInternalStatements(List<InternalStatements> alreadyOwnedInternalStatements) {
        this.alreadyOwnedInternalStatements = alreadyOwnedInternalStatements;
    }
}

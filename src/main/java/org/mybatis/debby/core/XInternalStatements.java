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
package org.mybatis.debby.core;

import org.apache.ibatis.mapping.SqlCommandType;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 11:12:03 AM
 */
public enum XInternalStatements {

    INSERT(SqlCommandType.INSERT, "insert"),
    INSERT_SELECTIVE(SqlCommandType.INSERT, "insertSelective"),
    UPDATE_BY_PRIMARY_KEY(SqlCommandType.UPDATE, "updateByPrimaryKey"),
    UPDATE_BY_PRIMARY_KEY_SELECTIVE(SqlCommandType.UPDATE, "updateByPrimaryKeySelective"),
    UPDATE_BY_CRITERIA(SqlCommandType.UPDATE, "updateByCriteria"),
    UPDATE_BY_CRITERIA_SELECTIVE(SqlCommandType.UPDATE, "updateByCriteriaSelective"),
    SELECT_BY_PRIMARY_KEY(SqlCommandType.SELECT, "selectByPrimaryKey"),
    SELECT_BY_CRITERIA(SqlCommandType.SELECT, "selectByCriteria"),
    SELECT_COUNT_BY_CRITERIA(SqlCommandType.SELECT, "selectCountByCriteria"),
    DELETE_BY_PRIMARY_KEY(SqlCommandType.DELETE, "deleteByPrimaryKey"),
    DELETE_BY_CRITERIA(SqlCommandType.DELETE, "deleteByCriteria");
    
    private String id;
    private SqlCommandType sqlCommandType;

    private XInternalStatements(SqlCommandType sqlCommandType, String id) {
        this.sqlCommandType = sqlCommandType;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

}

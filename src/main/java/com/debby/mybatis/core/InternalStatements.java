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

import org.apache.ibatis.mapping.SqlCommandType;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 11:12:03 AM
 */
public enum InternalStatements {

    INSERT(SqlCommandType.INSERT, "insert"),
    INSERT_SELECTIVE(SqlCommandType.INSERT, "insertSelective"),
    UPDATE_BY_ID(SqlCommandType.UPDATE, "updateById"),
    UPDATE_BY_ID_SELECTIVE(SqlCommandType.UPDATE, "updateByIdSelective"),
    UPDATE(SqlCommandType.UPDATE, "update"),
    UPDATE_SELECTIVE(SqlCommandType.UPDATE, "updateSelective"),
    SELECT_BY_ID(SqlCommandType.SELECT, "selectById"),
    SELECT_ONE(SqlCommandType.SELECT, "selectOne"),
    SELECT_LIST(SqlCommandType.SELECT, "selectList"),
    SELECT_PAGE(SqlCommandType.SELECT, "selectPage"),
    SELECT_COUNT(SqlCommandType.SELECT, "selectCount"),
    DELETE_BY_ID(SqlCommandType.DELETE, "deleteById"),
    DELETE(SqlCommandType.DELETE, "delete"),

    INTERNAL_SELECT_LIST_FOR_PAGING(SqlCommandType.SELECT, "_selectListForPaging");
    
    private String id;
    private SqlCommandType sqlCommandType;

    private InternalStatements(SqlCommandType sqlCommandType, String id) {
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

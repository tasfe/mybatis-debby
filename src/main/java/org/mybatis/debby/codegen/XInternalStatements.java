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
package org.mybatis.debby.codegen;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 11:12:03 AM
 */
public enum XInternalStatements {

    INSERT("insert"),
    UPDATE_BY_PRIMARY_KEY("updateByPrimaryKey"),
    UPDATE_BY_CRITERIA("updateByCriteria"),
    SELECT_BY_PRIMARY_KEY("selectByPrimaryKey"),
    SELECT_BY_CRITERIA("selectByCriteria"),
    SELECT_COUNT_BY_CRITERIA("selectCountByCriteria"),
    DELETE_BY_PRIMARY_KEY("deleteByPrimaryKey"),
    DELETE_BY_CRITERIA("deleteByCriteria");
    
    private String id;

    private XInternalStatements(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}

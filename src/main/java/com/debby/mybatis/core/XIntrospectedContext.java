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

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.XConfiguration;
import com.debby.mybatis.DebbyConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rocky.hu
 * @date Nov 17, 2017 11:48:16 AM
 */
public class XIntrospectedContext {

    private String tableName;
    private ResultMap resultMap;
    private XConfiguration xConfiguration;
    private DebbyConfiguration debbyConfiguration;
    private List<XInternalStatements> alreadyOwnedInternalStatements;

    public XIntrospectedContext(XConfiguration xConfiguration, DebbyConfiguration debbyConfiguration) {
        this.xConfiguration = xConfiguration;
        this.debbyConfiguration = debbyConfiguration;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ResultMap getResultMap() {
        return resultMap;
    }

    public void setResultMap(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    public XConfiguration getxConfiguration() {
        return xConfiguration;
    }

    public void setxConfiguration(XConfiguration xConfiguration) {
        this.xConfiguration = xConfiguration;
    }

    public DebbyConfiguration getDebbyConfiguration() {
        return debbyConfiguration;
    }

    public void setDebbyConfiguration(DebbyConfiguration debbyConfiguration) {
        this.debbyConfiguration = debbyConfiguration;
    }

    public List<XInternalStatements> getAlreadyOwnedInternalStatements() {
        if (alreadyOwnedInternalStatements == null) {
            return new ArrayList<XInternalStatements>();
        }
        return alreadyOwnedInternalStatements;
    }

    public void setAlreadyOwnedInternalStatements(List<XInternalStatements> alreadyOwnedInternalStatements) {
        this.alreadyOwnedInternalStatements = alreadyOwnedInternalStatements;
    }
}

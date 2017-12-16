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
package com.debby.mybatis.core.keystrategy.normal;

import com.debby.mybatis.core.keystrategy.XKeyStrategy;

/**
 * @author rocky.hu
 * @date 2017-11-26 2:54 PM
 */
public class XNormalKeyStrategy implements XKeyStrategy{

    private boolean before = false;
    private String runtimeSqlStatement;

    public boolean isBefore() {
        return before;
    }

    public void setBefore(boolean before) {
        this.before = before;
    }

    public String getRuntimeSqlStatement() {
        return runtimeSqlStatement;
    }

    public void setRuntimeSqlStatement(String runtimeSqlStatement) {
        this.runtimeSqlStatement = runtimeSqlStatement;
    }

}

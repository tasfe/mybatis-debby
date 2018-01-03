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
package com.debby.mybatis.core.dialect.identity;

import com.debby.mybatis.exception.MappingException;

/**
 * @author rocky.hu
 * @date 2017-12-16 8:14 PM
 */
public class MySQLIdentityColumnStrategy extends IdentityColumnStrategyImpl {

    @Override
    public String getIdentitySelectString() throws MappingException {
        return "select last_insert_id()";
    }
}

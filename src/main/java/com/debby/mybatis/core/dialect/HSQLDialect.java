/**
 * Copyright 2016-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.debby.mybatis.core.dialect;

import com.debby.mybatis.core.dialect.identity.HSQLIdentityColumnStrategy;
import com.debby.mybatis.core.dialect.identity.IdentityColumnStrategy;
import com.debby.mybatis.exception.DialectSupportException;

/**
 * @author rocky.hu
 * @date 2017-12-16 9:04 PM
 */
public class HSQLDialect extends Dialect {

    @Override
    public IdentityColumnStrategy getIdentityColumnStrategy() {
        return new HSQLIdentityColumnStrategy();
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    public String getSequenceNextValString(String sequenceName) throws DialectSupportException {
        return "call next value for " + sequenceName;
    }

}

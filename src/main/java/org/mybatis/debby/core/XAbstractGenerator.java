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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rocky.hu
 * @date Nov 17, 2017 11:53:21 AM
 */
public abstract class XAbstractGenerator {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected XIntrospectedContext introspectedContext;
    
    public XAbstractGenerator() {
        super();
    }

    public XIntrospectedContext getIntrospectedContext() {
        return introspectedContext;
    }

    public void setIntrospectedContext(XIntrospectedContext introspectedContext) {
        this.introspectedContext = introspectedContext;
    }

}

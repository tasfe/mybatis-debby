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
package org.mybatis.debby.codegen.xmlmapper.elements;

import org.mybatis.debby.codegen.XAbstractGenerator;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author rocky.hu
 * @date Nov 17, 2017 2:20:48 PM
 */
public abstract class XAbstractXmlElementGenerator extends XAbstractGenerator {
    
    public abstract void addElements(XmlElement parentElement);

    public XAbstractXmlElementGenerator() {
        super();
    }

}

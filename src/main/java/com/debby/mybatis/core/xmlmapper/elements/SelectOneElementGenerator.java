/**
 *    Copyright 2017-2018 the original author or authors.
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
package com.debby.mybatis.core.xmlmapper.elements;

import com.debby.mybatis.core.InternalStatements;
import com.debby.mybatis.core.dom.xml.XmlElement;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 2:10:36 PM
 */
public class SelectOneElementGenerator extends SelectListElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
    	XmlElement answer = buildSelectElement(InternalStatements.SELECT_ONE.getId(), false);
        parentElement.addElement(answer);
    }

}

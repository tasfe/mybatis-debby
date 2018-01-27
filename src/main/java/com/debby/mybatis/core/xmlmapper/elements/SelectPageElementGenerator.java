/**
 * Copyright 2017-2018 the original author or authors.
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
package com.debby.mybatis.core.xmlmapper.elements;

import com.debby.mybatis.bean.Page;
import com.debby.mybatis.core.InternalStatements;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.criteria.EntityCriteria;

/**
 * @author rocky.hu
 * @date 2018-01-06 5:20 PM
 */
public class SelectPageElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", InternalStatements.SELECT_PAGE.getId()));
        answer.addAttribute(new Attribute("parameterType", EntityCriteria.class.getName()));
        answer.addAttribute(new Attribute("resultType", Page.class.getName()));

        parentElement.addElement(answer);
    }

}

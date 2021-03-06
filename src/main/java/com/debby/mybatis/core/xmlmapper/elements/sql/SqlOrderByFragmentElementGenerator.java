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
package com.debby.mybatis.core.xmlmapper.elements.sql;

import com.debby.mybatis.core.constant.Constants;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 2:57:56 PM
 */
public class SqlOrderByFragmentElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", Constants.ORDER_BY_SQL_FRAGMENT_ID));

        StringBuilder sb = new StringBuilder();

        XmlElement entityOrderListIfElement = new XmlElement("if");
        entityOrderListIfElement.addAttribute(new Attribute("test", "_parameter != null and _parameter.orders != null and _parameter.orders.size() > 0"));
        sb.setLength(0);
        sb.append(" ORDER BY ");
        entityOrderListIfElement.addElement(new TextElement(sb.toString()));

        XmlElement forEachElement = new XmlElement("foreach");
        forEachElement.addAttribute(new Attribute("collection", "orders"));
        forEachElement.addAttribute(new Attribute("item", "order"));
        forEachElement.addAttribute(new Attribute("separator", ","));
        forEachElement.addElement(new TextElement("${order}"));
        entityOrderListIfElement.addElement(forEachElement);
        answer.addElement(entityOrderListIfElement);
        
        parentElement.addElement(answer);
    }

}

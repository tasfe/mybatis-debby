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
import com.debby.mybatis.core.constant.Constants;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.TextElement;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.criteria.EntityCriteria;

/**
 * @author rocky.hu
 * @date Nov 23, 2017 2:10:36 PM
 */
public class SelectListForPagingElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", InternalStatements.INTERNAL_SELECT_LIST_FOR_PAGING.getId()));
        answer.addAttribute(new Attribute("parameterType", EntityCriteria.class.getName()));
        answer.addAttribute(new Attribute("resultMap", Constants.BASE_RESULT_MAP_ID));
        
        answer.addElement(getPaginationPrefixSqlFragment());
        
        answer.addElement(new TextElement("${columns}"));
        
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append("FROM ");
        sb.append(introspectedContext.getTableName());
        answer.addElement(new TextElement(sb.toString()));

        answer.addElement(getWhereSqlFragment());
        answer.addElement(getOrderBySqlFragment());
        answer.addElement(getPaginationSuffixSqlFragment());
        
        parentElement.addElement(answer);
    }

}

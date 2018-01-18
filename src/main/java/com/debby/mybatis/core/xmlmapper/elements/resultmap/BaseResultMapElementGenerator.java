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
package com.debby.mybatis.core.xmlmapper.elements.resultmap;

import com.debby.mybatis.core.DebbyResultMapping;
import com.debby.mybatis.core.constant.Constants;
import com.debby.mybatis.core.dom.xml.Attribute;
import com.debby.mybatis.core.dom.xml.XmlElement;
import com.debby.mybatis.core.helper.EntityHelper;
import com.debby.mybatis.core.xmlmapper.elements.AbstractXmlElementGenerator;
import com.debby.mybatis.util.StringUtils;

import java.util.List;

/**
 * @author rocky.hu
 * @date 2018-01-13 6:10 PM
 */
public class BaseResultMapElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("resultMap");

        Class<?> entityType = introspectedContext.getEntityType();
        answer.addAttribute(new Attribute("id", Constants.BASE_RESULT_MAP_ID));
        answer.addAttribute(new Attribute("type", entityType.getName()));

        List<DebbyResultMapping> resultMappingList = EntityHelper.getXResultMappingList(entityType, introspectedContext.getDebbyConfiguration().getCamelToUnderscore());
        for (DebbyResultMapping resultMapping : resultMappingList) {
            XmlElement element = null;
            if (resultMapping.isId()) {
                element = new XmlElement("id");
            } else {
                element = new XmlElement("result");
            }

            element.addAttribute(new Attribute("column", resultMapping.getColumn()));
            element.addAttribute(new Attribute("property", resultMapping.getProperty()));
            if (!StringUtils.isNullOrEmpty(resultMapping.getJdbcType())) {
                element.addAttribute(new Attribute("jdbcType", resultMapping.getJdbcType()));
            }
            if (!StringUtils.isNullOrEmpty(resultMapping.getTypeHandler())) {
                element.addAttribute(new Attribute("typeHandler", resultMapping.getTypeHandler()));
            }

            answer.addElement(element);
        }

        parentElement.addElement(answer);
    }

}

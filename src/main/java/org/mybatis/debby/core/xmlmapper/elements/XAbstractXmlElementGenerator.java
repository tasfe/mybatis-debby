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
package org.mybatis.debby.core.xmlmapper.elements;

import com.google.common.base.Strings;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.debby.core.XAbstractGenerator;
import org.mybatis.debby.core.keystrategy.XKeyStrategy;
import org.mybatis.debby.core.keystrategy.identity.XIdentityKeyStrategy;
import org.mybatis.debby.core.keystrategy.normal.XNormalKeyStrategy;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 17, 2017 2:20:48 PM
 * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator
 */
public abstract class XAbstractXmlElementGenerator extends XAbstractGenerator {
    
    public abstract void addElements(XmlElement parentElement);

    protected XmlElement getBaseColumnListElement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", "baseColumns")); 
        return answer;
    }
    
    /**
     * Get "if" element for "updateByCriteria" statement.
     *
     * @return
     */
    protected XmlElement getUpdateByCriteriaIfElement()
    {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "_parameter != null and _parameter.updatedCriteria != null"));

        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", "updateWhereSqlFragment"));
        ifElement.addElement(includeElement);

        return ifElement;
    }

    protected int idResultCount(ResultMap resultMap) {
        int index = 0;
        for (ResultMapping resultMapping : resultMap.getIdResultMappings()) {
            if (!Strings.isNullOrEmpty(resultMapping.getColumn()) && !Strings.isNullOrEmpty(resultMapping.getProperty())) {
                index++;
            }
        }

        return index;
    }

    protected void addSelectKey(ResultMap resultMap, XmlElement parentElement) {
        // Handle <SelectKey> element. Composite keys is not supported.
        if (idResultCount(resultMap) == 1) {
            ResultMapping idMappings = resultMap.getIdResultMappings().get(0);

            XKeyStrategy keyStrategy = introspectedContext.getDebbyConfiguration().getKeyStrategy();
            if (keyStrategy instanceof XIdentityKeyStrategy) {
                parentElement.addAttribute(new Attribute("useGeneratedKeys", "true"));
                parentElement.addAttribute(new Attribute("keyProperty", idMappings.getProperty()));
                parentElement.addAttribute(new Attribute("keyColumn", idMappings.getColumn()));
            } else if (keyStrategy instanceof XNormalKeyStrategy) {
                XNormalKeyStrategy normalKeyStrategy = (XNormalKeyStrategy) keyStrategy;
                if (!Strings.isNullOrEmpty(normalKeyStrategy.getRuntimeSqlStatement())) {
                    XmlElement selectKeyElement = new XmlElement("selectKey");
                    selectKeyElement.addAttribute(new Attribute("keyProperty", idMappings.getProperty()));
                    selectKeyElement.addAttribute(new Attribute("resultType", idMappings.getJavaType().getName()));
                    selectKeyElement.addAttribute(new Attribute("order", normalKeyStrategy.isBefore() ? "BEFORE" : "AFTER" ));
                    selectKeyElement.addElement(new TextElement(normalKeyStrategy.getRuntimeSqlStatement()));

                    parentElement.addElement(selectKeyElement);
                }
            }
        }
    }

}

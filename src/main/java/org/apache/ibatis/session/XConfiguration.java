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
package org.apache.ibatis.session;

import com.debby.mybatis.core.constant.Constants;
import com.debby.mybatis.util.ReflectUtils;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.parsing.XNode;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author rocky.hu
 * @date 2018-01-14 9:34 PM
 */
public class XConfiguration {

    private Configuration configuration;

    public XConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void buildAllStatements() throws IllegalAccessException {
        configuration.buildAllStatements();
        Collection<XMLStatementBuilder> incompleteStatements = configuration.getIncompleteStatements();
        for(Iterator<XMLStatementBuilder> iter = incompleteStatements.iterator(); iter.hasNext();){
            XMLStatementBuilder xmlStatementBuilder = iter.next();
            Field field = ReflectUtils.findField(XMLStatementBuilder.class, "context");
            field.setAccessible(true);
            XNode xNode = (XNode) field.get(xmlStatementBuilder);
            if (Constants.BASE_RESULT_MAP_ID.equals(xNode.getStringAttribute("resultMap"))) {
                iter.remove();
            }
        }
    }

}

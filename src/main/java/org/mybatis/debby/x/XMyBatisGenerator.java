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
package org.mybatis.debby.x;

import java.util.Set;

import javax.persistence.Table;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.XConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.base.CaseFormat;

/**
 * @author rocky.hu
 * @date Nov 22, 2017 11:23:58 AM
 */
public class XMyBatisGenerator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(XMyBatisGenerator.class);
    
    private XConfiguration xConfiguration;
    
    public XMyBatisGenerator(XConfiguration xConfiguration) {
		super();
		this.xConfiguration = xConfiguration;
	}

	public void generate() {
        LOGGER.info("[Start] Support debby mapper support function...");
        try {
        	Resource commonMapperXMLResource = new ClassPathResource("x/CommonMapper.xml");
			new XMLMapperBuilder(commonMapperXMLResource.getInputStream(), xConfiguration.getConfiguration(), null, xConfiguration.getConfiguration()
			        .getSqlFragments(), "mybatis.debby.CommonMapper").parse();
			
			Set<String> loadedResources = xConfiguration.getLoadedResources();
	        for (String resource : loadedResources) {
	        	if (resource.startsWith("interface ")) {
	                String namespace = resource.substring(resource.indexOf(" ") + 1);
	                ResultMap resultMap = xConfiguration.getConfiguration().getResultMap(namespace + ".baseResultMap");
	                if (resultMap != null) {
	                	
	                	// parse the table name
	                    String tableName = "";
	                    Class<?> type = resultMap.getType();
	                    Table table = type.getAnnotation(Table.class);
	                    if (table != null) {
	                        tableName = table.name();
	                    } else {
	                        tableName = xConfiguration.getTablePrefix() + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, type.getSimpleName());
	                    }
	                    
	                    
	                    
	                }
	        	}
	        }
			
		} catch (Exception e) {
            LOGGER.error("[Exception]: Support debby mapper support functio...", e);
            throw new BuilderException("[Exception] Support debby mapper support function.");
		}
        LOGGER.info("[End] Support debby mapper support function...");
    }

}

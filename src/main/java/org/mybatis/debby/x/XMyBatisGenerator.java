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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Table;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.XConfiguration;
import org.mybatis.debby.codegen.XInternalStatements;
import org.mybatis.debby.codegen.XIntrospectedContext;
import org.mybatis.debby.codegen.builder.XXMLMapperBuilder;
import org.mybatis.debby.codegen.util.FileUtils;
import org.mybatis.debby.codegen.xmlmapper.XXMLMapperGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;

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

	public void execute() {
        LOGGER.info("[Start] debby mapper support...");

        if (xConfiguration.isDebugEnabled() && Strings.isNullOrEmpty(xConfiguration.getMapperXMLOuputDirectory())) {
			throw new BuilderException("[Exception] 'mapperXMLOuputDirectory' must be set on debug mode.");
		}

        try {
			XIntrospectedContext context = null;
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

						// determine if the Mapper interface is a subclass of DebbyMapper
						Class<?> boundType = null;
						try {
							boundType = Resources.classForName(namespace);
						} catch (ClassNotFoundException e) {
							continue;
						}

						if (boundType != null && DebbyMapper.class.isAssignableFrom(boundType)) {

							// if the mapper xml or mapper annotation have the same statements(the name and the SqlCommandType is all same) as the DebbyMapper, that ignore the latter.
							List<XInternalStatements> alreadyOwnedInternalStatements = new ArrayList<XInternalStatements>();

							XInternalStatements[] xInternalStatements = XInternalStatements.values();
							for (int i = 0; i<xInternalStatements.length; i++) {
								if (xConfiguration.getConfiguration().hasStatement(namespace + "." + xInternalStatements[i].getId())) {
									if(xInternalStatements[i].getSqlCommandType() ==
											xConfiguration.getConfiguration().getMappedStatement(xInternalStatements[i].getId()).getSqlCommandType()) {
										alreadyOwnedInternalStatements.add(xInternalStatements[i]);
									}
								}
							}

							context = new XIntrospectedContext();
							context.setResultMap(resultMap);
							context.setTableName(tableName);
							context.setAlreadyOwnedInternalStatements(alreadyOwnedInternalStatements);
							context.setxConfiguration(xConfiguration);

							XXMLMapperGenerator mapperGenerator = new XXMLMapperGenerator();
							mapperGenerator.setIntrospectedContext(context);

							String formattedContent = mapperGenerator.getDocument().getFormattedContent();

							if (xConfiguration.isDebugEnabled()) {
								FileUtils.writeFile(xConfiguration.getMapperXMLOuputDirectory() + namespace.replace(".", "_") + ".xml", formattedContent);
							}

							InputStream inputStream = new ByteArrayInputStream(formattedContent.getBytes("UTF-8"));
							XXMLMapperBuilder builder = new XXMLMapperBuilder(inputStream, xConfiguration.getConfiguration(), null, xConfiguration.getConfiguration().getSqlFragments(), namespace);
							builder.parse();
						}

	                }
	        	}
	        }
			
		} catch (Exception e) {
            LOGGER.error("[Exception]: debby mapper support...", e);
            throw new BuilderException("[Exception] debby mapper support.");
		}
        LOGGER.info("[End] debby mapper support...");
    }

}

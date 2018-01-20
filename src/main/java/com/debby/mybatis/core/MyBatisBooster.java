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
package com.debby.mybatis.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.XConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debby.mybatis.DebbyConfiguration;
import com.debby.mybatis.DebbyMapper;
import com.debby.mybatis.core.builder.XXMLMapperBuilder;
import com.debby.mybatis.core.constant.Constants;
import com.debby.mybatis.core.helper.EntityHelper;
import com.debby.mybatis.core.interceptor.PaginationExecutorInterceptor;
import com.debby.mybatis.core.interceptor.PaginationResultSetHandlerInterceptor;
import com.debby.mybatis.core.xmlmapper.BaseResultMapGenerator;
import com.debby.mybatis.core.xmlmapper.XMLMapperGenerator;
import com.debby.mybatis.criteria.EntityCriteria;
import com.debby.mybatis.util.FileUtils;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date Nov 22, 2017 11:23:58 AM
 */
public class MyBatisBooster {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisBooster.class);
    
    private static final String DEBBY_MAPPER_FILE_SUFFIX = "_DEBBY.xml";

    private DebbyConfiguration debbyConfiguration;
    private Configuration configuration;
    
    public MyBatisBooster(DebbyConfiguration debbyConfiguration, Configuration configuration) {
		super();
		this.debbyConfiguration = debbyConfiguration;
		this.configuration = configuration;
	}

	public void execute() {

        LOGGER.info("Debby-Info ：[Start] debby mapper support...");

        if (debbyConfiguration.isDebugEnabled() && StringUtils.isNullOrEmpty(debbyConfiguration.getMapperXmlOutputPath())) {
			throw new BuilderException("[Exception] 'mapperXmlOutputPath' must be set on debug mode.");
		} 
        
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        try {

			configuration.getTypeAliasRegistry().registerAlias("EntityCriteria", EntityCriteria.class);
			configuration.addInterceptor(new PaginationExecutorInterceptor());
			configuration.addInterceptor(new PaginationResultSetHandlerInterceptor());

			IntrospectedContext introspectedContext = null;
			BaseResultMapGenerator baseResultMapGenerator = null;
			XMLMapperGenerator mapperGenerator = null;
			
			StringBuilder directory = new StringBuilder();
			directory.append(debbyConfiguration.getMapperXmlOutputPath());
			directory.append(currentTime);
			directory.append(File.separator);

			StringBuilder fileName = new StringBuilder();
			StringBuilder resultMapBuilder = new StringBuilder();

			MapperRegistry mapperRegistry = configuration.getMapperRegistry();
	        for (Class<?> mapperClass : mapperRegistry.getMappers()) {

				if (DebbyMapper.class.isAssignableFrom(mapperClass)) {
					String mapperName = mapperClass.getName();
					String baseResultMapId = mapperName + "." + Constants.BASE_RESULT_MAP_ID;

					// get the entity type
					Class<?> entityType = null;
					ParameterizedType parameterizedType = (ParameterizedType) (mapperClass.getGenericInterfaces()[0]);
					Type[] argumentTypes = parameterizedType.getActualTypeArguments();
					entityType = (Class<?>) argumentTypes[0];
					
					// valid entity
					EntityHelper.validate(entityType);

					// construct the base result map if it's not be predefined in the mapper xml.
					if (!EntityHelper.hasResultMap(configuration, baseResultMapId)) {
						introspectedContext = new IntrospectedContext(configuration, debbyConfiguration);
						introspectedContext.setEntityType(entityType);

						baseResultMapGenerator = new BaseResultMapGenerator();
						baseResultMapGenerator.setIntrospectedContext(introspectedContext);

						String formattedContent = baseResultMapGenerator.getDocument().getFormattedContent();
						if (debbyConfiguration.isDebugEnabled()) {
							resultMapBuilder.append(formattedContent);
							resultMapBuilder.append(System.getProperty("line.separator"));
						}

						parse(formattedContent, mapperName);
					}

					ResultMap baseResultMap = configuration.getResultMap(baseResultMapId);
					ResultMapRegistry.putResultMap(entityType.getName(), baseResultMap);

					// if the mapper xml or mapper annotation have the same statements(the name and the SqlCommandType is all same) as the DebbyMapper, that ignore the latter.
					List<InternalStatements> alreadyOwnedInternalStatements = new ArrayList<InternalStatements>();
					InternalStatements[] xInternalStatements = InternalStatements.values();
					for (int i = 0; i<xInternalStatements.length; i++) {
						if (configuration.hasStatement(mapperName + "." + xInternalStatements[i].getId(), false)) {
							if(xInternalStatements[i].getSqlCommandType() ==
									configuration.getMappedStatement(mapperName + "." + xInternalStatements[i].getId(), false).getSqlCommandType()) {
								alreadyOwnedInternalStatements.add(xInternalStatements[i]);
							}
						}
					}

					introspectedContext = new IntrospectedContext(configuration, debbyConfiguration);
					introspectedContext.setResultMap(baseResultMap);
					introspectedContext.setEntityType(entityType);
					introspectedContext.setAlreadyOwnedInternalStatements(alreadyOwnedInternalStatements);

					mapperGenerator = new XMLMapperGenerator();
					mapperGenerator.setIntrospectedContext(introspectedContext);

					String formattedContent = mapperGenerator.getDocument().getFormattedContent();

					if (debbyConfiguration.isDebugEnabled()) {
						fileName.setLength(0);
						fileName.append(mapperName.replace(".", "$"));
						fileName.append(DEBBY_MAPPER_FILE_SUFFIX);
						
						FileUtils.writeFile(directory.toString(), fileName.toString(), formattedContent);
					}

					parse(formattedContent, mapperName);
				}

	        }

			if (debbyConfiguration.isDebugEnabled() && !StringUtils.isNullOrEmpty(resultMapBuilder.toString())) {
	        	FileUtils.writeFile(directory.toString(), "BASE_RESULT_MAP_DEBBY.xml", resultMapBuilder.toString());
			}

	        XConfiguration xConfiguration = new XConfiguration(configuration);
	        xConfiguration.buildAllStatements();

		} catch (Exception e) {
            LOGGER.error("Debby-Error ：debby mapper support...", e);
            throw new BuilderException("[Exception] debby mapper support.");
		}

        LOGGER.info("Debby-Info ：[END] debby mapper support...");
    }

	private void parse(String content, String mapperName) throws UnsupportedEncodingException {
		InputStream inputStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
		XXMLMapperBuilder builder = new XXMLMapperBuilder(inputStream, configuration, null, configuration.getSqlFragments(), mapperName);
		builder.parse();
	}

}

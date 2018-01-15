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
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.debby.mybatis.core.constant.XConstants;
import com.debby.mybatis.core.helper.EntityHelper;
import com.debby.mybatis.core.interceptor.PaginationExecutorInterceptor;
import com.debby.mybatis.core.interceptor.PaginationResultSetHandlerInterceptor;
import com.debby.mybatis.core.xmlmapper.XBaseResultMapGenerator;
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
import com.debby.mybatis.core.xmlmapper.XXMLMapperGenerator;
import com.debby.mybatis.criteria.EntityCriteria;
import com.debby.mybatis.util.FileUtils;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date Nov 22, 2017 11:23:58 AM
 */
public class XMyBatisGenerator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(XMyBatisGenerator.class);

    private DebbyConfiguration debbyConfiguration;
    private Configuration configuration;
    
    public XMyBatisGenerator(DebbyConfiguration debbyConfiguration, Configuration configuration) {
		super();
		this.debbyConfiguration = debbyConfiguration;
		this.configuration = configuration;
	}

	public void execute() {

        LOGGER.info("Debby-Info ：[Start] debby mapper support...");

        if (debbyConfiguration.isDebugEnabled() && StringUtils.isNullOrEmpty(debbyConfiguration.getMapperXMLOutputDirectory())) {
			throw new BuilderException("[Exception] 'mapperXMLOuputDirectory' must be set on debug mode.");
		}

        try {

			configuration.getTypeAliasRegistry().registerAlias("EntityCriteria", EntityCriteria.class);
			configuration.addInterceptor(new PaginationExecutorInterceptor());
			configuration.addInterceptor(new PaginationResultSetHandlerInterceptor());

			XIntrospectedContext introspectedContext = null;
			XBaseResultMapGenerator baseResultMapGenerator = null;
			XXMLMapperGenerator mapperGenerator = null;

			MapperRegistry mapperRegistry = configuration.getMapperRegistry();
	        for (Class<?> mapperClass : mapperRegistry.getMappers()) {

				if (DebbyMapper.class.isAssignableFrom(mapperClass)) {
					String mapperName = mapperClass.getName();
					String baseResultMapId = mapperName + "." + XConstants.BASE_RESULT_MAP_ID;

					// get the entity type
					Class<?> entityType = null;
					ParameterizedType parameterizedType = (ParameterizedType) (mapperClass.getGenericInterfaces()[0]);
					Type[] argumentTypes = parameterizedType.getActualTypeArguments();
					entityType = (Class<?>) argumentTypes[0];

					// Construct the base result map if it's not be predefined in the mapper xml.
					if (!EntityHelper.hasResultMap(configuration, baseResultMapId)) {
						introspectedContext = new XIntrospectedContext(configuration, debbyConfiguration);
						introspectedContext.setEntityType(entityType);

						baseResultMapGenerator = new XBaseResultMapGenerator();
						baseResultMapGenerator.setIntrospectedContext(introspectedContext);

						String formattedContent = baseResultMapGenerator.getDocument().getFormattedContent();
						if (debbyConfiguration.isDebugEnabled()) {
							LOGGER.debug("[{}][BASE_RESULT_MAP] : {}", mapperName, formattedContent);
						}

						parse(formattedContent, mapperName);
					}

					ResultMap baseResultMap = configuration.getResultMap(baseResultMapId);
					XBaseResultMapRegistry.putResultMap(entityType.getName(), baseResultMap);

					// if the mapper xml or mapper annotation have the same statements(the name and the SqlCommandType is all same) as the DebbyMapper, that ignore the latter.
					List<XInternalStatements> alreadyOwnedInternalStatements = new ArrayList<XInternalStatements>();
					XInternalStatements[] xInternalStatements = XInternalStatements.values();
					for (int i = 0; i<xInternalStatements.length; i++) {
						if (configuration.hasStatement(mapperName + "." + xInternalStatements[i].getId(), false)) {
							if(xInternalStatements[i].getSqlCommandType() ==
									configuration.getMappedStatement(mapperName + "." + xInternalStatements[i].getId(), false).getSqlCommandType()) {
								alreadyOwnedInternalStatements.add(xInternalStatements[i]);
							}
						}
					}

					introspectedContext = new XIntrospectedContext(configuration, debbyConfiguration);
					introspectedContext.setResultMap(baseResultMap);
					introspectedContext.setEntityType(entityType);
					introspectedContext.setAlreadyOwnedInternalStatements(alreadyOwnedInternalStatements);

					mapperGenerator = new XXMLMapperGenerator();
					mapperGenerator.setIntrospectedContext(introspectedContext);

					String formattedContent = mapperGenerator.getDocument().getFormattedContent();

					if (debbyConfiguration.isDebugEnabled()) {
						FileUtils.writeFile(debbyConfiguration.getMapperXMLOutputDirectory(), mapperName.replace(".", "_") + ".xml", formattedContent);
					}

					parse(formattedContent, mapperName);
				}

	        }

			new XConfiguration(configuration).buildAllStatements();

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

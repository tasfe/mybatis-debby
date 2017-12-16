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
package com.debby.mybatis.core.builder;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;

/**
 * @author rocky.hu
 * @date Nov 22, 2017 10:47:45 PM
 */
public class XXMLMapperBuilder extends BaseBuilder {

	private XPathParser parser;
	private Map<String, XNode> sqlFragments;
	private MapperBuilderAssistant builderAssistant;

	public XXMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource,
			Map<String, XNode> sqlFragments, String namespace) {
		this(inputStream, configuration, resource, sqlFragments);
		this.builderAssistant.setCurrentNamespace(namespace);
	}

	private XXMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource,
			Map<String, XNode> sqlFragments) {
		this(new XPathParser(inputStream, true, configuration.getVariables(), new XMLMapperEntityResolver()),
				configuration, resource, sqlFragments);
	}

	private XXMLMapperBuilder(XPathParser parser, Configuration configuration, String resource,
			Map<String, XNode> sqlFragments) {
		super(configuration);
		this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
		this.parser = parser;
		this.sqlFragments = sqlFragments;
	}

	public void parse() {
		configurationElement(parser.evalNode("/mapper"));
	}

	private void configurationElement(XNode context) {
		try {
			sqlElement(context.evalNodes("/mapper/sql"));
			buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
		} catch (Exception e) {
			throw new BuilderException("Error parsing Mapper XML. Cause: " + e, e);
		}
	}

	private void sqlElement(List<XNode> list) throws Exception {
		if (configuration.getDatabaseId() != null) {
			sqlElement(list, configuration.getDatabaseId());
		}
		sqlElement(list, null);
	}

	private void sqlElement(List<XNode> list, String requiredDatabaseId) throws Exception {
		for (XNode context : list) {
			String databaseId = context.getStringAttribute("databaseId");
			String id = context.getStringAttribute("id");
			id = builderAssistant.applyCurrentNamespace(id, false);
			if (databaseIdMatchesCurrent(id, databaseId, requiredDatabaseId)) {
				sqlFragments.put(id, context);
			}
		}
	}

	private void buildStatementFromContext(List<XNode> list) {
		if (configuration.getDatabaseId() != null) {
			buildStatementFromContext(list, configuration.getDatabaseId());
		}
		buildStatementFromContext(list, null);
	}

	private void buildStatementFromContext(List<XNode> list, String requiredDatabaseId) {
		for (XNode context : list) {
			final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, builderAssistant,
					context, requiredDatabaseId);
			try {
				statementParser.parseStatementNode();
			} catch (IncompleteElementException e) {
				configuration.addIncompleteStatement(statementParser);
			}
		}
	}

	private boolean databaseIdMatchesCurrent(String id, String databaseId, String requiredDatabaseId) {
		if (requiredDatabaseId != null) {
			if (!requiredDatabaseId.equals(databaseId)) {
				return false;
			}
		} else {
			if (databaseId != null) {
				return false;
			}
			// skip this fragment if there is a previous one with a not null
			// databaseId
			if (this.sqlFragments.containsKey(id)) {
				XNode context = this.sqlFragments.get(id);
				if (context.getStringAttribute("databaseId") != null) {
					return false;
				}
			}
		}
		return true;
	}

	public XNode getSqlFragment(String refid) {
		return sqlFragments.get(refid);
	}

}

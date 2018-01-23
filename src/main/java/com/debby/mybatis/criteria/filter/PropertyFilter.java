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
package com.debby.mybatis.criteria.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debby.mybatis.core.helper.EntityHelper;

/**
 * Filter property when execute querying.
 * 
 * @author rocky.hu
 * @date Jan 22, 2018 4:45:53 PM
 */
public class PropertyFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyFilter.class);

	private final Class<?> entityType;
	private PropertyFilterMode filterMode = PropertyFilterMode.EXCLUDE;
	private List<String> propertyList = new ArrayList<String>();

	public PropertyFilter(Class<?> entityType) {
		this.entityType = entityType;
	}

	public PropertyFilterMode getFilterMode() {
		return filterMode;
	}

	public void setFilterMode(PropertyFilterMode filterMode) {
		this.filterMode = filterMode;
	}
	
	public List<String> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<String> propertyList) {
		this.propertyList = propertyList;
	}

	public void add(String[] properties) {
		if (properties != null && properties.length > 0) {
			propertyList.addAll(Arrays.asList(properties));
		}
	}

	public String getColumns() {
		StringBuilder sb = new StringBuilder();
		List<ResultMapping> resultMappingList = EntityHelper.getPropertyResultMappings(entityType);
		List<String> columnList = new ArrayList<String>();
		for (int i = 0; i < resultMappingList.size(); i++) {
			ResultMapping resultMapping = resultMappingList.get(i);
			String propertyName = resultMapping.getProperty();
			String column = resultMapping.getColumn();

			if (propertyName.contains(".")) {
				propertyName = propertyName.substring(0, propertyName.indexOf("."));
			}

			if (filterMode == PropertyFilterMode.EXCLUDE) {
				if (propertyList.contains(propertyName)) {
					continue;
				}
			} else {
				if (!propertyList.contains(propertyName)) {
					continue;
				}
			}

			columnList.add(column);
		}

		Iterator<String> iter = columnList.iterator();
		while (iter.hasNext()) {
			sb.append(iter.next());
			if (iter.hasNext()) {
				sb.append(", ");
			}
		}

		LOGGER.debug("[{}][COLUMNS]: [{}]", entityType.getName(), sb.toString());
		return sb.toString();
	}

}

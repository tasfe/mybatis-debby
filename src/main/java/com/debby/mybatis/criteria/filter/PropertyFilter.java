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
package com.debby.mybatis.criteria.filter;

import com.debby.mybatis.core.helper.EntityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filter property when execute querying.
 * 
 * @author rocky.hu
 * @date Jan 22, 2018 4:45:53 PM
 */
public class PropertyFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyFilter.class);

	private final Class<?> entityType;
	private boolean exclude;
	private List<String> propertyList = new ArrayList<String>();

	public PropertyFilter(Class<?> entityType) {
		this.entityType = entityType;
	}
	
	public boolean getExclude() {
		return exclude;
	}

	public void setExclude(boolean exclude) {
		this.exclude = exclude;
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
		return EntityHelper.getColumns(entityType, exclude, propertyList);
	}

}

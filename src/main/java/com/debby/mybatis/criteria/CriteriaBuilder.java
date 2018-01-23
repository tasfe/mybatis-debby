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
package com.debby.mybatis.criteria;

/**
 * @author rocky.hu
 * @date Jan 23, 2018 2:55:05 PM
 */
public class CriteriaBuilder {
	
	private final Class<?> entityType;
	
	public CriteriaBuilder(Class<?> entityType) {
		this.entityType = entityType;
	}
	
	public Criteria build() {
		return new Criteria(entityType);
	}

}

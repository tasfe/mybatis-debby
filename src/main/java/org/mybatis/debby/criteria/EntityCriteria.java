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
package org.mybatis.debby.criteria;

/**
 * @author rocky.hu
 * @date Aug 23, 2016, 11:18:24 PM
 */
public interface EntityCriteria {

	/**
	 * Create Criteria that use for warping Criterion.
	 *
	 * @return Criteria
	 */
	Criteria createCriteria();

	/**
	 * Add another Criteria for OR function.
	 *
	 * @return Criteria
	 */
	Criteria or();

	/**
	 * Set a limit upon the number of objects to be retrieved.
	 *
	 * @param maxResults
	 *            the maximum number of results
	 */
	void setMaxResults(Integer maxResults);

	/**
	 * Set the first result to be retrieved.
	 *
	 * @param firstResult
	 *            the first result to retrieve, numbered from <tt>0</tt>
	 */
	void setFirstResult(Integer firstResult);

	/**
	 * Set distinct for select records.
	 *
	 * @param distinct
	 */
	void setDistinct(Boolean distinct);

	/**
	 * Add an {@link EntityOrder ordering} to the result set.
	 *
	 * @param entityOrder
	 *            The {@link EntityOrder entityOrder} object representing an
	 *            ordering to be applied to the results.
	 */
	void addEntityOrder(EntityOrder entityOrder);

}

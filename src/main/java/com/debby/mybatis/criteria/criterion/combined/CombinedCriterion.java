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
package com.debby.mybatis.criteria.criterion.combined;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.debby.mybatis.criteria.criterion.Criterion;
import com.debby.mybatis.criteria.criterion.simple.SimpleCriterion;

/**
 * @author rocky.hu
 * @date Jan 25, 2018 2:25:54 PM
 */
public class CombinedCriterion implements Criterion {

	private CombinedCriterionConnector connector;
	private List<Criterion> criterions = new ArrayList<Criterion>();
	
	public CombinedCriterion(CombinedCriterionConnector connector, Criterion[] criterions) {
		this.connector = connector;
		this.criterions = Arrays.asList(criterions);
	}

	public CombinedCriterionConnector getConnector() {
		return connector;
	}

	public void setConnector(CombinedCriterionConnector connector) {
		this.connector = connector;
	}

	public List<Criterion> getCriterions() {
		return criterions;
	}

	public void setCriterions(List<Criterion> criterions) {
		this.criterions = criterions;
	}

	@Override
	public String toSqlString() {
		StringBuilder sb = new StringBuilder();
		recursive(this, sb);
		return sb.toString();
	}
	
	private void recursive(CombinedCriterion combinedCriterion, StringBuilder sb) {
		if (combinedCriterion.getCriterions().size() > 0) {
			sb.append("(");
			sb.append(combinedCriterion.getConnector().name());
			sb.append(" ");
			for (Criterion criterion : combinedCriterion.getCriterions()) {
				if (criterion instanceof SimpleCriterion) {
					sb.append(criterion.toSqlString());
				} else {
					recursive((CombinedCriterion) criterion, sb);
				}
			}
			sb.append(")");
		}
	}

}

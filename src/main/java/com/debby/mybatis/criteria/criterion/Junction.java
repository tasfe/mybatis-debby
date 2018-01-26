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
package com.debby.mybatis.criteria.criterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.debby.mybatis.criteria.EntityCriteria;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date Jan 26, 2018 2:30:32 PM
 */
public class Junction implements Criterion {
	
	private final Nature nature;
	private final List<Criterion> criterions = new ArrayList<Criterion>();
	
	protected Junction(Nature nature) {
		this.nature = nature;
	}

	protected Junction(Nature nature, Criterion... criterion) {
		this( nature );
		Collections.addAll( criterions, criterion );
	}
	
	public Junction add(Criterion criterion) {
		criterions.add( criterion );
		return this;
	}

	public Nature getNature() {
		return nature;
	}
	
	public Iterable<Criterion> criterions() {
		return criterions;
	}
	
	@Override
	public String toSqlString(Class<?> entityType) {
		if ( criterions.size()==0 ) {
			return "1=1";
		}

		final StringBuilder buffer = new StringBuilder().append( '(' );
		final Iterator itr = criterions.iterator();
		while ( itr.hasNext() ) {
			buffer.append( ( (Criterion) itr.next() ).toSqlString(entityType) );
			if ( itr.hasNext() ) {
				buffer.append( ' ' )
						.append( nature.getOperator() )
						.append( ' ' );
			}
		}

		return buffer.append( ')' ).toString();
	}

	@Override
	public String toString() {
		return '(' + StringUtils.join( ' ' + nature.getOperator() + ' ', criterions.iterator() ) + ')';
	}
	
	public static enum Nature {
		
		AND,
		OR;

		public String getOperator() {
			return name();
		}
	}

}

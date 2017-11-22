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

import java.util.ArrayList;
import java.util.List;

/**
 * @author rocky.hu
 * @date Aug 24, 2016, 11:20:59 PM
 */
public class EntityCriteriaImpl implements EntityCriteria {
    
    private Integer maxResults;
    private Integer firstResult;
    private Boolean distinct;

    private List<Criteria> oredCriteriaList;
    private List<EntityOrder> entityOrderList;

    public EntityCriteriaImpl()
    {
    }

    public Integer getMaxResults()
    {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults)
    {
        this.maxResults = maxResults;
    }

    public Integer getFirstResult()
    {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult)
    {
        this.firstResult = firstResult;
    }

    public Boolean getDistinct()
    {
        return distinct;
    }

    public void setDistinct(Boolean distinct)
    {
        this.distinct = distinct;
    }

    public List<Criteria> getOredCriteriaList()
    {
        return oredCriteriaList;
    }

	/*public void setOredCriteriaList(List<Criteria> oredCriteriaList)
    {
		this.oredCriteriaList = oredCriteriaList;
	}*/

    public List<EntityOrder> getEntityOrderList()
    {
        return entityOrderList;
    }

	/*public void setEntityOrderList(List<EntityOrder> entityOrderList)
	{
		this.entityOrderList = entityOrderList;
	}*/

    @Override
    public void addEntityOrder(EntityOrder entityOrder)
    {
        if (entityOrderList == null) {
            entityOrderList = new ArrayList<EntityOrder>();
        }

        entityOrderList.add(entityOrder);
    }

    /**
     * Create and add a Criteria.
     * <p>
     * WARN: this method can only call once, if you want to add another Criteria, please use {@link #or()} method.
     */
    @Override
    public Criteria createCriteria()
    {
        if (oredCriteriaList == null) {
            oredCriteriaList = new ArrayList<Criteria>();
        }
        else if (oredCriteriaList.size() > 1) {
            throw new RuntimeException("A Criteria has been existed, if you want add another criteria, please use or" +
                    "() method.");
        }

        Criteria criteria = createCriteriaInternal();
        oredCriteriaList.add(criteria);
        return criteria;
    }

    @Override
    public Criteria or()
    {
        if (oredCriteriaList == null || oredCriteriaList.size() == 0) {
            throw new RuntimeException("Use createCriteria() method instead.");
        }
        Criteria criteria = createCriteriaInternal();
        oredCriteriaList.add(criteria);
        return criteria;
    }

    private Criteria createCriteriaInternal()
    {
        Criteria criteria = new Criteria();
        return criteria;
    }

}

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
package org.mybatis.debby;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.debby.criteria.EntityCriteria;

/**
 * @author rocky.hu
 * @date Aug 6, 2016 11:03:23 PM
 */
public interface DebbyMapper<ENTITY, PK extends Serializable> {
    
    /**
     * Insert an entity.
     *
     * @param entity
     */
    void insert(ENTITY entity);

    /**
     * Insert an entity selectively.
     *
     * Don't like {@link #insert(Object)}}, the method just insert the property which the value is not null.
     *
     * @param entity
     */
    void insertSelective(ENTITY entity);

    /**
     * Update an entity by primary key.
     *
     * @param entity
     */
    void updateByPrimaryKey(ENTITY entity);

    /**
     * Update an entity selectively by primary key.
     *
     * Don't like {@link #updateByPrimaryKey(Object)}}, the method just update the property which the value is not null.
     *
     * @param entity
     */
    void updateByPrimaryKeySelective(ENTITY entity);

    /**
     * Update an entity by updated conditions.
     *
     * @param record
     * @param updatedCriteria
     * @return
     */
    int updateByCriteria(@Param("record") ENTITY record, @Param("updatedCriteria") EntityCriteria updatedCriteria);

    /**
     * Update an entity selective by updated conditions.
     *
     * Don't like {@link #updateByCriteria(Object, EntityCriteria)}, the method just update the property which the value is not null.
     *
     * @param record
     * @param updatedCriteria
     * @return
     */
    int updateByCriteriaSelective(@Param("record") ENTITY record, @Param("updatedCriteria") EntityCriteria updatedCriteria);

    /**
     * Select an entity by primary key.
     *
     * @param pk
     * @return
     */
    ENTITY selectByPrimaryKey(PK pk);

    /**
     * A enhanced select method that accept different selective conditions.
     *
     * @param criteria
     * @return
     */
    List<ENTITY> selectByCriteria(EntityCriteria criteria);
    
    /**
     * Count the records by different conditions.
     *
     * @param criteria
     * @return
     */
    long selectCountByCriteria(EntityCriteria criteria);

    /**
     * Delete an entity by primary key.
     *
     * @param pk
     */
    void deleteByPrimaryKey(PK pk);

    /**
     * A enhanced select method that accept different deleted conditions.
     *
     * @param criteria
     * @return
     */
    int deleteByCriteria(EntityCriteria criteria);

}

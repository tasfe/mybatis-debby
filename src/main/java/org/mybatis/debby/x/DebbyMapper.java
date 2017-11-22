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
package org.mybatis.debby.x;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.debby.criteria.EntityCriteria;

/**
 * @author rocky.hu
 * @date Aug 6, 2016 11:03:23 PM
 */
public interface DebbyMapper<ENTITY, ID extends Serializable> {
    
    /**
     * Insert an entity object.
     * After, it has the primary id value assigned and we can get from the entity object we just inserted.
     *
     * @param entity
     */
    void insert(ENTITY entity);

    /**
     * Update an entity object. Mare sure that the value of property "id" is not null.
     *
     * @param entity
     *//*
    void updateById(ENTITY entity);*/

    /**
     * A enhanced update method that accept different updated conditions.
     *
     * @param record
     * @param updatedCriteria
     * @return
     */
    int updateByCriteria(@Param("record") ENTITY record, @Param("updatedCriteria") EntityCriteria updatedCriteria);

    /**
     * Select an entity by id.
     *
     * @param id
     * @return
     */
    ENTITY selectById(ID id);

    /**
     * A enhanced select method that accept different selective conditions.
     *
     * @param criteria
     * @return
     */
    List<ENTITY> selectByCriteria(EntityCriteria criteria);

    /**
     * Select specified count of records with criteria.
     * <p>
     * <p>
     * set the firstResult and maxResult property of criteria
     * </p>
     *
     * @param criteria
     * @return
     */
    List<ENTITY> selectWithCountByCriteria(EntityCriteria criteria);

    /**
     * Delete a entity by id.
     *
     * @param id
     */
    void deleteById(ID id);

    /**
     * A enhanced select method that accept different deleted conditions.
     *
     * @param criteria
     * @return
     */
    int deleteByCriteria(EntityCriteria criteria);

    /**
     * Count the records by different conditions.
     *
     * @param criteria
     * @return
     */
    long countByCriteria(EntityCriteria criteria);

}

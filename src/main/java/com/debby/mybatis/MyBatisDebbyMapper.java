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
package com.debby.mybatis;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.debby.mybatis.bean.Page;
import com.debby.mybatis.criteria.EntityCriteria;

/**
 * @author rocky.hu
 * @date Aug 6, 2016 11:03:23 PM
 */
public interface MyBatisDebbyMapper<ENTITY, ID extends Serializable> {
    
    /**
     * Insert an entity.
     *
     * @param entity
     */
    void insert(ENTITY entity);

    /**
     * Insert an entity selectively.
     *
     * Unlike {@link #insert(Object)}}, the method just insert the property which the value is not null.
     *
     * @param entity
     */
    void insertSelective(ENTITY entity);

    /**
     * Update an entity by primary key.
     *
     * @param entity
     */
    void updateById(ENTITY entity);

    /**
     * Update an entity selectively by primary key.
     *
     * Unlike {@link #updateById(Object)}}, the method just update the property which the value is not null.
     *
     * @param entity
     */
    void updateByIdSelective(ENTITY entity);

    /**
     * Update an entity by updated conditions.
     *
     * @param record
     * @param updatedCriteria
     * @return
     */
    int update(@Param("record") ENTITY record, @Param("updatedCriteria") EntityCriteria updatedCriteria);

    /**
     * Update an entity selective by updated conditions.
     *
     * Unlike {@link #update(Object, EntityCriteria1)}, the method just update the property which the value is not null.
     *
     * @param record
     * @param updatedCriteria
     * @return
     */
    int updateSelective(@Param("record") ENTITY record, @Param("updatedCriteria") EntityCriteria updatedCriteria);

    /**
     * Select an entity by primary key.
     *
     * @param id
     * @return
     */
    ENTITY selectById(ID id);
    
    /**
     * Select an entity by criteria.
     * 
     * @param criteria
     * @return
     */
    ENTITY selectOne(EntityCriteria criteria);

    /**
     * Select entity collection by criteria.
     *
     * @param criteria
     * @return
     */
    List<ENTITY> selectList(EntityCriteria criteria);
    
    /**
     * Paging Query.
     * 
     * @param criteria
     * @return
     */
    Page<ENTITY> selectPage(EntityCriteria criteria);
    
    /**
     * Count the records by different conditions.
     *
     * @param criteria
     * @return
     */
    long selectCount(EntityCriteria criteria);

    /**
     * Delete an entity by primary key.
     *
     * @param id
     */
    void deleteById(ID id);

    /**
     * A enhanced delete method that accept different deleted conditions.
     *
     * @param criteria
     * @return
     */
    int delete(EntityCriteria criteria);

}

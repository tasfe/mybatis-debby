/**
 * Copyright 2017-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.debby.mybatis.mapping;

import java.io.Serializable;

import com.debby.mybatis.annotation.MappingCompositeId;
import com.debby.mybatis.annotation.MappingId;
import com.debby.mybatis.annotation.MappingResult;

/**
 * @author rocky.hu
 * @date 2018-01-14 5:49 PM
 */
public class Entity {

    @MappingCompositeId
    private EntityCompositeIdMock entityCompositeIdMock;
    @MappingResult(association = true)
    private EntityAssocationMock entityAssocationMock;
    private int age;

    public EntityCompositeIdMock getEntityCompositeIdMock() {
        return entityCompositeIdMock;
    }

    public void setEntityCompositeIdMock(EntityCompositeIdMock entityCompositeIdMock) {
        this.entityCompositeIdMock = entityCompositeIdMock;
    }

    public EntityAssocationMock getEntityAssocationMock() {
        return entityAssocationMock;
    }

    public void setEntityAssocationMock(EntityAssocationMock entityAssocationMock) {
        this.entityAssocationMock = entityAssocationMock;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    class EntityAssocationMock {

        @MappingId
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    
    class EntityCompositeIdMock implements Serializable {

        @MappingId
        private int id;
        @MappingId(generatedValue = false)
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

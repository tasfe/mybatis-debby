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
package com.debby.mybatis.batch;

import com.debby.mybatis.AbstractMyBatisDebbyMapperTest;
import com.debby.mybatis.MyBatisHelper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rocky.hu
 * @date 2018-01-28 3:34 PM
 */
public class AnimalMapperTest extends AbstractMyBatisDebbyMapperTest<AnimalMapper> {

    public AnimalMapperTest() {
        super();
        this.setDdlPath("/data/ddl/animal.ddl");
    }

    @Test
    public void testBatchInsert() {

        List<Animal> animalList = new ArrayList<>();

        SqlSession sqlSession = MyBatisHelper.getSqlSessionFactory().openSession(ExecutorType.BATCH, TransactionIsolationLevel.SERIALIZABLE);
        AnimalMapper animalMapper = sqlSession.getMapper(AnimalMapper.class);

        Animal animal = null;
        for (int i=0; i<10; i++) {
            animal = new Animal();
            animal.setName("dog" + i);
            animal.setWeight(i);
            animalList.add(animal);

            animalMapper.insert(animal);
        }

        sqlSession.commit();
        sqlSession.close();
    }

}

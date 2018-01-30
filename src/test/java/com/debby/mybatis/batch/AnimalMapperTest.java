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

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.AbstractMyBatisDebbyMapperTest;
import com.debby.mybatis.MyBatisHelper;

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

        SqlSession sqlSession = MyBatisHelper.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        AnimalMapper animalMapper = sqlSession.getMapper(AnimalMapper.class);

        Animal animal = null;
        for (int i=0; i<10; i++) {
            animal = new Animal();
            animal.setName("dog" + i);
            animal.setWeight(i);

            animalMapper.insert(animal);
            
            animalList.add(animal);
        }

        sqlSession.commit();
        sqlSession.close();
        
        Assert.assertEquals(animalList.get(0).getId().intValue(), 1);
        Assert.assertEquals(animalList.get(1).getId().intValue(), 2);
        Assert.assertEquals(animalList.get(2).getId().intValue(), 3);
        Assert.assertEquals(animalList.get(3).getId().intValue(), 4);
        Assert.assertEquals(animalList.get(4).getId().intValue(), 5);
        Assert.assertEquals(animalList.get(5).getId().intValue(), 6);
        Assert.assertEquals(animalList.get(6).getId().intValue(), 7);
        Assert.assertEquals(animalList.get(7).getId().intValue(), 8);
        Assert.assertEquals(animalList.get(8).getId().intValue(), 9);
        Assert.assertEquals(animalList.get(9).getId().intValue(), 10);
    }

}

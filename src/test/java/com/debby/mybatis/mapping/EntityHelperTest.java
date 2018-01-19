/**
 * Copyright 2016-2017 the original author or authors.
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

import org.testng.annotations.Test;

import com.debby.mybatis.core.helper.EntityHelper;
import com.debby.mybatis.exception.MappingException;

/**
 * @author rocky.hu
 * @date 2018-01-14 5:09 PM
 */
public class EntityHelperTest {

    @Test
    public void test() {
    	EntityHelper.validate(Entity.class);
    }
    
    @Test(expectedExceptions = {MappingException.class}, expectedExceptionsMessageRegExp = "Use MappingCompositeId for multiple ids.")
    public void testEntityWithMultipleMappingId() {
    	EntityHelper.validate(EntityWithMultipleMappingId.class);
    }
    
    @Test(expectedExceptions = {MappingException.class}, expectedExceptionsMessageRegExp = "Only one field can be annotated with MappingCompositeId annotation.")
    public void testEntityWithMultipleMappingCompositeId() {
    	EntityHelper.validate(EntityWithMultipleMappingCompositeId.class);
    }
    
    @Test(expectedExceptions = {MappingException.class}, expectedExceptionsMessageRegExp = "Use MappingId or MappingCompositeId, not both.")
    public void testEntityWithMappingIdAndMappingCompositeId() {
    	EntityHelper.validate(EntityWithMappingIdAndMappingCompositeId.class);
    }
    
    @Test(expectedExceptions = {MappingException.class}, expectedExceptionsMessageRegExp = "Id field must be specified for \\[com.debby.mybatis.mapping.EntityWithNoId\\].")
    public void testEntityWithNoId() {
    	EntityHelper.validate(EntityWithNoId.class);
    }
    
    @Test(expectedExceptions = {MappingException.class}, expectedExceptionsMessageRegExp = "No MappingId found in composite type \\[com.debby.mybatis.mapping.EntityWithMappingCompositeIdAndNoMappingIdIn\\$CompositeId\\].")
    public void testEntityWithMappingCompositeIdAndNoMappingIdIn() {
    	EntityHelper.validate(EntityWithMappingCompositeIdAndNoMappingIdIn.class);
    }
    
    @Test(expectedExceptions = {MappingException.class}, expectedExceptionsMessageRegExp = "Only one field can be set to generated value.")
    public void testEntityWithMappingCompositeIdAndMultipleGeneratedMappingId() {
    	EntityHelper.validate(EntityWithMappingCompositeIdAndMultipleGeneratedMappingId.class);
    }

}

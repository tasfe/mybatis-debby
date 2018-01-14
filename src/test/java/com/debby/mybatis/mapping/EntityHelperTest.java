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

import com.debby.mybatis.core.XResultMapping;
import com.debby.mybatis.core.helper.EntityHelper;
import com.debby.mybatis.key.composite.Member;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author rocky.hu
 * @date 2018-01-14 5:09 PM
 */
public class EntityHelperTest {

    @Test
    public void test() {
        List<XResultMapping> resultMappingList = EntityHelper.getXResultMappingList(Member.class, true);
        for (XResultMapping resultMapping : resultMappingList) {
            System.out.println(resultMapping);
        }
    }

}
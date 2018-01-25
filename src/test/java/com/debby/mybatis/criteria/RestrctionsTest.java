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
package com.debby.mybatis.criteria;

import static com.debby.mybatis.criteria.Restrictions.*;
import com.debby.mybatis.criteria.criterion.combined.CombinedCriterion;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.annotation.Retention;

/**
 * @author rocky.hu
 * @date 2018-01-25 10:35 PM
 */
public class RestrctionsTest {

    @Test
    public void testOr_Single_SimpleCriterion() {
        CombinedCriterion combinedCriterion = or(eq("A", "a"));
        Assert.assertEquals(combinedCriterion.toSqlString()," OR A = #{criterions[0].value}");
    }

    @Test
    public void testOr_Multiple_SimpleCriterion() {
        CombinedCriterion combinedCriterion = or(eq("A", "a"), like("B", "b"));
        Assert.assertEquals(combinedCriterion.toSqlString()," OR A = #{criterions[0].value} OR B like #{criterions[0].value}");
    }

    @Test
    public void testAnd_Single_SimpleCriterion() {
        CombinedCriterion combinedCriterion = and(eq("A", "a"));
        Assert.assertEquals(combinedCriterion.toSqlString()," AND A = #{criterions[0].value}");
    }

}

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

import com.debby.mybatis.criteria.criterion.Criterion;
import com.debby.mybatis.criteria.criterion.combined.CombinedCriterion;
import com.debby.mybatis.criteria.criterion.combined.CombinedCriterionConnector;
import com.debby.mybatis.criteria.criterion.simple.*;
import com.debby.mybatis.criteria.criterion.simple.mode.MatchMode;
import com.debby.mybatis.sql.SQLComparisonOperator;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author rocky.hu
 * @date 2018-01-25 9:31 PM
 */
public class CombinedCriterionTest {

    @Test
    public void test1() {
        List<String> inValues = new ArrayList<String>(Arrays.asList("a", "b", "c"));

        BetweenCriterion betweenCriterion = new BetweenCriterion("a", 1, 2);
        NotBetweenCriterion notBetweenCriterion = new NotBetweenCriterion("a", 1, 2);
        ComparisonCriterion eq = new ComparisonCriterion("a", "1", SQLComparisonOperator.eq);
        ComparisonCriterion ne = new ComparisonCriterion("a", "1", SQLComparisonOperator.ne);
        ComparisonCriterion gt = new ComparisonCriterion("a", "1", SQLComparisonOperator.gt);
        ComparisonCriterion lt = new ComparisonCriterion("a", "1", SQLComparisonOperator.lt);
        ComparisonCriterion le = new ComparisonCriterion("a", "1", SQLComparisonOperator.le);
        ComparisonCriterion ge = new ComparisonCriterion("a", "1", SQLComparisonOperator.ge);
        InCriterion inCriterion = new InCriterion("a", inValues);
        NotInCriterion notInCriterion = new NotInCriterion("a", inValues);
        LikeCriterion likeCriterion = new LikeCriterion("a", "a", MatchMode.ANYWHERE);
        NotLikeCriterion notLikeCriterion = new NotLikeCriterion("a", "a", MatchMode.ANYWHERE);
        NullCriterion nullCriterion = new NullCriterion("a");
        NotNullCriterion notNullCriterion = new NotNullCriterion("a");

        Criterion[] criterions = new Criterion[14];
        criterions[0] = betweenCriterion;
        criterions[1] = notBetweenCriterion;
        criterions[2] = eq;
        criterions[3] = ne;
        criterions[4] = gt;
        criterions[5] = lt;
        criterions[6] = le;
        criterions[7] = ge;
        criterions[8] = inCriterion;
        criterions[9] = notInCriterion;
        criterions[10] = likeCriterion;
        criterions[11] = notLikeCriterion;
        criterions[12] = nullCriterion;
        criterions[13] = notNullCriterion;

        CombinedCriterion combinedCriterion = new CombinedCriterion(CombinedCriterionConnector.AND, criterions);
        System.out.println(combinedCriterion.toSqlString());
    }

    @Test
    public void test2() {

        Criterion[] c = new Criterion[2];
        CombinedCriterion cc = new CombinedCriterion(CombinedCriterionConnector.AND, c);


        Criterion[] c1 = new Criterion[2];
        c1[0] = new ComparisonCriterion("a", "1", SQLComparisonOperator.eq);
        c1[1] = new ComparisonCriterion("a", "1", SQLComparisonOperator.ne);
        CombinedCriterion cc1 = new CombinedCriterion(CombinedCriterionConnector.OR, c1);
    }

    @Test
    public void testOr_Single() {

    }

}

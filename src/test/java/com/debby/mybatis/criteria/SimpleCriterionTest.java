package com.debby.mybatis.criteria;

import com.debby.mybatis.criteria.criterion.simple.*;
import com.debby.mybatis.criteria.criterion.simple.mode.MatchMode;
import com.debby.mybatis.sql.SQLComparisonOperator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rocky.hu
 * @date Jan 25, 2018 5:55:40 PM
 */
public class SimpleCriterionTest {

	@Test
	public void testBetweenCriterion() {
		BetweenCriterion betweenCriterion = new BetweenCriterion("a", 1, 2);
		Assert.assertEquals(betweenCriterion.toSqlString(), "(a BETWEEN #{criterions[0].value[0]} AND #{criterions[0].value[1]})");
	}

	@Test
	public void testNotBetweenCriterion() {
		NotBetweenCriterion notBetweenCriterion = new NotBetweenCriterion("a", 1, 2);
		Assert.assertEquals(notBetweenCriterion.toSqlString(), "(a NOT BETWEEN #{criterions[0].value[0]} AND #{criterions[0].value[1]})");
	}

	@Test
	public void testComparisonCriterion_eq() {
		ComparisonCriterion eq = new ComparisonCriterion("a", "1", SQLComparisonOperator.eq);
		Assert.assertEquals(eq.toSqlString(),"a = #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_ne() {
		ComparisonCriterion ne = new ComparisonCriterion("a", "1", SQLComparisonOperator.ne);
		Assert.assertEquals(ne.toSqlString(),"a <> #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_gt() {
		ComparisonCriterion gt = new ComparisonCriterion("a", "1", SQLComparisonOperator.gt);
		Assert.assertEquals(gt.toSqlString(),"a > #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_lt() {
		ComparisonCriterion lt = new ComparisonCriterion("a", "1", SQLComparisonOperator.lt);
		Assert.assertEquals(lt.toSqlString(),"a < #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_le() {
		ComparisonCriterion le = new ComparisonCriterion("a", "1", SQLComparisonOperator.le);
		Assert.assertEquals(le.toSqlString(),"a <= #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_ge() {
		ComparisonCriterion ge = new ComparisonCriterion("a", "1", SQLComparisonOperator.ge);
		Assert.assertEquals(ge.toSqlString(),"a >= #{criterions[0].value}");
	}

	@Test
	public void testInCriterion() {
		List<String> values = new ArrayList<String>();
		values.add("a");
		values.add("b");
		values.add("c");
		InCriterion inCriterion = new InCriterion("a", values);
		Assert.assertEquals(inCriterion.toSqlString(),"(a IN (#{criterions[0].value.get(0)}, #{criterions[0].value.get(1)}, #{criterions[0].value.get(2)}))");
	}

	@Test
	public void testNotInCriterion() {
		List<String> values = new ArrayList<String>();
		values.add("a");
		values.add("b");
		values.add("c");
		NotInCriterion notInCriterion = new NotInCriterion("a", values);
		Assert.assertEquals(notInCriterion.toSqlString(),"(a NOT IN (#{criterions[0].value.get(0)}, #{criterions[0].value.get(1)}, #{criterions[0].value.get(2)}))");
	}

	@Test
	public void testLikeCriterion() {
		LikeCriterion likeCriterion = new LikeCriterion("a", "a", MatchMode.ANYWHERE);
		Assert.assertEquals(likeCriterion.toSqlString(),"a LIKE #{criterions[0].value}");
	}

	@Test
	public void testNotLikeCriterion() {
		NotLikeCriterion notLikeCriterion = new NotLikeCriterion("a", "a", MatchMode.ANYWHERE);
		Assert.assertEquals(notLikeCriterion.toSqlString(),"a NOT LIKE #{criterions[0].value}");
	}
	
	@Test
	public void testNullCriterion() {
		NullCriterion nullCriterion = new NullCriterion("a");
		Assert.assertEquals(nullCriterion.toSqlString(),"a IS NULL");
	}

	@Test
	public void testNotNullCriterion() {
		NotNullCriterion notNullCriterion = new NotNullCriterion("a");
		Assert.assertEquals(notNullCriterion.toSqlString(),"a IS NOT NULL");
	}

}

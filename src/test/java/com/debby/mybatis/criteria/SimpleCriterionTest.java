package com.debby.mybatis.criteria;

import com.debby.mybatis.core.helper.EntityHelper;
import mockit.Mock;
import mockit.MockUp;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.debby.mybatis.criteria.criterion.BetweenCriterion;
import com.debby.mybatis.criteria.criterion.ComparisonCriterion;
import com.debby.mybatis.criteria.criterion.InCriterion;
import com.debby.mybatis.criteria.criterion.LikeCriterion;
import com.debby.mybatis.criteria.criterion.MatchMode;
import com.debby.mybatis.criteria.criterion.NotBetweenCriterion;
import com.debby.mybatis.criteria.criterion.NotInCriterion;
import com.debby.mybatis.criteria.criterion.NotLikeCriterion;
import com.debby.mybatis.criteria.criterion.NotNullCriterion;
import com.debby.mybatis.criteria.criterion.NullCriterion;
import com.debby.mybatis.sql.SQLComparisonOperator;

/**
 * @author rocky.hu
 * @date Jan 25, 2018 5:55:40 PM
 */
public class SimpleCriterionTest {

	@BeforeMethod
	public void init() {

		new MockUp<EntityHelper>() {

			@Mock
			public String getColumn(Class<?> entityType, String property) {
				return "id";
			}

			@Mock
			public String getTypeHandler(Class<?> entityType, String property) {
				return null;
			}

		};

	}

	@Test
	public void testBetweenCriterion() {
		BetweenCriterion betweenCriterion = new BetweenCriterion("id", 1, 2);
		Assert.assertEquals(betweenCriterion.toSqlString(Book.class), "(id BETWEEN #{criterions[0].value[0]} AND #{criterions[0].value[1]})");
	}

	@Test
	public void testNotBetweenCriterion() {
		NotBetweenCriterion notBetweenCriterion = new NotBetweenCriterion("id", 1, 2);
		Assert.assertEquals(notBetweenCriterion.toSqlString(Book.class), "(id NOT BETWEEN #{criterions[0].value[0]} AND #{criterions[0].value[1]})");
	}

	@Test
	public void testComparisonCriterion_eq() {
		ComparisonCriterion eq = new ComparisonCriterion("id", "1", SQLComparisonOperator.eq);
		Assert.assertEquals(eq.toSqlString(Book.class),"id = #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_ne() {
		ComparisonCriterion ne = new ComparisonCriterion("id", "1", SQLComparisonOperator.ne);
		Assert.assertEquals(ne.toSqlString(Book.class),"id <> #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_gt() {
		ComparisonCriterion gt = new ComparisonCriterion("id", "1", SQLComparisonOperator.gt);
		Assert.assertEquals(gt.toSqlString(Book.class),"id > #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_lt() {
		ComparisonCriterion lt = new ComparisonCriterion("id", "1", SQLComparisonOperator.lt);
		Assert.assertEquals(lt.toSqlString(Book.class),"id < #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_le() {
		ComparisonCriterion le = new ComparisonCriterion("id", "1", SQLComparisonOperator.le);
		Assert.assertEquals(le.toSqlString(Book.class),"id <= #{criterions[0].value}");
	}

	@Test
	public void testComparisonCriterion_ge() {
		ComparisonCriterion ge = new ComparisonCriterion("id", "1", SQLComparisonOperator.ge);
		Assert.assertEquals(ge.toSqlString(Book.class),"id >= #{criterions[0].value}");
	}

	@Test
	public void testInCriterion() {
		InCriterion inCriterion = new InCriterion("id", new String[] {"a","b","c"});
		Assert.assertEquals(inCriterion.toSqlString(Book.class),"(id IN (#{criterions[0].value[0]}, #{criterions[0].value[1]}, #{criterions[0].value[2]}))");
	}

	@Test
	public void testNotInCriterion() {
		NotInCriterion notInCriterion = new NotInCriterion("id", new String[] {"a","b","c"});
		Assert.assertEquals(notInCriterion.toSqlString(Book.class),"(id NOT IN (#{criterions[0].value[0]}, #{criterions[0].value[1]}, #{criterions[0].value[2]}))");
	}

	@Test
	public void testLikeCriterion() {
		LikeCriterion likeCriterion = new LikeCriterion("id", "a", MatchMode.ANYWHERE);
		Assert.assertEquals(likeCriterion.toSqlString(Book.class),"id LIKE #{criterions[0].value}");
	}

	@Test
	public void testNotLikeCriterion() {
		NotLikeCriterion notLikeCriterion = new NotLikeCriterion("id", "a", MatchMode.ANYWHERE);
		Assert.assertEquals(notLikeCriterion.toSqlString(Book.class),"id NOT LIKE #{criterions[0].value}");
	}
	
	@Test
	public void testNullCriterion() {
		NullCriterion nullCriterion = new NullCriterion("id");
		Assert.assertEquals(nullCriterion.toSqlString(Book.class),"id IS NULL");
	}

	@Test
	public void testNotNullCriterion() {
		NotNullCriterion notNullCriterion = new NotNullCriterion("id");
		Assert.assertEquals(notNullCriterion.toSqlString(Book.class),"id IS NOT NULL");
	}

}

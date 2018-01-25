package com.debby.mybatis.criteria;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.criteria.criterion.simple.NullCriterion;

/**
 * @author rocky.hu
 * @date Jan 25, 2018 5:55:40 PM
 */
public class SimpleCriterionTest {
	
	@Test
	public void testNullCriterion() {
		NullCriterion nullCriterion = new NullCriterion("a");
		Assert.assertEquals("a IS NULL", nullCriterion.toSqlString());
	}

}

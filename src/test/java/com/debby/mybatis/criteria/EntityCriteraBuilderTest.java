package com.debby.mybatis.criteria;

import org.testng.annotations.Test;

import com.debby.mybatis.association.Product;

/**
 * @author rocky.hu
 * @date Jan 25, 2018 4:12:05 PM
 */
public class EntityCriteraBuilderTest {

	@Test
	public void test() {
		EntityCriteriaBuilder entityCriteraBuilder = EntityCriteria.forEntity(Product.class);
	}

}

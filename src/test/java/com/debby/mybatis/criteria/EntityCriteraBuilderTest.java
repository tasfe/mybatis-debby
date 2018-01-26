package com.debby.mybatis.criteria;

import org.testng.annotations.Test;

import com.debby.mybatis.association.Product;
import com.debby.mybatis.criteria.criterion.Restrictions;

/**
 * @author rocky.hu
 * @date Jan 25, 2018 4:12:05 PM
 */
public class EntityCriteraBuilderTest {

	@Test
	public void test() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.eq("id", 1)).build();
		System.out.println(entityCriteria.getWhereSql());
	}

}

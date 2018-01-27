package com.debby.mybatis.criteria;

import com.debby.mybatis.association.Product;
import com.debby.mybatis.core.helper.EntityHelper;
import com.debby.mybatis.criteria.criterion.Conjunction;
import com.debby.mybatis.criteria.criterion.Disjunction;
import com.debby.mybatis.criteria.criterion.MatchMode;
import com.debby.mybatis.criteria.criterion.Restrictions;
import mockit.Mock;
import mockit.MockUp;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author rocky.hu
 * @date Jan 25, 2018 4:12:05 PM
 */
public class EntityCriteraBuilderTest {

	@BeforeMethod
	public void init() {

		new MockUp<EntityHelper>() {

			@Mock
			public String getColumns(Class<?> entityType, boolean exclude, List<String> properties) {
				return "id";
			}

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
	public void test_ComparisonCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.eq("id", 1)).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 1);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), 1);
		Assert.assertEquals(entityCriteria.getWhereSql(), "id = #{criterions[0].value}");
	}

	@Test
	public void test_BetweenCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.between("id", 1 ,3)).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 1);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), new Object[]{1,3});
		Assert.assertEquals(entityCriteria.getWhereSql(), "(id BETWEEN #{criterions[0].value[0]} AND #{criterions[0].value[1]})");
	}

	@Test
	public void test_NotBetweenCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.notBetween("id", 1 ,3)).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 1);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), new Object[]{1,3});
		Assert.assertEquals(entityCriteria.getWhereSql(), "(id NOT BETWEEN #{criterions[0].value[0]} AND #{criterions[0].value[1]})");
	}

	@Test
	public void test_InCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.in("id", 1, 2, 3)).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 1);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), new Object[]{1,2,3});
		Assert.assertEquals(entityCriteria.getWhereSql(), "(id IN (#{criterions[0].value[0]}, #{criterions[0].value[1]}, #{criterions[0].value[2]}))");
	}

	@Test
	public void test_NotInCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.notIn("id", 1, 2, 3)).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 1);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), new Object[]{1,2,3});
		Assert.assertEquals(entityCriteria.getWhereSql(), "(id NOT IN (#{criterions[0].value[0]}, #{criterions[0].value[1]}, #{criterions[0].value[2]}))");
	}

	@Test
	public void test_LikeCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.like("id", "a")).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 1);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), "a");
		Assert.assertEquals(entityCriteria.getWhereSql(), "id LIKE #{criterions[0].value}");
	}

	@Test
	public void test_NotLikeCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.notLike("id", "a")).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 1);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), "a");
		Assert.assertEquals(entityCriteria.getWhereSql(), "id NOT LIKE #{criterions[0].value}");
	}

	@Test
	public void test_NullCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.isNull("id")).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 1);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), null);
		Assert.assertEquals(entityCriteria.getWhereSql(), "id IS NULL");
	}

	@Test
	public void test_NotNullCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.isNotNull("id")).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 1);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), null);
		Assert.assertEquals(entityCriteria.getWhereSql(), "id IS NOT NULL");
	}

	@Test
	public void test_AND_LogicalCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.and(Restrictions.eq("id", 2), Restrictions.between("id", 1, 3))).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 2);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getIndex(), 1);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getValue(), new Object[]{1,3});
		Assert.assertEquals(entityCriteria.getWhereSql(), "(id = #{criterions[0].value} AND (id BETWEEN #{criterions[1].value[0]} AND #{criterions[1].value[1]}))");
	}

	@Test
	public void test_OR_LogicalCriterion() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.or(Restrictions.eq("id", 2), Restrictions.between("id", 1, 3))).build();
		Assert.assertEquals(entityCriteria.getCriterions().length, 2);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getIndex(), 1);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getValue(), new Object[]{1,3});
		Assert.assertEquals(entityCriteria.getWhereSql(), "(id = #{criterions[0].value} OR (id BETWEEN #{criterions[1].value[0]} AND #{criterions[1].value[1]}))");
	}

	@Test
	public void test_Conjunction() {
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("id", 2));
		conjunction.add(Restrictions.like("id", "a", MatchMode.ANYWHERE));
		conjunction.add(Restrictions.between("id", 1, 3));

		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(conjunction).build();

		Assert.assertEquals(entityCriteria.getCriterions().length, 3);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getIndex(), 1);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getValue(), "%a%");
		Assert.assertEquals(entityCriteria.getCriterions()[2].getIndex(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[2].getValue(), new Object[]{1,3});
		Assert.assertEquals(entityCriteria.getWhereSql(), "(id = #{criterions[0].value} AND id LIKE #{criterions[1].value} AND (id BETWEEN #{criterions[2].value[0]} AND #{criterions[2].value[1]}))");
	}

	@Test
	public void test_Disjunction() {
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.eq("id", 2));
		disjunction.add(Restrictions.like("id", "a", MatchMode.ANYWHERE));
		disjunction.add(Restrictions.between("id", 1, 3));

		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(disjunction).build();

		Assert.assertEquals(entityCriteria.getCriterions().length, 3);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getIndex(), 1);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getValue(), "%a%");
		Assert.assertEquals(entityCriteria.getCriterions()[2].getIndex(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[2].getValue(), new Object[]{1,3});
		Assert.assertEquals(entityCriteria.getWhereSql(), "(id = #{criterions[0].value} OR id LIKE #{criterions[1].value} OR (id BETWEEN #{criterions[2].value[0]} AND #{criterions[2].value[1]}))");
	}

	@Test
	public void test_Junction() {
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.eq("id", 2));
		disjunction.add(Restrictions.like("id", "a", MatchMode.ANYWHERE));
		disjunction.add(Restrictions.between("id", 1, 3));

		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.le("id", 4));
		conjunction.add(Restrictions.like("id", "a", MatchMode.START));
		conjunction.add(Restrictions.in("id", 5, 6, 7));

		disjunction.add(conjunction);

		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(disjunction).build();

		Assert.assertEquals(entityCriteria.getCriterions().length, 6);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getIndex(), 1);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getValue(), "%a%");
		Assert.assertEquals(entityCriteria.getCriterions()[2].getIndex(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[2].getValue(), new Object[]{1,3});
		Assert.assertEquals(entityCriteria.getCriterions()[3].getIndex(), 3);
		Assert.assertEquals(entityCriteria.getCriterions()[3].getValue(), 4);
		Assert.assertEquals(entityCriteria.getCriterions()[4].getIndex(), 4);
		Assert.assertEquals(entityCriteria.getCriterions()[4].getValue(), "a%");
		Assert.assertEquals(entityCriteria.getCriterions()[5].getIndex(), 5);
		Assert.assertEquals(entityCriteria.getCriterions()[5].getValue(), new Object[]{5, 6, 7});
		Assert.assertEquals(entityCriteria.getWhereSql(), "(id = #{criterions[0].value} OR id LIKE #{criterions[1].value} OR (id BETWEEN #{criterions[2].value[0]} AND #{criterions[2].value[1]}) OR (id <= #{criterions[3].value} AND id LIKE #{criterions[4].value} AND (id IN (#{criterions[5].value[0]}, #{criterions[5].value[1]}, #{criterions[5].value[2]}))))");
	}

	/**
	 * Complex Queries
	 */

	@Test
	public void test_Complex_Query_1() {

		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.like("id", "a", MatchMode.ANYWHERE));
		disjunction.add(Restrictions.gt("id", 2));

		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("id", "b", MatchMode.END));
		conjunction.add(Restrictions.isNull("id"));

		EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
				.where(Restrictions.eq("id", 1), disjunction, conjunction).build();

		Assert.assertEquals(entityCriteria.getCriterions().length, 5);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getIndex(), 0);
		Assert.assertEquals(entityCriteria.getCriterions()[0].getValue(), 1);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getIndex(), 1);
		Assert.assertEquals(entityCriteria.getCriterions()[1].getValue(), "%a%");
		Assert.assertEquals(entityCriteria.getCriterions()[2].getIndex(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[2].getValue(), 2);
		Assert.assertEquals(entityCriteria.getCriterions()[3].getIndex(), 3);
		Assert.assertEquals(entityCriteria.getCriterions()[3].getValue(), "%b");
		Assert.assertEquals(entityCriteria.getCriterions()[4].getIndex(), 4);
		Assert.assertEquals(entityCriteria.getCriterions()[4].getValue(), null);

		System.out.println(entityCriteria.getWhereSql());
	}

}

package com.debby.mybatis.association;

import com.debby.mybatis.AbstractMyBatisDebbyMapperTest;
import com.debby.mybatis.criteria.EntityCriteria;
import com.debby.mybatis.criteria.criterion.Conjunction;
import com.debby.mybatis.criteria.criterion.Disjunction;
import com.debby.mybatis.criteria.criterion.MatchMode;
import com.debby.mybatis.criteria.criterion.Restrictions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class ProductMapperTest extends AbstractMyBatisDebbyMapperTest<ProductMapper> {
	
	public ProductMapperTest() {
		super();
		this.setDdlPath("/data/ddl/product.ddl");
		this.setDataXmlPath("/data/product.xml");
	}
	
	@Test
    @Override
    public void testInsert() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1);

        Product product = new Product();
        product.setCreateTime(new Date());
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(100);
        product.setSoldOut(false);
        product.setTitle("p4");
        product.setWeight(10.0);
        product.setPm(Product.PM.BIG);
        product.setProductCategory(productCategory);

        mapper.insert(product);

        Assert.assertNotNull(product.getId());
        Assert.assertEquals(product.getId().intValue(), 4);
    }

	@Test
    @Override
    public void testInsertSelective() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1);

        Product product = new Product();
        product.setCreateTime(new Date());
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(100);
        product.setSoldOut(false);
        product.setWeight(10.0);
        product.setPm(Product.PM.BIG);
        product.setProductCategory(productCategory);

        mapper.insertSelective(product);

        Assert.assertNotNull(product.getId());
        Assert.assertEquals(product.getId().intValue(), 4);

        // use the default value for 'title'
        product = mapper.selectById(4);
        Assert.assertNotNull(product);
        Assert.assertEquals(product.getTitle(), "test");
    }

    @Test
    @Override
    public void testUpdateById() {
    	
        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        mapper.updateById(product);

        product = mapper.selectById(1);
        Assert.assertNull(product.getCreateTime());
    }

    @Test
    @Override
    public void testUpdateByIdSelective() {
    	
        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        mapper.updateByIdSelective(product);

        product = mapper.selectById(1);
        Assert.assertNotNull(product.getCreateTime());
    }

    @Test
    @Override
    public void testUpdate() {
    }

    @Test
    @Override
    public void testSelectById() {
        Product product = mapper.selectById(1);
        Assert.assertEquals(product.getTitle(), "p1");
    }
    
    @Test
	@Override
	public void testSelectOne() {

        EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
                .where(Restrictions.eq("title", "p1")).build();
		
		Product product = mapper.selectOne(entityCriteria);
		Assert.assertNotNull(product);
		Assert.assertEquals(product.getTitle(), "p1");
	}

    @Test
    @Override
    public void testSelectList() {
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.eq("id", 2));
        disjunction.add(Restrictions.like("title", "a", MatchMode.ANYWHERE));
        disjunction.add(Restrictions.between("id", 1, 3));

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.le("id", 4));
        conjunction.add(Restrictions.like("title", "a", MatchMode.START));
        conjunction.add(Restrictions.in("id", 5, 6, 7));

        disjunction.add(conjunction);

        EntityCriteria entityCriteria = EntityCriteria.forEntity(Product.class)
                .where(disjunction).build();

        List<Product> result = mapper.selectList(entityCriteria);

    }

    @Test
    public void testTestVarargs() {
        Product product = mapper.testVarargs(1);
        Assert.assertEquals(product.getTitle(), "p1");
    }

}

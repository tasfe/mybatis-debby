package com.debby.mybatis.normal;

import java.math.BigDecimal;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.DebbyMapperTest;
import com.debby.mybatis.criteria.EntityCriteria;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class ProductMapperTest extends DebbyMapperTest<ProductMapper> {
	
	public ProductMapperTest() {
		super();
		this.setDdlPath("/assets/normal/product.ddl");
		this.setDataXmlPath("/assets/normal/product.xml");
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
        product = mapper.selectByPrimaryKey(4);
        Assert.assertNotNull(product);
        Assert.assertEquals(product.getTitle(), "test");
    }

    @Test
    @Override
    public void testUpdateByPrimaryKey() {
    	
        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        mapper.updateByPrimaryKey(product);

        product = mapper.selectByPrimaryKey(1);
        Assert.assertNull(product.getCreateTime());
    }

    @Test
    @Override
    public void testUpdateByPrimaryKeySelective() {
    	
        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        mapper.updateByPrimaryKeySelective(product);

        product = mapper.selectByPrimaryKey(1);
        Assert.assertNotNull(product.getCreateTime());
    }

    @Test
    @Override
    public void testUpdateByCriteria() {
        
        Product product = new Product();
        product.setQuantity(101);;

        EntityCriteria entityCriteria = new EntityCriteria(Product.class);
    }

    @Test
    @Override
    public void testSelectByPrimaryKey() {
        Product product = mapper.selectByPrimaryKey(1);
        Assert.assertEquals(product.getTitle(), "p1");
    }

}

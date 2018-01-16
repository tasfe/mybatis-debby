package com.debby.mybatis.spring;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.debby.mybatis.DBUnitHelper;
import com.debby.mybatis.normal.Product;
import com.debby.mybatis.normal.ProductCategory;
import com.debby.mybatis.normal.ProductMapper;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class ProductMapperTest {
	
	private DBUnitHelper dbUnitHelper = new DBUnitHelper();
	private ProductMapper mapper = null;
	
	public ProductMapperTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		mapper = (ProductMapper) context.getBean("productMapper");
		((ConfigurableApplicationContext)context).close();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		dbUnitHelper.createTableFromFile("/assets/normal/product.ddl");
		dbUnitHelper.executeDatasetAsRefresh("/assets/normal/product.xml");
	}
	
	@Test
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
    public void testUpdateByPrimaryKey() {
    	
        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        mapper.updateByPrimaryKey(product);

        product = mapper.selectByPrimaryKey(1);
        Assert.assertNull(product.getCreateTime());
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
    	
        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        mapper.updateByPrimaryKeySelective(product);

        product = mapper.selectByPrimaryKey(1);
        Assert.assertNotNull(product.getCreateTime());
    }

    @Test
    public void testUpdateByCriteria() {
    }

    @Test
    public void testSelectByPrimaryKey() {
        Product product = mapper.selectByPrimaryKey(1);
        Assert.assertEquals(product.getTitle(), "p1");
    }

    @Test
    public void testTestVarargs() {
        Product product = mapper.testVarargs(1);
        Assert.assertEquals(product.getTitle(), "p1");
    }

}

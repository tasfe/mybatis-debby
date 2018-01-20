package com.debby.mybatis.spring;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.debby.mybatis.DBUnitHelper;
import com.debby.mybatis.association.Product;
import com.debby.mybatis.association.ProductCategory;
import com.debby.mybatis.association.ProductMapper;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class ProductMapperTest {
	
	private DBUnitHelper dbUnitHelper = new DBUnitHelper();
	private ProductMapper mapper = null;
	private PlatformTransactionManager transactionManager;

    public ProductMapperTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		mapper = (ProductMapper) context.getBean("productMapper");
        transactionManager = (PlatformTransactionManager) context.getBean("transactionManager");
		((ConfigurableApplicationContext)context).close();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		dbUnitHelper.createTableFromFile("/data/ddl/product.ddl");
		dbUnitHelper.executeDatasetAsRefresh("/data/product.xml");
	}
	
	@Test
    public void testInsert() {

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

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

        transactionManager.commit(status);

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
        product = mapper.selectById(4);
        Assert.assertNotNull(product);
        Assert.assertEquals(product.getTitle(), "test");
    }

    @Test
    public void testUpdateByPrimaryKey() {
    	
        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        mapper.updateById(product);

        product = mapper.selectById(1);
        Assert.assertNull(product.getCreateTime());
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {

        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        mapper.updateByIdSelective(product);

        product = mapper.selectById(1);
        Assert.assertNotNull(product.getCreateTime());
    }

    @Test
    public void testUpdateByCriteria() {
    }

    @Test
    public void testSelectByPrimaryKey() {
        Product product = mapper.selectById(1);
        Assert.assertEquals(product.getTitle(), "p1");
    }

    @Test
    public void testTestVarargs() {
        Product product = mapper.testVarargs(1);
        Assert.assertEquals(product.getTitle(), "p1");
    }

}

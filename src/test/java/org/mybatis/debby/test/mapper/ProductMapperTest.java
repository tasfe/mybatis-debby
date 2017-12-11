package org.mybatis.debby.test.mapper;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.debby.criteria.EntityCriteria;
import org.mybatis.debby.test.entity.Product;
import org.mybatis.debby.test.entity.ProductCategory;
import org.mybatis.debby.test.helper.DBUnitHelper;
import org.mybatis.debby.test.helper.MyBatisHelper;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class ProductMapperTest {

    private SqlSession sqlSession;
    private DBUnitHelper dbUnitHelper = new DBUnitHelper();

    @BeforeMethod
    public void setUp() {
        dbUnitHelper.createTableFromFile("/data/db.ddl");
        sqlSession = MyBatisHelper.getSqlSessionFactory().openSession(true);
    }

    @AfterMethod
    public void tearDown() {
        sqlSession.close();
    }

    @Test
    public void test_insert() {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1);

        Product product = new Product();
        product.setCreateTime(new Date());
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(100);
        product.setSoldOut(false);
        product.setTitle("p1");
        product.setWeight(10.0);
        product.setPm(Product.PM.BIG);
        product.setProductCategory(productCategory);

        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.insert(product);

        Assert.assertNotNull(product.getId());
        Assert.assertEquals(product.getId().intValue(), 1);
    }

    @Test
    public void test_insertSelective() {

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

        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.insertSelective(product);

        Assert.assertNotNull(product.getId());
        Assert.assertEquals(product.getId().intValue(), 1);

        // use the default value for 'title'
        product = productMapper.selectByPrimaryKey(1);
        Assert.assertNotNull(product);
        Assert.assertEquals(product.getTitle(), "test1");
    }

    @Test
    public void test_updateByPrimaryKey() {
        dbUnitHelper.executeDatasetAsRefresh("/data/db.xml");

        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.updateByPrimaryKey(product);

        product = productMapper.selectByPrimaryKey(1);
        Assert.assertNull(product.getCreateTime());
    }

    @Test
    public void test_updateByPrimaryKeySelective() {
        dbUnitHelper.executeDatasetAsRefresh("/data/db.xml");

        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.updateByPrimaryKeySelective(product);

        product = productMapper.selectByPrimaryKey(1);
        Assert.assertNotNull(product.getCreateTime());
    }

    @Test
    public void test_updateByCriteria() {
        dbUnitHelper.executeDatasetAsRefresh("/data/db.xml");

        Product product = new Product();
        product.setQuantity(101);;

        EntityCriteria entityCriteria = new EntityCriteria(Product.class);
    }

}

package com.debby.mybatis.mapper;

import com.debby.mybatis.criteria.EntityCriteria;
import com.debby.mybatis.entity.Product;
import com.debby.mybatis.entity.ProductCategory;
import com.debby.mybatis.helper.DBUnitHelper;
import com.debby.mybatis.helper.MyBatisHelper;
import org.apache.ibatis.session.SqlSession;
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
public class ProductMapperTest implements DebbyMapperTest {

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

    @Override
    public void testInsert() {
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

        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.insertSelective(product);

        Assert.assertNotNull(product.getId());
        Assert.assertEquals(product.getId().intValue(), 1);

        // use the default value for 'title'
        product = productMapper.selectByPrimaryKey(1);
        Assert.assertNotNull(product);
        Assert.assertEquals(product.getTitle(), "test1");
    }

    @Override
    public void testUpdateByPrimaryKey() {
        dbUnitHelper.executeDatasetAsRefresh("/data/db.xml");

        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.updateByPrimaryKey(product);

        product = productMapper.selectByPrimaryKey(1);
        Assert.assertNull(product.getCreateTime());
    }

    @Override
    public void testUpdateByPrimaryKeySelective() {
        dbUnitHelper.executeDatasetAsRefresh("/data/db.xml");

        Product product = new Product();
        product.setId(1);
        product.setTitle("p1-1");

        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.updateByPrimaryKeySelective(product);

        product = productMapper.selectByPrimaryKey(1);
        Assert.assertNotNull(product.getCreateTime());
    }

    @Override
    public void testUpdateByCriteria() {
        dbUnitHelper.executeDatasetAsRefresh("/data/db.xml");

        Product product = new Product();
        product.setQuantity(101);;

        EntityCriteria entityCriteria = new EntityCriteria(Product.class);
    }

    @Override
    public void testUpdateByCriteriaSelective() {

    }

    @Test
    @Override
    public void testSelectByPrimaryKey() {
        dbUnitHelper.executeDatasetAsRefresh("/data/db.xml");

        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        Product product = productMapper.selectByPrimaryKey(1);
        Assert.assertEquals(product.getTitle(), "p1");
    }

    @Override
    public void testSelectByCriteria() {

    }

    @Override
    public void testSelectCountByCriteria() {

    }

    @Override
    public void testDeleteByPrimaryKey() {

    }

    @Override
    public void testDeleteByCriteria() {

    }

}

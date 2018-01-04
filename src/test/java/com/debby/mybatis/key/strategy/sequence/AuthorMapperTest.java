package com.debby.mybatis.key.strategy.sequence;

import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.debby.mybatis.DBUnitHelper;
import com.debby.mybatis.DebbyMapperTest;
import com.debby.mybatis.MyBatisHelper;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class AuthorMapperTest implements DebbyMapperTest {

    private SqlSession sqlSession;
    private DBUnitHelper dbUnitHelper = new DBUnitHelper();

    @BeforeMethod
    public void setUp() {
        dbUnitHelper.createTableFromFile("/assets/key/strategy/sequence/author.ddl");
        sqlSession = MyBatisHelper.getSqlSessionFactory().openSession(true);
    }

    @AfterMethod
    public void tearDown() {
        sqlSession.close();
    }

    @Test
    @Override
    public void testInsert() {
    	
    	Author author = new Author();
    	author.setAge(28);
    	author.setName("rocky");
    	
        AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
        authorMapper.insert(author);
        
        Assert.assertNotNull(author.getId());
        Assert.assertEquals(author.getId().intValue(), 1);
    }

    @Override
    public void testInsertSelective() {

    }

    @Override
    public void testUpdateByPrimaryKey() {

    }

    @Override
    public void testUpdateByPrimaryKeySelective() {

    }

    @Override
    public void testUpdateByCriteria() {

    }

    @Override
    public void testUpdateByCriteriaSelective() {

    }

    @Test
    @Override
    public void testSelectByPrimaryKey() {
        dbUnitHelper.executeDatasetAsRefresh("/assets/key/strategy/sequence/author.xml");
        AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
        Author author = authorMapper.selectByPrimaryKey(1l);
        Assert.assertEquals(author.getName(), "m1");
        Assert.assertEquals(author.getAge(), 20);
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

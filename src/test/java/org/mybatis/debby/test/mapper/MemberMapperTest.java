package org.mybatis.debby.test.mapper;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.debby.test.entity.Member;
import org.mybatis.debby.test.entity.MemberPK;
import org.mybatis.debby.test.entity.Product;
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
public class MemberMapperTest implements DebbyMapperTest {

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
        dbUnitHelper.executeDatasetAsRefresh("/data/db.xml");
        MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);

        MemberPK memberPK = new MemberPK();
        memberPK.setId(1);
        memberPK.setName("m1");

        Member member = memberMapper.selectByPrimaryKey(memberPK);
        Assert.assertEquals(member.getAge(), 20);
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

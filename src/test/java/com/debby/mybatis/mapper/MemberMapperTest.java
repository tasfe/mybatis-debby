package com.debby.mybatis.mapper;

import com.debby.mybatis.entity.Member;
import com.debby.mybatis.entity.MemberPK;
import com.debby.mybatis.helper.DBUnitHelper;
import com.debby.mybatis.helper.MyBatisHelper;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

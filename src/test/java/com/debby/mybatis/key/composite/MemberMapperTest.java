package com.debby.mybatis.key.composite;

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
public class MemberMapperTest implements DebbyMapperTest {

    private SqlSession sqlSession;
    private DBUnitHelper dbUnitHelper = new DBUnitHelper();

    @BeforeMethod
    public void setUp() {
        dbUnitHelper.createTableFromFile("/com/debby/mybatis/key/composite/member.ddl");
        sqlSession = MyBatisHelper.getSqlSessionFactory().openSession(true);
    }

    @AfterMethod
    public void tearDown() {
        sqlSession.close();
    }

    @Test
    @Override
    public void testInsert() {
    	MemberPK memberPk = new MemberPK();
    	memberPk.setName("rocky");
    	
    	Member member = new Member();
    	member.setAge(28);
    	member.setMemberPK(memberPk);
    	
        MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
        memberMapper.insert(member);
        
        Assert.assertNotNull(member.getMemberPK().getId());
        Assert.assertEquals(member.getMemberPK().getId().intValue(), 1);
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
        dbUnitHelper.executeDatasetAsRefresh("/com/debby/mybatis/key/composite/member.xml");
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

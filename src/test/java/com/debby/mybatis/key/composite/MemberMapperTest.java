package com.debby.mybatis.key.composite;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.AbstractMyBatisDebbyMapperTest;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class MemberMapperTest extends AbstractMyBatisDebbyMapperTest<MemberMapper> {
	
	public MemberMapperTest() {
		super();
		this.setDdlPath("/data/ddl/member.ddl");
		this.setDataXmlPath("/data/member.xml");
	}

    @Test
    @Override
    public void testInsert() {
    	MemberPK memberPk = new MemberPK();
    	memberPk.setName("rocky");
    	
    	Member member = new Member();
    	member.setAge(28);
    	member.setMemberPK(memberPk);
    	
        mapper.insert(member);
        
        Assert.assertNotNull(member.getMemberPK().getId());
        Assert.assertEquals(member.getMemberPK().getId().intValue(), 3);
    }

    @Test
    @Override
    public void testSelectById() {
    	
        MemberPK memberPK = new MemberPK();
        memberPK.setId(1);
        memberPK.setName("m1");

        Member member = mapper.selectById(memberPK);
        Assert.assertEquals(member.getAge(), 20);
    }
    

}

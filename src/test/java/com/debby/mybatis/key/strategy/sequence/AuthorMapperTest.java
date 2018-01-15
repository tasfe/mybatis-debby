package com.debby.mybatis.key.strategy.sequence;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.DebbyMapperTest;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class AuthorMapperTest extends DebbyMapperTest<AuthorMapper> {
	
	public AuthorMapperTest() {
		super();
		this.setDdlPath("/assets/key/strategy/sequence/author.ddl");
		this.setDataXmlPath("/assets/key/strategy/sequence/author.xml");
	}

    @Test
    @Override
    public void testInsert() {
    	
    	Author author = new Author();
    	author.setAge(28);
    	author.setName("rocky");
    	
        mapper.insert(author);
        
        Assert.assertNotNull(author.getId());
        Assert.assertEquals(author.getId().intValue(), 3);
    }

    @Test
    @Override
    public void testSelectByPrimaryKey() {
    	
        Author author = mapper.selectByPrimaryKey(1L);
        Assert.assertEquals(author.getName(), "m1");
        Assert.assertEquals(author.getAge(), 20);
    }

}

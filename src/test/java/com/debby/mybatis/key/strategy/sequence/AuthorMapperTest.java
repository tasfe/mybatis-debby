package com.debby.mybatis.key.strategy.sequence;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.AbstractDebbyMapperTest;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class AuthorMapperTest extends AbstractDebbyMapperTest<AuthorMapper> {
	
	public AuthorMapperTest() {
		super();
		this.setDdlPath("/data/ddl/author.ddl");
		this.setDataXmlPath("/data/author.xml");
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
    public void testSelectById() {
    	
        Author author = mapper.selectById(1L);
        Assert.assertEquals(author.getName(), "m1");
        Assert.assertEquals(author.getAge(), 20);
    }

}

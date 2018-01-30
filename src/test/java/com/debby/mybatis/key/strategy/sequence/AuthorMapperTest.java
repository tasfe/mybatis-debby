package com.debby.mybatis.key.strategy.sequence;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.AbstractMyBatisDebbyMapperTest;
import com.debby.mybatis.MyBatisHelper;

/**
 * @author rocky.hu
 * @date 2017-11-29 9:41 PM
 */
public class AuthorMapperTest extends AbstractMyBatisDebbyMapperTest<AuthorMapper> {
	
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
	public void testBatchInsert() {
    	
    	SqlSession sqlSession = MyBatisHelper.getSqlSessionFactory().openSession(ExecutorType.BATCH);
    	AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
    	
    	List<Author> authorList = new ArrayList<Author>();
    	
    	Author author = null;
    	for (int i=0;i<10;i++) {
    		author = new Author();
    		author.setAge(i);
    		author.setName("rocky" + i);
    		
    		authorMapper.insert(author);
    		
    		authorList.add(author);
    	}
    	
    	sqlSession.commit();
        sqlSession.close();
	}

	@Test
    @Override
    public void testSelectById() {
    	
        Author author = mapper.selectById(1L);
        Assert.assertEquals(author.getName(), "m1");
        Assert.assertEquals(author.getAge(), 20);
    }

}

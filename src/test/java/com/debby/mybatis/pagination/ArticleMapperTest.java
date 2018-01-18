package com.debby.mybatis.pagination;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.AbstractDebbyMapperTest;
import com.debby.mybatis.bean.Page;
import com.debby.mybatis.criteria.EntityCriteria;
import com.debby.mybatis.criteria.Order;

/**
 * @author rocky.hu
 * @date Jan 5, 2018 2:30:50 PM
 */
public class ArticleMapperTest extends AbstractDebbyMapperTest<ArticleMapper> {
	
	public ArticleMapperTest() {
		super();
		this.setDdlPath("/data/ddl/article.ddl");
		this.setDataXmlPath("/data/article.xml");
	}

	@Test
	@Override
	public void testSelectPage() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Article.class);
		entityCriteria.setFirstResult(0);
		entityCriteria.setMaxResults(10);
		
		entityCriteria.addOrder(Order.asc("createTime"));
		entityCriteria.addOrder(Order.asc("id"));
		
		entityCriteria.addFilterProperty("createTime");
		
		Page<Article> page = mapper.selectPage(entityCriteria);
		Assert.assertEquals(page.getTotalCount(), 50L);
		Assert.assertEquals(page.getResults().size(), 10);
	}
    
}

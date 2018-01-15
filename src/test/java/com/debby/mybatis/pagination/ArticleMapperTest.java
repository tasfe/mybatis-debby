package com.debby.mybatis.pagination;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.DebbyMapperTest;
import com.debby.mybatis.bean.QueryResult;
import com.debby.mybatis.criteria.EntityCriteria;
import com.debby.mybatis.criteria.Order;

/**
 * @author rocky.hu
 * @date Jan 5, 2018 2:30:50 PM
 */
public class ArticleMapperTest extends DebbyMapperTest<ArticleMapper> {
	
	public ArticleMapperTest() {
		super();
		this.setDdlPath("/assets/pagination/article.ddl");
		this.setDataXmlPath("/assets/pagination/article.xml");
	}

	@Test
	@Override
	public void testSelectPaginationByCriteria() {
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Article.class);
		entityCriteria.setFirstResult(0);
		entityCriteria.setMaxResults(10);
		
		entityCriteria.addOrder(Order.asc("createTime"));
		entityCriteria.addOrder(Order.asc("id"));
		
		entityCriteria.addFilterProperty("createTime");
		
		QueryResult<Article> queryResult = mapper.selectPaginationByCriteria(entityCriteria);
		Assert.assertEquals(queryResult.getTotalCount(), 50L);
		Assert.assertEquals(queryResult.getResults().size(), 10);
	}
    
}

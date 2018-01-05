package com.debby.mybatis.pagination;

import org.testng.annotations.Test;

import com.debby.mybatis.DebbyMapperTest;
import com.debby.mybatis.bean.QueryResult;
import com.debby.mybatis.criteria.EntityCriteria;

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
		QueryResult<Article> queryResult = mapper.selectPaginationByCriteria(new EntityCriteria(Article.class));
		System.out.println(queryResult);
	}
    
}

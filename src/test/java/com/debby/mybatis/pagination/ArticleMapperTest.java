package com.debby.mybatis.pagination;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.debby.mybatis.AbstractDebbyMapperTest;
import com.debby.mybatis.bean.Page;
import com.debby.mybatis.criteria.EntityCriteria;
import com.debby.mybatis.criteria.sort.Order;

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
		EntityCriteria entityCriteria = EntityCriteria.forEntity(Article.class)
				.filter(true, new String[] { "createTime" })
				.orderBy(Order.asc("createTime"), Order.asc("id"))
				.limit(0, 10).build();

		Page<Article> page = mapper.selectPage(entityCriteria);
		Assert.assertEquals(page.getTotalCount(), 50L);
		Assert.assertEquals(page.getResults().size(), 10);
	}

}

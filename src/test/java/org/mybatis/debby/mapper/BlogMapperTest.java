package org.mybatis.debby.mapper;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.debby.MyBatisUtils;
import org.mybatis.debby.entity.Blog;
import org.mybatis.debby.entity.Member;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * @author rocky.hu
 * @date 2017-11-23 11:10 PM
 */
public class BlogMapperTest {

    @Test
    public void test_insert() {
        SqlSession sqlSession = MyBatisUtils.getSqlSessionFactory().openSession();
        Blog blog = new Blog();
        blog.setTitle("blog3");
        blog.setContent("this is blog3");

        Member member = new Member();
        member.setId(1);
        blog.setMember(member);

        sqlSession.getMapper(BlogMapper.class).insert(blog);
        sqlSession.commit();
        sqlSession.close();
        Assert.assertNotNull(blog.getId());
    }

    @Test
    public void test_selectByPrimaryKey()
    {
        SqlSession sqlSession = MyBatisUtils.getSqlSessionFactory().openSession();
        Blog blog = sqlSession.getMapper(BlogMapper.class).selectByPrimaryKey(1);
        Assert.assertNotNull(blog);
        Member member = blog.getMember();
        Assert.assertNotNull(member);
        Assert.assertEquals(member.getId().intValue(), 1);
    }

//    @Test
//    public void test_selectByCriteria()
//    {
//        SqlSession sqlSession = MyBatisUtils.getSqlSessionFactory().openSession();
//
//        EntityCriteria entityCriteria = EntityCriteriaBuilder.buildEntityCriteria();
//        entityCriteria.createCriteria().addCriterion(Restrictions.eq("title", "t1"));
//
//        List<Blog> blogList = sqlSession.getMapper(BlogMapper.class).selectByCriteria(entityCriteria);
//        assertThat(blogList.size(), equalTo(1));
//    }
//
//    @Test
//    public void test_selectWithCountByCriteria()
//    {
//        SqlSession sqlSession = MyBatisUtils.getSqlSessionFactory().openSession();
//
//        EntityCriteria entityCriteria = EntityCriteriaBuilder.buildEntityCriteria();
//        entityCriteria.createCriteria().addCriterion(Restrictions.like("title", "t", MatchMode.ANYWHERE));
//        entityCriteria.setFirstResult(0);
//        entityCriteria.setMaxResults(1);
//
//        List<Blog> blogList = sqlSession.getMapper(BlogMapper.class).selectWithCountByCriteria(entityCriteria);
//        assertThat(blogList.size(), equalTo(1));
//    }
//
//    @Test
//    public void test_countByCriteria()
//    {
//        SqlSession sqlSession = MyBatisUtils.getSqlSessionFactory().openSession();
//
//        EntityCriteria entityCriteria = EntityCriteriaBuilder.buildEntityCriteria();
//        entityCriteria.createCriteria().addCriterion(Restrictions.like("title", "t", MatchMode.ANYWHERE));
//
//        int count = sqlSession.getMapper(BlogMapper.class).countByCriteria(entityCriteria);
//        assertThat(count, equalTo(2));
//    }

}

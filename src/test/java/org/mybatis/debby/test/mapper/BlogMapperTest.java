package org.mybatis.debby.test.mapper;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.debby.test.util.MyBatisUtils;
import org.mybatis.debby.test.entity.Blog;
import org.mybatis.debby.test.entity.Member;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author rocky.hu
 * @date 2017-11-23 11:10 PM
 */
public class BlogMapperTest {

    private SqlSession sqlSession;

    @BeforeMethod
    public void setUp() {
         sqlSession = MyBatisUtils.getSqlSessionFactory().openSession();
    }

    @AfterMethod
    public void afterMethod() {
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test_insert() {
        Blog blog = new Blog();
        blog.setTitle("blog6");

        Member member = new Member();
        member.setId(1);
        blog.setMember(member);

        sqlSession.getMapper(BlogMapper.class).insert(blog);
        Assert.assertNotNull(blog.getId());
    }

    @Test
    public void test_insertSelective() {
        Blog blog = new Blog();
        blog.setTitle("blog5");

        Member member = new Member();
        member.setId(1);
        blog.setMember(member);

        sqlSession.getMapper(BlogMapper.class).insertSelective(blog);
        Assert.assertNotNull(blog.getId());
    }

    @Test
    public void test_updateByPrimaryKey() {
        Blog blog = new Blog();
        blog.setId(1);
        blog.setTitle("blog1_1");

        sqlSession.getMapper(BlogMapper.class).updateByPrimaryKey(blog);

        blog = sqlSession.getMapper(BlogMapper.class).selectByPrimaryKey(1);
        Assert.assertNull(blog.getContent());
    }

    @Test
    public void test_selectByPrimaryKey()
    {
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

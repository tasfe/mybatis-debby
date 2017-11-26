package org.mybatis.debby.mapper;

import org.apache.ibatis.annotations.Insert;
import org.mybatis.debby.entity.Blog;
import org.mybatis.debby.x.DebbyMapper;

/**
 * @author rocky.hu
 * @date 2017-11-23 10:31 PM
 */
public interface BlogMapper extends DebbyMapper<Blog, Integer> {

//    @Insert("INSERT into t_blog(id,name,title,member_id) VALUES(#{id}, #{name}, #{title}, #{member.id})")
//    void insert(Blog blog);

}

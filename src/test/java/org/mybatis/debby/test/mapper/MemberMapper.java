package org.mybatis.debby.test.mapper;

import org.mybatis.debby.DebbyMapper;
import org.mybatis.debby.test.entity.Member;
import org.mybatis.debby.test.entity.MemberPK;

/**
 * @author rocky.hu
 * @date 2017-12-16 11:40 AM
 */
public interface MemberMapper extends DebbyMapper<Member, MemberPK> {
}

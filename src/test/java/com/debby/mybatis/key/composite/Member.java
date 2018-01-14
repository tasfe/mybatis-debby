package com.debby.mybatis.key.composite;

import com.debby.mybatis.annotation.MappingCompositeId;

/**
 * @author rocky.hu
 * @date 2017-12-16 11:25 AM
 */
public class Member {

    @MappingCompositeId
    private MemberPK memberPK;
    private int age;

    public MemberPK getMemberPK() {
        return memberPK;
    }

    public void setMemberPK(MemberPK memberPK) {
        this.memberPK = memberPK;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

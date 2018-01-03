package com.debby.mybatis.compositekey;

import javax.persistence.EmbeddedId;

/**
 * @author rocky.hu
 * @date 2017-12-16 11:25 AM
 */
public class Member {

    @EmbeddedId
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

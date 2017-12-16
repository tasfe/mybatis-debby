package org.mybatis.debby.test.entity;

/**
 * @author rocky.hu
 * @date 2017-12-16 11:25 AM
 */
public class Member {

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

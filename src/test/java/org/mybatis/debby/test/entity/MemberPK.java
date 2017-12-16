package org.mybatis.debby.test.entity;

import java.io.Serializable;

/**
 * @author rocky.hu
 * @date 2017-12-16 12:08 PM
 */
public class MemberPK implements Serializable {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
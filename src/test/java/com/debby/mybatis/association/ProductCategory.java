package com.debby.mybatis.association;

import com.debby.mybatis.annotation.MappingId;

/**
 * @author rocky.hu
 * @date 2017-11-28 10:55 PM
 */
public class ProductCategory {

    @MappingId
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

package com.debby.mybatis.association;

import com.debby.mybatis.MyBatisDebbyMapper;

/**
 * @author rocky.hu
 * @date 2017-11-23 10:31 PM
 */
public interface ProductMapper extends MyBatisDebbyMapper<Product, Integer> {

    Product testVarargs(Object... args);

}

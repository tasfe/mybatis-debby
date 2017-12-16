package com.debby.mybatis.mapper;

import com.debby.mybatis.DebbyMapper;
import com.debby.mybatis.entity.Product;

/**
 * @author rocky.hu
 * @date 2017-11-23 10:31 PM
 */
public interface ProductMapper extends DebbyMapper<Product, Integer> {

    Product testVarargs(Object... args);

}

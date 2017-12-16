package org.mybatis.debby.test.mapper;

import org.mybatis.debby.test.entity.Product;
import org.mybatis.debby.DebbyMapper;

/**
 * @author rocky.hu
 * @date 2017-11-23 10:31 PM
 */
public interface ProductMapper extends DebbyMapper<Product, Integer> {

    Product testVarargs(Object... args);

}

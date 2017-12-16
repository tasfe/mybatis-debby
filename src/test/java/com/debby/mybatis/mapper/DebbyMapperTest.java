package com.debby.mybatis.mapper;

/**
 * @author rocky.hu
 * @date 2017-12-16 11:59 AM
 */
public interface DebbyMapperTest {

    void testInsert();

    void testInsertSelective();

    void testUpdateByPrimaryKey();

    void testUpdateByPrimaryKeySelective();

    void testUpdateByCriteria();

    void testUpdateByCriteriaSelective();

    void testSelectByPrimaryKey();

    void testSelectByCriteria();

    void testSelectCountByCriteria();

    void testDeleteByPrimaryKey();

    void testDeleteByCriteria();
}

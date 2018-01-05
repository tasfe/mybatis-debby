package com.debby.mybatis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * @author rocky.hu
 * @date 2017-12-16 11:59 AM
 */
public abstract class DebbyMapperTest<MAPPER extends DebbyMapper<?, ?>> {

	protected MAPPER mapper;
	private Class<MAPPER> mapperClass;
	protected SqlSession sqlSession;
	protected DBUnitHelper dbUnitHelper = new DBUnitHelper();
	protected String ddlPath;
	protected String dataXmlPath;
	
	@SuppressWarnings("unchecked")
	public DebbyMapperTest() {
		this.mapperClass = null;
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.mapperClass = (Class<MAPPER>) parameterizedType[0];
        }
	}
	
	@BeforeMethod
	public void beforeMethod() {
		dbUnitHelper.createTableFromFile(ddlPath);
		dbUnitHelper.executeDatasetAsRefresh(dataXmlPath);
        sqlSession = MyBatisHelper.getSqlSessionFactory().openSession(true);
        mapper = sqlSession.getMapper(mapperClass);
	}

	@AfterMethod
	public void afterMethod() {
		sqlSession.close();
	}
	
	public String getDdlPath() {
		return ddlPath;
	}

	public void setDdlPath(String ddlPath) {
		this.ddlPath = ddlPath;
	}

	public String getDataXmlPath() {
		return dataXmlPath;
	}

	public void setDataXmlPath(String dataXmlPath) {
		this.dataXmlPath = dataXmlPath;
	}

	public void testInsert() {
	}

	public void testInsertSelective() {
	}

	public void testUpdateByPrimaryKey() {
	}

	public void testUpdateByPrimaryKeySelective() {
	}

	public void testUpdateByCriteria() {
	}

	public void testUpdateByCriteriaSelective() {
	}

	public void testSelectByPrimaryKey() {
	}

	public void testSelectByCriteria() {
	}

	public void testSelectPaginationByCriteria() {
	}

	public void testSelectCountByCriteria() {
	}

	public void testDeleteByPrimaryKey() {
	}

	public void testDeleteByCriteria() {
	}
	
}

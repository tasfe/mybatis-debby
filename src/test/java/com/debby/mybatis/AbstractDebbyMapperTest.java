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
public abstract class AbstractDebbyMapperTest<MAPPER extends DebbyMapper<?, ?>> {

	protected MAPPER mapper;
	private Class<MAPPER> mapperClass;
	protected SqlSession sqlSession;
	protected DBUnitHelper dbUnitHelper = new DBUnitHelper();
	protected String ddlPath;
	protected String dataXmlPath;
	
	@SuppressWarnings("unchecked")
	public AbstractDebbyMapperTest() {
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
        sqlSession = MyBatisHelper.getSqlSessionFactory().openSession(false);
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

	// test methods
	
    public void testInsert(){};
    public void testInsertSelective(){};
    public void testUpdateById(){};
    public void testUpdateByIdSelective(){};
    public void testUpdate(){};
    public void testUpdateSelective(){};
    public void testSelectById(){};
    public void testSelectOne(){};
    public void testSelectList(){};
    public void testSelectPage(){};
    public void testSelectCount(){};
    public void testDeleteById(){};
    public void testDelete(){};
	
	
}

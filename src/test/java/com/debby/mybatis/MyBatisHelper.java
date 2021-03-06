package com.debby.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.File;
import java.io.InputStream;

/**
 * @author rocky.hu
 * @date Jan 4, 2018 11:48:26 AM
 */
public class MyBatisHelper {

    private static SqlSessionFactory sqlSessionFactory = buildSqlSessionFactory();

    private static SqlSessionFactory buildSqlSessionFactory() {
    	SqlSessionFactory sqlSessionFactory = null;
    	
    	try {
			String config = "mybatis-config.xml";
			InputStream configInputStream = Resources.getResourceAsStream(config);
			MyBatisDebbyConfiguration debbyConfiguration = new MyBatisDebbyConfiguration();
			debbyConfiguration.setDebugEnabled(false);
			debbyConfiguration.setMapperXmlOutputPath(System.getProperty("user.home") + File.separator + ".debby" + File.separator);
			debbyConfiguration.setTablePrefix("t_");
			debbyConfiguration.setDialect("h2");
			debbyConfiguration.setCamelToUnderscore(true);

			sqlSessionFactory = new MyBatisDebbySqlSessionFactoryBuilder(debbyConfiguration).build(configInputStream);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	
    	return sqlSessionFactory;
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

}

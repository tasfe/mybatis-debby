package com.debby.mybatis;

import java.io.File;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;

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
			DebbyConfiguration debbyConfiguration = new DebbyConfiguration();
			debbyConfiguration.setDebugEnabled(true);
			debbyConfiguration.setMapperXMLOutputDirectory(System.getProperty("user.home") + File.separator + ".debby" + File.separator);
			debbyConfiguration.setTablePrefix("t_");
			debbyConfiguration.setDialect("mysql");
			debbyConfiguration.setCamelToUnderscore(true);

			sqlSessionFactory = new DebbySqlSessionFactoryBuilder(debbyConfiguration).build(configInputStream);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	
    	return sqlSessionFactory;
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

}

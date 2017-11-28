package org.mybatis.debby.test.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;

import org.mybatis.debby.core.builder.XSqlSessionFactoryBuilder;
import org.mybatis.debby.core.keystrategy.normal.XMySQLKeyStrategy;
import org.mybatis.debby.x.DebbyConfiguration;

import java.io.InputStream;

/**
 * Created by rocky on 3/15/16.
 */
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory = buildSqlSessionFactory();

    private static SqlSessionFactory buildSqlSessionFactory() {
    	SqlSessionFactory sqlSessionFactory = null;
    	
    	try {
			String config = "mybatis-config.xml";
			InputStream configInputStream = Resources.getResourceAsStream(config);
			DebbyConfiguration debbyConfiguration = new DebbyConfiguration();
			debbyConfiguration.setDebugEnabled(true);
			debbyConfiguration.setMapperXMLOutputDirectory("/Users/rocky/Work/project/temp/");
			debbyConfiguration.setTablePrefix("t_");
			debbyConfiguration.setKeyStrategy(new XMySQLKeyStrategy());

			sqlSessionFactory = new XSqlSessionFactoryBuilder(debbyConfiguration).build(configInputStream);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	
    	return sqlSessionFactory;
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

}

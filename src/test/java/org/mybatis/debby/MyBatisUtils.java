package org.mybatis.debby;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.XConfiguration;
import org.mybatis.debby.codegen.keystrategy.normal.XMySQLKeyStrategy;
import org.mybatis.debby.x.XMyBatisGenerator;

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
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(configInputStream);
			Configuration configuration = sqlSessionFactory.getConfiguration();

			XConfiguration xConfiguration = new XConfiguration(configuration);
			xConfiguration.setDebugEnabled(true);
			xConfiguration.setMapperXMLOuputDirectory("/Users/rocky/Work/project/temp/");
			xConfiguration.setTablePrefix("t_");
			xConfiguration.setKeyStrategy(new XMySQLKeyStrategy());

			XMyBatisGenerator xMyBatisGenerator = new XMyBatisGenerator(xConfiguration);
			xMyBatisGenerator.execute();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	
    	return sqlSessionFactory;
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

}

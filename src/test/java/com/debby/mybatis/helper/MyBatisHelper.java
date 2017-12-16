package com.debby.mybatis.helper;

import com.debby.mybatis.DebbyConfiguration;
import com.debby.mybatis.DebbySqlSessionFactoryBuilder;
import com.debby.mybatis.core.keystrategy.normal.XH2KeyStrategy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.InputStream;

/**
 * Created by rocky on 3/15/16.
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
			debbyConfiguration.setMapperXMLOutputDirectory("/Users/rocky/Work/project/temp/");
			debbyConfiguration.setTablePrefix("t_");
			debbyConfiguration.setKeyStrategy(new XH2KeyStrategy());

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

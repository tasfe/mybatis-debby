package com.debby.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.File;
import java.io.InputStream;

/**
 * @author rocky.hu
 * @date 2017-11-23 10:30 PM
 */
public class MyBatisDebbySqlSessionFactoryBuilderTest {

    public static void main(String[] args) throws Exception {

        String config = "mybatis-config.xml";
        InputStream configInputStream = Resources.getResourceAsStream(config);

        MyBatisDebbyConfiguration debbyConfiguration = new MyBatisDebbyConfiguration();
        debbyConfiguration.setDebugEnabled(true);
        debbyConfiguration.setMapperXmlOutputPath(System.getProperty("user.home") + File.separator + ".debby" + File.separator);
        debbyConfiguration.setTablePrefix("t_");
        debbyConfiguration.setDialect("h2");
        debbyConfiguration.setCamelToUnderscore(true);

        SqlSessionFactory sqlSessionFactory = new MyBatisDebbySqlSessionFactoryBuilder(debbyConfiguration).build(configInputStream);
        Configuration configuration = sqlSessionFactory.getConfiguration();
        System.out.println(configuration);
    }

}

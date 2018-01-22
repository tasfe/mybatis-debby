package com.debby.mybatis;

import java.io.File;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author rocky.hu
 * @date 2017-11-23 10:30 PM
 */
public class DebbySqlSessionFactoryBuilderTest {

    public static void main(String[] args) throws Exception {

        String config = "mybatis-config.xml";
        InputStream configInputStream = Resources.getResourceAsStream(config);

        MyBatisDebbyConfiguration myBatisDebbyConfiguration = new MyBatisDebbyConfiguration();
        myBatisDebbyConfiguration.setDebugEnabled(true);
        myBatisDebbyConfiguration.setMapperXmlOutputPath(System.getProperty("user.home") + File.separator + ".debby" + File.separator);
        myBatisDebbyConfiguration.setTablePrefix("t_");
        myBatisDebbyConfiguration.setDialect("h2");
        myBatisDebbyConfiguration.setCamelToUnderscore(true);

        SqlSessionFactory sqlSessionFactory = new MyBatisDebbySqlSessionFactoryBuilder(myBatisDebbyConfiguration).build(configInputStream);
        Configuration configuration = sqlSessionFactory.getConfiguration();
        System.out.println(configuration);
    }

}

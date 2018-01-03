package com.debby.mybatis;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

import com.debby.mybatis.core.dialect.H2Dialect;

/**
 * @author rocky.hu
 * @date 2017-11-23 10:30 PM
 */
public class DebbySqlSessionFactoryBuilderTest {

    public static void main(String[] args) throws Exception {

        String config = "mybatis-config.xml";
        InputStream configInputStream = Resources.getResourceAsStream(config);

        DebbyConfiguration debbyConfiguration = new DebbyConfiguration();
        debbyConfiguration.setDebugEnabled(true);
        debbyConfiguration.setMapperXMLOutputDirectory("/Users/rocky/Work/project/temp/");
        debbyConfiguration.setTablePrefix("t_");
        debbyConfiguration.setDialect(new H2Dialect());

        SqlSessionFactory sqlSessionFactory = new DebbySqlSessionFactoryBuilder(debbyConfiguration).build(configInputStream);
        Configuration configuration = sqlSessionFactory.getConfiguration();
    }

}

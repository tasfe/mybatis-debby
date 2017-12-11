package org.mybatis.debby.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.debby.core.builder.XSqlSessionFactoryBuilder;
import org.mybatis.debby.core.keystrategy.normal.XMySQLKeyStrategy;
import org.mybatis.debby.DebbyConfiguration;

import java.io.InputStream;

/**
 * @author rocky.hu
 * @date 2017-11-23 10:30 PM
 */
public class XMyBatisComplementorTest {

    public static void main(String[] args) throws Exception {

        String config = "mybatis-config.xml";
        InputStream configInputStream = Resources.getResourceAsStream(config);

        DebbyConfiguration debbyConfiguration = new DebbyConfiguration();
        debbyConfiguration.setDebugEnabled(true);
        debbyConfiguration.setMapperXMLOutputDirectory("/Users/rocky/Work/project/temp/");
        debbyConfiguration.setTablePrefix("t_");
        debbyConfiguration.setKeyStrategy(new XMySQLKeyStrategy());

        SqlSessionFactory sqlSessionFactory = new XSqlSessionFactoryBuilder(debbyConfiguration).build(configInputStream);
        Configuration configuration = sqlSessionFactory.getConfiguration();
    }

}

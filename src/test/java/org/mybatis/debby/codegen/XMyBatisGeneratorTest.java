package org.mybatis.debby.codegen;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.XConfiguration;
import org.mybatis.debby.x.XMyBatisGenerator;

import java.io.InputStream;

/**
 * @author rocky.hu
 * @date 2017-11-23 10:30 PM
 */
public class XMyBatisGeneratorTest {

    public static void main(String[] args) throws Exception {

        String config = "mybatis-config.xml";
        InputStream configInputStream = Resources.getResourceAsStream(config);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configInputStream);
        Configuration configuration = sqlSessionFactory.getConfiguration();

        XConfiguration xConfiguration = new XConfiguration(configuration);
        xConfiguration.setDebugEnabled(true);
        xConfiguration.setMapperXMLOuputDirectory("F:/temp/xml/");
        xConfiguration.setTablePrefix("t_");

        XMyBatisGenerator xMyBatisGenerator = new XMyBatisGenerator(xConfiguration);
        xMyBatisGenerator.generate();
    }

}

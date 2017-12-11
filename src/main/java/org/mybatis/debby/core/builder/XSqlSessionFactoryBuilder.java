/**
 *    Copyright 2016-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.debby.core.builder;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.XConfiguration;
import org.mybatis.debby.DebbyConfiguration;
import org.mybatis.debby.core.XMyBatisComplementor;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * @author rocky.hu
 * @date 2017-11-27 9:39 PM
 */
public class XSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {

    private DebbyConfiguration debbyConfiguration;

    public XSqlSessionFactoryBuilder() {
        debbyConfiguration = new DebbyConfiguration();
    }

    public XSqlSessionFactoryBuilder(DebbyConfiguration debbyConfiguration) {
        this.debbyConfiguration = debbyConfiguration;
    }

    @Override
    public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
        SqlSessionFactory sqlSessionFactory = super.build(reader, environment, properties);
        boost(sqlSessionFactory.getConfiguration());
        return sqlSessionFactory;
    }

    @Override
    public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
        SqlSessionFactory sqlSessionFactory = super.build(inputStream, environment, properties);
        boost(sqlSessionFactory.getConfiguration());
        return sqlSessionFactory;
    }

    private void boost(Configuration configuration) {
        XConfiguration xConfiguration = new XConfiguration(configuration);
        XMyBatisComplementor xMyBatisGenerator = new XMyBatisComplementor(debbyConfiguration, xConfiguration);
        xMyBatisGenerator.execute();
    }

}

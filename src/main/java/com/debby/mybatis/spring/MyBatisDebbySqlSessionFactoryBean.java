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
package com.debby.mybatis.spring;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import com.debby.mybatis.MyBatisDebbyConfiguration;
import com.debby.mybatis.core.MyBatisDebbyBooster;

/**
 * @author rocky.hu
 * @date Jan 16, 2018 2:31:41 PM
 */
public class MyBatisDebbySqlSessionFactoryBean extends SqlSessionFactoryBean {
	
	private MyBatisDebbyConfiguration myBatisDebbyConfiguration;

	@Override
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();
		MyBatisDebbyBooster.boost(myBatisDebbyConfiguration, sqlSessionFactory.getConfiguration());
		return sqlSessionFactory;
	}

	public MyBatisDebbyConfiguration getDebbyConfiguration() {
		return myBatisDebbyConfiguration;
	}

	public void setDebbyConfiguration(MyBatisDebbyConfiguration myBatisDebbyConfiguration) {
		this.myBatisDebbyConfiguration = myBatisDebbyConfiguration;
	}

}

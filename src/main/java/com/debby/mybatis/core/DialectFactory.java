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
package com.debby.mybatis.core;

import java.util.HashMap;
import java.util.Map;

import com.debby.mybatis.core.dialect.DB2Dialect;
import com.debby.mybatis.core.dialect.Dialect;
import com.debby.mybatis.core.dialect.H2Dialect;
import com.debby.mybatis.core.dialect.HSQLDialect;
import com.debby.mybatis.core.dialect.MySQLDialect;
import com.debby.mybatis.core.dialect.OracleDialect;
import com.debby.mybatis.core.dialect.SQLServer2005Dialect;
import com.debby.mybatis.core.dialect.SQLServer2008Dialect;
import com.debby.mybatis.core.dialect.SQLServer2012Dialect;
import com.debby.mybatis.core.dialect.SQLServerDialect;
import com.debby.mybatis.exception.MyBatisDebbyException;

/**
 * @author rocky.hu
 * @date Jan 16, 2018 3:42:37 PM
 */
public class DialectFactory {
	
	private static Map<String, Dialect> dialectMap = new HashMap<String, Dialect>();
	
	static {
		dialectMap.put("h2", new H2Dialect());
		dialectMap.put("mysql", new MySQLDialect());
		dialectMap.put("db2", new DB2Dialect());
		dialectMap.put("hsqldb", new HSQLDialect());
		dialectMap.put("oracle", new OracleDialect());
		dialectMap.put("sqlserver", new SQLServerDialect());
		dialectMap.put("sqlserver2005", new SQLServer2005Dialect());
		dialectMap.put("sqlserver2008", new SQLServer2008Dialect());
		dialectMap.put("sqlserver2012", new SQLServer2012Dialect());
	}
	
	public static Dialect getDialect(String dialectName) {
		final Dialect dialect = dialectMap.get(dialectName);
		if (dialect == null) {
			throw new MyBatisDebbyException("Unable to recognize dialect [" + dialectName + "]");
		}
		return dialect;
	}
	
}

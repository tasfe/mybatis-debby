/**
 *    Copyright 2017-2018 the original author or authors.
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
package com.debby.mybatis.core.interceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.debby.mybatis.core.InternalStatements;
import com.debby.mybatis.core.session.PaginationRowBounds;

/**
 * @author rocky.hu
 * @date 2017-12-12 9:19 PM
 */
@Intercepts(
    {
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
    }
)
public class PaginationExecutorInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        String mappedStatementId = mappedStatement.getId();
        String namespace = mappedStatementId.substring(0, mappedStatementId.lastIndexOf("."));
        String id = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
        if (InternalStatements.SELECT_PAGE.getId().equals(id)) {
            try {
                MappedStatement internalPagingMappedStatement = mappedStatement.getConfiguration().getMappedStatement(namespace + "." + InternalStatements.INTERNAL_SELECT_LIST_FOR_PAGING.getId());
                MappedStatement selectCountByCriteriaMappedStatement = mappedStatement.getConfiguration().getMappedStatement(namespace + "." + InternalStatements.SELECT_COUNT.getId());
                List<?> resultList = null;
                List<Long> countList = null;
                if (args.length == 4) {
                    resultList = executor.query(internalPagingMappedStatement, args[1], (RowBounds) args[2], (ResultHandler) args[3]);
                    countList = executor.query(selectCountByCriteriaMappedStatement, args[1], (RowBounds) args[2], (ResultHandler) args[3]);
                } else {
                    resultList = executor.query(internalPagingMappedStatement, args[1], (RowBounds) args[2], (ResultHandler) args[3], (CacheKey) args[4], (BoundSql) args[5]);
                    countList = executor.query(selectCountByCriteriaMappedStatement, args[1], (RowBounds) args[2], (ResultHandler) args[3], (CacheKey) args[4], (BoundSql) args[5]);
                }

                long count = countList.get(0);

                PaginationRowBounds paginationRowBounds = new PaginationRowBounds(((RowBounds) args[2]).getOffset(), ((RowBounds) args[2]).getLimit(), resultList, count);
                args[2] = paginationRowBounds;
            } catch (Exception e) {
                throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
            } finally {
                ErrorContext.instance().reset();
            }
        }

        return method.invoke(executor, args);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

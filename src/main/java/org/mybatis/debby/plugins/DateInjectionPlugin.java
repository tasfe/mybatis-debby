///**
// *    Copyright 2016-2017 the original author or authors.
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *       http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//package org.mybatis.debby.plugins;
//
//import java.lang.reflect.Method;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import org.apache.ibatis.binding.BindingException;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//import org.apache.ibatis.plugin.Signature;
//import org.mybatis.debby.annotation.CreatedDate;
//import org.mybatis.debby.annotation.ModifiedDate;
//import org.mybatis.debby.helper.EntityHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.util.ReflectionUtils;
//
///**
// * Auto populate the date property which is annotated by {#link CreatedDate} or {#link ModifiedDate}.
// * <p>
// * It just for pure mybatis's mapper operation, not working for {#link EntityRepository} interface.
// * <p>
// * Example:
// * 1. insert(Entity entity)<br/>
// * 2. insert(List<Entity> entityList)<br/>
// * 3. update(@Param("record") ENTITY record, ...)<br/>
// * 4. update(Entity entity)
// *
// * @author rocky.hu
// * @date Apr 13, 2015 11:38:12 PM
// */
//
//@Intercepts(
//        {
//                @Signature(
//                        type = Executor.class,
//                        method = "update",
//                        args = {MappedStatement.class, Object.class}
//                )
//        }
//)
//public class DateInjectionPlugin implements Interceptor {
//    private static final Logger LOGGER = LoggerFactory.getLogger(DateInjectionPlugin.class);
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable
//    {
//        LOGGER.info("DateInjectionPlugin executing...");
//
//        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
//        Object parameter = invocation.getArgs()[1];
//        if (parameter instanceof HashMap) {
//            Map<?, ?> map = (HashMap<?, ?>) parameter;
//
//            try {
//                Object record = ((HashMap) parameter).get("record");
//                if (record != null) {
//                    dateInjection(record, sqlCommandType);
//                }
//                else {
//                    // Operation(add and update) for Entity List
//                    List<?> entityList = (List<?>) map.get("list");
//                    if (entityList != null) {
//                        for (int i = 0; i < entityList.size(); i++) {
//                            dateInjection(entityList.get(i), sqlCommandType);
//                        }
//                    }
//                }
//            }
//            catch (BindingException ex) {
//                LOGGER.error("DateInjectionPlugin executing failed...", ex);
//            }
//        }
//        else {
//            dateInjection(parameter, sqlCommandType);
//        }
//
//        return invocation.proceed();
//    }
//
//    private void dateInjection(Object parameter, SqlCommandType sqlCommandType) throws Throwable
//    {
//        Method[] methods = ReflectionUtils.getAllDeclaredMethods(parameter.getClass());
//
//        Date currentDate = new Date();
//        if (SqlCommandType.INSERT == sqlCommandType) {
//            for (Method method : methods) {
//                if (AnnotationUtils.getAnnotation(method, CreatedDate.class) != null ||
//                        AnnotationUtils.getAnnotation(method, ModifiedDate.class) != null)
//                {
//                    if (!EntityHelper.methodReturnTypeCheck(method, Date.class)) {
//                        continue;
//                    }
//
//                    Method setterMethod = EntityHelper.findSetterMethod(method.getName().replaceFirst("get", ""),
//                            parameter.getClass(), Date.class);
//                    setterMethod.invoke(parameter, currentDate);
//                }
//            }
//        }
//        else if (SqlCommandType.UPDATE == sqlCommandType) {
//            for (Method method : methods) {
//                if (AnnotationUtils.getAnnotation(method, ModifiedDate.class) != null) {
//                    if (!EntityHelper.methodReturnTypeCheck(method, Date.class)) {
//                        continue;
//                    }
//
//                    Method setterMethod = EntityHelper.findSetterMethod(method.getName().replaceFirst("get", ""), parameter.getClass(), Date.class);
//                    setterMethod.invoke(parameter, currentDate);
//                }
//            }
//        }
//    }
//
//    public Object plugin(Object target)
//    {
//        if (target instanceof Executor) {
//            return Plugin.wrap(target, this);
//        }
//        else {
//            return target;
//        }
//    }
//
//    public void setProperties(Properties properties)
//    {
//    }
//
//}

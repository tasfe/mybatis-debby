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
//package org.mybatis.debby.helper;
//
//import java.lang.reflect.Method;
//
//import javax.persistence.Table;
//
//import org.springframework.util.ReflectionUtils;
//
//import com.google.common.base.CaseFormat;
//
///**
// * @author rocky.hu
// * @date Apr 26, 2015 4:05:19 PM
// */
//public class EntityHelper {
//    private static final String GET = "get";
//    private static final String SET = "set";
//
//    public static String getColumnName1(String propertyName, boolean isMapUnderscoreToCamelCase)
//    {
//        if (!isMapUnderscoreToCamelCase) {
//            return propertyName;
//        }
//
//        return camelToUnderscore(propertyName);
//    }
//
//    public static String getTableName1(Class<?> entityClass)
//    {
//        Table table = entityClass.getAnnotation(Table.class);
//        if (table == null) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(camelToUnderscore(entityClass.getSimpleName()));
//            return sb.toString();
//        }
//
//        return table.name();
//    }
//
//    public static String camelToUnderscore1(String camelStr)
//    {
//        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelStr);
//    }
//
//    public static String underscoreToCamel1(String underlineStr)
//    {
//        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, underlineStr);
//    }
//
//    public static Method findGetterMethod1(String propertyName, Class<?> entityClass)
//    {
//        StringBuilder sb = new StringBuilder();
//        sb.append(GET);
//        sb.append(Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1));
//        Method method = ReflectionUtils.findMethod(entityClass, sb.toString());
//        return method;
//    }
//
//    public static Method findSetterMethod1(String propertyName, Class<?> entityClass, Class<?>... paramTypes)
//    {
//        StringBuilder sb = new StringBuilder();
//        sb.append(SET);
//        sb.append(Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1));
//        Method method = ReflectionUtils.findMethod(entityClass, sb.toString(), paramTypes);
//        return method;
//    }
//
//    public static boolean methodParameterTypeCheck1(Method method, Class<?> expectedType)
//    {
//        Class<?>[] parameterTypes = method.getParameterTypes();
//        if (parameterTypes.length != 1 || !(parameterTypes[0].isAssignableFrom(expectedType))) {
//            return false;
//        }
//
//        return true;
//    }
//
//    public static boolean methodReturnTypeCheck1(Method method, Class<?> exptectedType)
//    {
//        Class<?> returnType = method.getReturnType();
//        if (!returnType.isAssignableFrom(exptectedType)) {
//            return false;
//        }
//
//        return true;
//    }
//
//    public static void main(String[] args)
//    {
//        String ss = "UPPER_UNDERSCORE";
//        System.out.println(underscoreToCamel(ss));
//    }
//
//}

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
import java.util.Iterator;
import java.util.Map;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

import com.debby.mybatis.exception.DebbyException;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date 2017-12-05 10:08 PM
 */
public class XBaseResultMapRegistry {

    public static final Map<String, ResultMap> ENTITY_RESULT_MAP_MAP = new HashMap<String, ResultMap>();

    public static void putResultMap(String className, ResultMap resultMap) {
        ENTITY_RESULT_MAP_MAP.put(className, resultMap);
    }

    public static ResultMap getResultMap(String className) {
        ResultMap resultMap = ENTITY_RESULT_MAP_MAP.get(className);
        if (resultMap == null) {
            throw new DebbyException("No ResultMap is associated with [" + className + "]");
        }
        return ENTITY_RESULT_MAP_MAP.get(className);
    }

    public static ResultMapping getResultMapping(String className, String propertyName) {
        ResultMapping resultMapping = null;
        ResultMap resultMap = getResultMap(className);
        Iterator<ResultMapping> iter = resultMap.getPropertyResultMappings().iterator();
        while (iter.hasNext()) {
            ResultMapping rm = iter.next();
            if (!StringUtils.isNullOrEmpty(rm.getNestedQueryId()) || !StringUtils.isNullOrEmpty(rm.getNestedResultMapId())) {
                continue;
            }

            if (rm.getProperty().equals(propertyName)) {
                resultMapping = rm;
                break;
            }
        }

        if (resultMapping == null) {
            throw new DebbyException("No result mapping for property [" + propertyName + "]");
        }

        return resultMapping;
    }

}

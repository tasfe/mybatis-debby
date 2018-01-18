/**
 * Copyright 2016-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.debby.mybatis.core.helper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import com.debby.mybatis.annotation.MappingCompositeId;
import com.debby.mybatis.annotation.MappingId;
import com.debby.mybatis.annotation.MappingJdbcType;
import com.debby.mybatis.annotation.MappingResult;
import com.debby.mybatis.annotation.MappingTransient;
import com.debby.mybatis.annotation.MappingTypeHandler;
import com.debby.mybatis.core.ResultMapRegistry;
import com.debby.mybatis.core.bean.XResultMapping;
import com.debby.mybatis.exception.IdConfigException;
import com.debby.mybatis.exception.MappingException;
import com.debby.mybatis.util.BeanUtils;
import com.debby.mybatis.util.ReflectUtils;
import com.debby.mybatis.util.StringUtils;

/**
 * @author rocky.hu
 * @date 2018-01-13 5:16 PM
 */
public class EntityHelper {

    public static boolean hasResultMap(Configuration configuration, String resultMapId) {
        return configuration.getResultMapNames().contains(resultMapId);
    }

    /**
     * Get the field which is annotated with MappingId and the value is generated for a composite id class.
     *
     * @param compositeIdClazz
     *          composite id class
     * @return
     */
    public static Field getGeneratedValueField(Class<?> compositeIdClazz) {
        int generatedValueIdCount = 0;
        Field appropriateMappindIdField = null;
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(compositeIdClazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Field field = ReflectUtils.findField(compositeIdClazz, propertyName);
            MappingId mappingId = field.getAnnotation(MappingId.class);
            if (mappingId != null) {
                boolean generatedValue = mappingId.generatedValue();
                if (generatedValue) {
                    appropriateMappindIdField = field;
                    generatedValueIdCount++;
                }
            }
        }

        if (generatedValueIdCount > 1) {
            throw new IdConfigException("Only one field can be set to generated value.");
        }

        return appropriateMappindIdField;
    }

    public static List<XResultMapping> getXResultMappingList(Class<?> entityClazz, boolean camelToUnderscore) {
        List<XResultMapping> resultMappingList = new ArrayList<XResultMapping>();
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(entityClazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Field field = ReflectUtils.findField(entityClazz, propertyName);

            // Skip some properties
            if (field.getAnnotation(MappingTransient.class) != null) {
                continue;
            }
            if (Collection.class.isAssignableFrom(field.getType()) || Collection.class.isAssignableFrom(Map.class)) {
                continue;
            }

            boolean isId = false;
            String column = camelToUnderscore ? StringUtils.camelToUnderscore(propertyName, false) : propertyName;
            String jdbcType = null;
            String javaType = null;
            String typeHandler = null;

            // MappingId
            MappingId mappingId = field.getAnnotation(MappingId.class);
            if (mappingId != null) {
                isId = true;
                if (!StringUtils.isNullOrEmpty(mappingId.column())) {
                    column = mappingId.column();
                }
            }

            // MappingCompositeId
            MappingCompositeId mappingCompositeId = field.getAnnotation(MappingCompositeId.class);
            if (mappingCompositeId != null) {
                List<XResultMapping> embbedResultMappingList = getXResultMappingList(field.getType(), camelToUnderscore);
                for (XResultMapping resultMapping : embbedResultMappingList) {
                    resultMapping.setProperty(field.getName() + "." + resultMapping.getProperty());
                }
                resultMappingList.addAll(embbedResultMappingList);
                continue;
            }

            // MappingResult
            MappingResult mappingResult = field.getAnnotation(MappingResult.class);
            if (mappingResult != null) {
                if (!StringUtils.isNullOrEmpty(mappingResult.column())) {
                    column = mappingResult.column();
                }
                if (mappingResult.association()) {
                    Class<?> assoicationEntityType = field.getType();
                    Field idField = BeanUtils.findField(assoicationEntityType, MappingId.class);
                    if (idField == null) {
                        throw new MappingException("The assoiction entity " + assoicationEntityType + " must define id field");
                    }

                    propertyName = propertyName + "." + idField.getName();
                    column = column + "_" + StringUtils.camelToUnderscore(idField.getName(), false);
                }
            }

            // MappingJdbcType
            MappingJdbcType mappingJdbcType = field.getAnnotation(MappingJdbcType.class);
            if (mappingJdbcType != null) {
                jdbcType = mappingJdbcType.value().name();
            }

            // MappingTypeHandler
            MappingTypeHandler mappingTypeHandler = field.getAnnotation(MappingTypeHandler.class);
            if (mappingTypeHandler != null) {
                typeHandler = mappingTypeHandler.value().getName();
            }

            XResultMapping resultMapping = new XResultMapping();
            resultMapping.setColumn(column);
            resultMapping.setProperty(propertyName);
            resultMapping.setId(isId);

            if (!StringUtils.isNullOrEmpty(jdbcType)) {
                resultMapping.setJdbcType(jdbcType);
            }
            if (!StringUtils.isNullOrEmpty(typeHandler)) {
                resultMapping.setTypeHandler(typeHandler);
            }

            resultMappingList.add(resultMapping);
        }

        Collections.sort(resultMappingList, new Comparator<XResultMapping>() {
            @Override
            public int compare(XResultMapping o1, XResultMapping o2) {
                boolean isId1 = o1.isId();
                boolean isId2 = o2.isId();
                if (isId1 == true && isId2 == false) {
                    return -1;
                } else if ((isId1 == isId2) ) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        return resultMappingList;
    }
    
    /**
     * Get property result mappings of a entityType.
     * <p>
     *     Exclude nested result mappings.
     * </p>
     * 
     * @param entityType
     * @return
     */
    public static List<ResultMapping> getPropertyResultMappings(Class<?> entityType) {
    	ResultMap resultMap = ResultMapRegistry.getResultMap(entityType.getName());
    	List<ResultMapping> propertyResultMappings = new ArrayList<ResultMapping>();
        if (resultMap.getPropertyResultMappings() != null && resultMap.getPropertyResultMappings().size() > 0) {
            Iterator<ResultMapping> iter = resultMap.getPropertyResultMappings().iterator();
            while (iter.hasNext()) {
                ResultMapping propertyResultMapping = iter.next();
                if (!StringUtils.isNullOrEmpty(propertyResultMapping.getNestedQueryId()) || !StringUtils.isNullOrEmpty(propertyResultMapping.getNestedResultMapId())) {
                    continue;
                }
                propertyResultMappings.add(propertyResultMapping);
            }
        }

        return propertyResultMappings;
    }

}

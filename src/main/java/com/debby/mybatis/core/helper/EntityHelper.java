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
import java.io.Serializable;
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

    /**
     * Check if has the specified result map.
     *
     * @param configuration
     * @param resultMapId
     * @return
     */
    public static boolean hasResultMap(Configuration configuration, String resultMapId) {
        return configuration.getResultMapNames().contains(resultMapId);
    }

    /**
     * Get the composite id field in entity.
     * <p>
     *     Note: Only one field can be specified to composite id field.
     * </p>
     *
     * @param entityType
     * @return
     */
    public static Field getCompositeIdField(Class<?> entityType) {
        List<Field> fieldList = BeanUtils.findField(entityType, MappingCompositeId.class);
        if (fieldList.size() > 1) {
            throw new MappingException("Only one field can be annotated with MappingCompositeId annotation.");
        }
        return fieldList.size() == 0 ? null : fieldList.get(0);
    }

    /**
     * Get the field which is annotated with MappingId and the value is generated.
     * <p>
     *     Note: Only one field can be specified to the value is generated.
     * </p>
     *
     * @param clazz
     * @return
     */
    public static Field getGeneratedValueField(Class<?> clazz) {
        int generatedValueIdCount = 0;
        Field appropriateMappindIdField = null;
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(clazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Field field = ReflectUtils.findField(clazz, propertyName);
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

    private static boolean validate(Class<?> entityClazz) {

        // check MappingId field
        List<Field> mappingIdFieldList = BeanUtils.findField(entityClazz, MappingId.class);
        if (mappingIdFieldList.size() > 1) {
            throw new MappingException("Use composite id for multiple ids.");
        }

        // check MappingCompositeId field
        List<Field> mappingCompositeFieldList = BeanUtils.findField(entityClazz, MappingCompositeId.class);
        if (mappingCompositeFieldList.size() > 1) {
            throw new MappingException("Only one field can be annotated with MappingCompositeId annotation.");
        }

        // MappingId and MappingCompositeId cannot coexist.
        if (mappingIdFieldList.size() > 0 && mappingCompositeFieldList.size() > 0) {
            throw new MappingException("Use MappingId or MappingCompositeId, not both.");
        }

        if (mappingCompositeFieldList.size() == 1) {

            Field mappingCompositeIdField = mappingCompositeFieldList.get(0);
            Class<?> compositeType = mappingCompositeIdField.getType();

            // composite id type must implement the Serializable interface.
            if (!Serializable.class.isAssignableFrom(compositeType)) {
                throw new MappingException("The composite id type must implement the Serializable interface.");
            }

            // check MappingId field in composite id type.
            List<Field> embbedMappingIdFieldList = BeanUtils.findField(compositeType, MappingId.class);
            if (embbedMappingIdFieldList.size() == 0) {
                throw new MappingException("No MappingId found in composite typpe [" + compositeType.getName() + "]");
            }

            // check the generated value field
            getGeneratedValueField(compositeType);
        }

        return true;
    }

    public static List<XResultMapping> autoMappingForCompositeIdType(Class<?> compositeIdType, boolean camelToUnderscore) {
        List<XResultMapping> xResultMappingList = new ArrayList<>();
        List<Field> idFieldList = BeanUtils.findField(compositeIdType, MappingId.class);
        XResultMapping xResultMapping = null;
        for (Field field : idFieldList) {
            xResultMapping = new XResultMapping();
            String fieldName = field.getName();
            String column = camelToUnderscore ? StringUtils.camelToUnderscore(fieldName, false) : fieldName;

            String jdbcType = null;
            String typeHandler = null;

            // MappingId
            MappingId mappingId = field.getAnnotation(MappingId.class);
            if (!StringUtils.isNullOrEmpty(mappingId.column())) {
                column = mappingId.column();
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

            xResultMapping.setColumn(column);
            xResultMapping.setProperty(fieldName);
            xResultMapping.setId(true);

            if (!StringUtils.isNullOrEmpty(jdbcType)) {
                xResultMapping.setJdbcType(jdbcType);
            }
            if (!StringUtils.isNullOrEmpty(typeHandler)) {
                xResultMapping.setTypeHandler(typeHandler);
            }

            xResultMappingList.add(xResultMapping);
        }

        return xResultMappingList;
    }

    /**
     * Entity property auto mapping.
     *
     * @param entityClazz
     * @param camelToUnderscore
     * @return
     */
    public static List<XResultMapping> autoMapping(Class<?> entityClazz, boolean camelToUnderscore) {
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
                List<XResultMapping> embbedResultMappingList = autoMappingForCompositeIdType(field.getType(), camelToUnderscore);
                for (XResultMapping xResultMapping : embbedResultMappingList) {
                    xResultMapping.setProperty(field.getName() + "." + xResultMapping.getProperty());
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

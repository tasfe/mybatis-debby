/**
 * Copyright 2017-2018 the original author or authors.
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
package com.debby.mybatis.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rocky.hu
 * @date 2017-12-17 10:02 PM
 */
public class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static Field findField(Class<?> clazz, String propertyName) {
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyName.equals(propertyDescriptor.getName())) {
                Method readMethod = propertyDescriptor.getReadMethod();
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (readMethod != null && writeMethod != null) {
                    return ReflectUtils.findField(clazz, propertyName);
                }
            }
        }

        return null;
    }
    
    public static <A extends Annotation> List<Field> findField(Class<?> clazz, Class<A> annotationType) {
        List<Field> fieldList = new ArrayList<Field>();
    	PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
    	for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
    		String propertyName = propertyDescriptor.getName();
    		Field field = ReflectUtils.findField(clazz, propertyName);
    		A annotation = field.getAnnotation(annotationType);
    		if (annotation != null) {
    		    fieldList.add(field);
    		}
    	}
    	return fieldList;
    }

    public static Method findReadMethod(Class<?> clazz, String propertyName) {
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyName.equals(propertyDescriptor.getName())) {
                Method readMethod = propertyDescriptor.getReadMethod();
               return readMethod;
            }
        }

        return null;
    }

    public static Method findWriteMethod(Class<?> clazz, String propertyName) {
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyName.equals(propertyDescriptor.getName())) {
                Method writeMethod = propertyDescriptor.getWriteMethod();
                return writeMethod;
            }
        }

        return null;
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        }
        catch (IntrospectionException e) {
            logger.error(e.getMessage(), e);
            return new PropertyDescriptor[0];
        }

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        return propertyDescriptors;
    }

}

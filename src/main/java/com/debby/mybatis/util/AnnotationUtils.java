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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author rocky.hu
 * @date 2017-12-27 8:22 PM
 */
public class AnnotationUtils {

    public static Annotation[] getAnnotations(Field field) {
        return field.getAnnotations();
    }

    public static Annotation[] getAnnotations(Method method) {
        return method.getAnnotations();
    }

    public static <A extends Annotation> A getAnnotation(Field field, Class<A> annotationType) {
        return field.getAnnotation(annotationType);
    }

    public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
        return method.getAnnotation(annotationType);
    }

}

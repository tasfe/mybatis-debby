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
package org.mybatis.debby.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Declares a field as the one representing the date the entity containing the field was modified.
 * <p>
 * So far, it only available when being applied to the "getter" method which the return type is <code>java.util
 * .Date<code>.
 *
 * @author rocky.hu
 * @version 1.1.0
 * @date Apr 13, 2015 11:45:35 PM
 */

@Target({METHOD})
@Retention(RUNTIME)
public @interface ModifiedDate {
}

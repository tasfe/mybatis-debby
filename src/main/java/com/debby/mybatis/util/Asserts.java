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
package com.debby.mybatis.util;

import java.util.Collection;

/**
 * @author rocky.hu
 * @date Jan 24, 2018 2:36:40 PM
 */
public class Asserts {
	
	private static String NOT_NULL_MESSAGE = "Value cann't be null.";
	private static String NOT_EMPATY_MESSAGE = "Value cann't be empty.";
	
	public static void notNull(Object value) {
		notNull(value, NOT_NULL_MESSAGE);
	}
	
	public static void notNull(Object value, String message) {
		if (value == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void notEmpty(Object value) {
		notEmpty(value, NOT_EMPATY_MESSAGE);
	}
	
	public static void notEmpty(Object value, String message) {
		notNull(value, message);
		boolean valid = true;
		if (value instanceof String) {
			if (((String) value).isEmpty()) {
				valid = false;
			}
		} else if (value instanceof Collection) {
			if (((Collection<?>) value).size() == 0) {
				valid =  false;
			}
		} else if (value instanceof Object[]) {
			if (((Object[]) value).length == 0) {
				valid = false;
			}
		}
		
		if (!valid) {
			throw new IllegalArgumentException(message);
		}
	}

}

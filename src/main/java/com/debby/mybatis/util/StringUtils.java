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

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rocky.hu
 * @date Jan 4, 2018 5:13:36 PM
 */
public class StringUtils {
	
	private static Pattern CAMEL_PATTERN = Pattern.compile("(?<=[a-z])[A-Z]");
	
	public static boolean isNullOrEmpty(String string) {
		return ((string == null) || (string.isEmpty()));
	}
	
	public static String camelToUnderscore(final String str, boolean upperCase) {
		Matcher matcher = CAMEL_PATTERN.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
		    matcher.appendReplacement(sb, "_" + matcher.group().toLowerCase());
		}
		matcher.appendTail(sb);
		
		return upperCase ? sb.toString().toUpperCase() : sb.toString().toLowerCase();
	}
	
	public static String join(String seperator, Iterator objects) {
		StringBuilder buf = new StringBuilder();
		if ( objects.hasNext() ) {
			buf.append( objects.next() );
		}
		while ( objects.hasNext() ) {
			buf.append( seperator ).append( objects.next() );
		}
		return buf.toString();
	}
	
}

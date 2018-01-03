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
package com.debby.mybatis.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.generator.internal.util.StringUtility;

import com.google.common.base.Strings;

/**
 * @author Jeff Butler
 * @author rocky.hu
 * @date Nov 20, 2017 10:42:07 PM
 * @see org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities
 */
public class XMyBatis3FormattingUtilities {
	
	 /** The beginning delimiter. */
    private static String beginningDelimiter = "\""; 

    /** The ending delimiter. */
    private static String endingDelimiter = "\"";
	
	public static String getEscapedColumnName(ResultMapping resultMapping) {
		String column = resultMapping.getColumn();
		StringBuilder sb = new StringBuilder();
		sb.append(escapeStringForMyBatis3(column));
		
		if (StringUtility.stringContainsSpace(column)) {
			sb.insert(0, beginningDelimiter);
            sb.append(endingDelimiter);
		}
		
		return sb.toString();
	}

    public static List<String> getEscapedPropertyList(ResultMapping resultMapping) {
	    List<String> propertyList = new ArrayList<String>();
	    String property = resultMapping.getProperty();
	    String[] segments = property.split("\\.");
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < segments.length) {
            sb.setLength(0);
            for (int i=0; i<=index; i++) {
                sb.append(segments[i]);
                if (i<index) {
                    sb.append(".");
                }
            }

            propertyList.add(sb.toString());
            index++;
        }
        return propertyList;
    }

    public static String getPropertyClause(ResultMapping resultMapping) {
        StringBuilder propertySb = new StringBuilder();
        List<String> properties = getEscapedPropertyList(resultMapping);
        Iterator<String> propertyIter = properties.iterator();
        while (propertyIter.hasNext()) {
            propertySb.append(propertyIter.next());
            propertySb.append(" != null");
            if (propertyIter.hasNext()) {
                propertySb.append(" and ");
            }
        }
        return propertySb.toString();
    }
	
	public static String getParameterClause(ResultMapping resultMapping) {
        return getParameterClause(resultMapping, null);
    }
	
	public static String getParameterClause(ResultMapping resultMapping, String prefix) {
        StringBuilder sb = new StringBuilder();

        sb.append("#{");
        if (Strings.isNullOrEmpty(prefix)) {
            sb.append(resultMapping.getProperty());
        } else {
            sb.append(prefix);
            sb.append(resultMapping.getProperty());
        }
        
        if (resultMapping.getJdbcType() != null) {
            sb.append(",jdbcType=");
            sb.append(resultMapping.getJdbcType());
        }
        
        if (resultMapping.getTypeHandler() != null) {
            sb.append(",typeHandler=");
            sb.append(resultMapping.getTypeHandler().getClass().getName());
        }

        sb.append('}');

        return sb.toString();
    }
	
	public static String getParameterClause(ResultMapping resultMapping, String property, String prefix) {
        StringBuilder sb = new StringBuilder();

        sb.append("#{");
        if (Strings.isNullOrEmpty(prefix)) {
            sb.append(property);
        } else {
            sb.append(prefix);
            sb.append(property);
        }
        
        if (resultMapping.getJdbcType() != null) {
            sb.append(",jdbcType=");
            sb.append(resultMapping.getJdbcType());
        }
        
        if (resultMapping.getTypeHandler() != null) {
            sb.append(",typeHandler=");
            sb.append(resultMapping.getTypeHandler().getClass().getName());
        }

        sb.append('}');

        return sb.toString();
    }
	
	public static String escapeStringForMyBatis3(String s) {
        // nothing to do for MyBatis3 so far
        return s;
    }
	
}

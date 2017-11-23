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
package org.mybatis.debby.plugins;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

/**
 * Remove the prefix of table and rename the domain object.
 * <p>
 * This plugin is used with mybatis-generator.
 *
 * @author rocky.hu
 * @date Aug 4, 2016 10:01:02 PM
 */
public class RenameDomainObjectNamePlugin extends PluginAdapter {
    
    private String searchString;
    private String replaceString;
    private Pattern pattern;

    public RenameDomainObjectNamePlugin() {
    }

    @Override
    public boolean validate(List<String> warnings)
    {
        searchString = properties.getProperty("searchString"); //$NON-NLS-1$
        replaceString = properties.getProperty("replaceString"); //$NON-NLS-1$

        if (searchString != null && searchString.length() > 0) {
            pattern = Pattern.compile(searchString);
            return true;
        }

        return false;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {

        // Base Record Type
        String baseRecordType = introspectedTable.getBaseRecordType();
        Matcher baseRecordTypeMatcher = pattern.matcher(baseRecordType);
        baseRecordType = baseRecordTypeMatcher.replaceFirst(replaceString);
        introspectedTable.setBaseRecordType(baseRecordType);

        // Mapper Interface
        String mapperType = introspectedTable.getMyBatis3JavaMapperType();
        Matcher mapperTypeMatcher = pattern.matcher(mapperType);
        mapperType = mapperTypeMatcher.replaceFirst(replaceString);
        introspectedTable.setMyBatis3JavaMapperType(mapperType);

        // XML Mapper File 
        String xmlMapperFileName = introspectedTable.getMyBatis3XmlMapperFileName();
        Matcher xmlMapperFileNameMatcher = pattern.matcher(xmlMapperFileName);
        xmlMapperFileName = xmlMapperFileNameMatcher.replaceFirst(replaceString);
        introspectedTable.setMyBatis3XmlMapperFileName(xmlMapperFileName);

        // Example Type
        String exampleType = introspectedTable.getExampleType();
        Matcher exampleTypeMatcher = pattern.matcher(exampleType);
        exampleType = exampleTypeMatcher.replaceAll(replaceString);
        introspectedTable.setExampleType(exampleType);

    }

}

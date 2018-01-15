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
package com.debby.mybatis.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author rocky.hu
 * @date 2017-11-27 12:07 AM
 */
public class FileUtils {

	/**
	 * Writes the content to a file with specified path.
	 * 
	 * @param directory
	 * @param fileName
	 * @param content
	 * 
	 * @throws IOException
	 */
    public static void writeFile(String directory, String fileName , String content) throws IOException {
    	
    	Path directoryPath = Paths.get(directory);
    	Files.createDirectories(directoryPath);
    	
    	Path filePath = Paths.get(directory, fileName);
    	Files.write(filePath, content.getBytes("UTF-8"));
    }

}

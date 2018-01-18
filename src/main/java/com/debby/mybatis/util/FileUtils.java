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
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rocky.hu
 * @date 2017-11-27 12:07 AM
 */
public class FileUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
	
    public static void writeFile(String directory, String fileName , String content) {
    	try {
			Path directoryPath = Paths.get(directory);
			Files.createDirectories(directoryPath);
			
			Path filePath = Paths.get(directory, fileName);
			Files.write(filePath, content.getBytes("UTF-8"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} 	
    }
    
    public static void clearDirectory(String directory) {
    	clearOrDeleteDirectory(directory, false);
    }
    
    public static void deleteDirectory(String directory) {
    	clearOrDeleteDirectory(directory, true);
    }
    
    private static void clearOrDeleteDirectory(String directory, final boolean deleteCurrent) {
    	
    	try {
			final Path path = Paths.get(directory);
			if (!Files.exists(path)) {
				throw new IOException(path + "is not exists!");
			}
			if (!Files.isDirectory(path)) {
				throw new IOException(path + "is not a directory!");
			}
			
			Files.walkFileTree(path, new FileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					LOGGER.debug("Visit Directory: {}", dir);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					LOGGER.debug("Visit File: {}", file);
					boolean result = Files.deleteIfExists(file);
					if (result) {
						LOGGER.debug("Deleted File: {}", file);
					} else {
						LOGGER.debug("Not deleted File: {}", file);
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					throw exc;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					
					if (!(!deleteCurrent && path.equals(dir))) {
						boolean success = Files.deleteIfExists(dir);
						if (success) {
							LOGGER.debug("Deleted Directory: " + dir);
						} else {
							LOGGER.debug("Not deleted Directory: " + dir);
						}
					}
					
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
    }

}

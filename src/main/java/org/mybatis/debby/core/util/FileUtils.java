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
package org.mybatis.debby.core.util;

import java.io.*;

/**
 * @author rocky.hu
 * @date 2017-11-27 12:07 AM
 */
public class FileUtils {

    /**
     * Writes, or overwrites, the contents of the specified file.
     *
     * @param file         the file
     * @param content      the content
     * @param fileEncoding the file encoding
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeFile(File file, String content, String fileEncoding) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(file, false);
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter(fos);
        }
        else {
            osw = new OutputStreamWriter(fos, fileEncoding);
        }

        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.close();
    }

    /**
     * @param path
     * @param content
     */
    public static void writeFile(String path, String content) {
        FileWriter writer;
        try {
            File file = new File(path);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

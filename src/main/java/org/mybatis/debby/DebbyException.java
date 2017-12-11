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
package org.mybatis.debby;

/**
 * The base exception type for Mybatis-Debby exceptions.
 *
 * @author rocky.hu
 * @date 2017-12-05 9:37 PM
 */
public class DebbyException extends RuntimeException {

    public DebbyException() {
        super();
    }

    public DebbyException(String message) {
        super(message);
    }

    public DebbyException(Throwable cause) {
        super(cause);
    }

    public DebbyException(String message, Throwable cause) {
        super(message, cause);
    }

}

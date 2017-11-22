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
package org.mybatis.debby.criteria;

/**
 * Represents an strategy for matching strings using "like".
 *
 * @author rocky.hu
 * @date Aug 24, 2016, 08:30:06 PM
 */
public enum MatchMode {
    
    EXACT("EXACT"), START("START"), END("END"), ANYWHERE("ANYWHERE");

    private String notation;

    private MatchMode(String notation)
    {
        this.notation = notation;
    }

    public String getNotation()
    {
        return notation;
    }

    public void setNotation(String notation)
    {
        this.notation = notation;
    }

    @Override
    public String toString()
    {
        return this.getNotation();
    }
}

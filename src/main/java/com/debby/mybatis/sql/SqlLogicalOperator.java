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
package com.debby.mybatis.sql;

/**
 * Here is a list of all the logical operators available in SQL.
 *
 * @author rocky.hu
 * @date Sep 08, 2016, 08:59:54 PM
 * @see <a href="http://www.tutorialspoint.com/sql/sql-operators.htm">SQL Logical Operators</a>
 */
public enum SqlLogicalOperator implements SqlOperator {

    ALL("ALL"),
    AND("AND"),
    ANY("ANY"),
    BETWEEN("BETWEEN"),
    EXISTS("EXISTS"),
    IN("IN"),
    LIKE("LIKE"),
    NOT("NOT"),
    OR("OR"),
    UNIQUE("UNIQUE"),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL"),
    NOT_LIKE("NOT LIKE"),
    NOT_IN("NOT IN"),
    NOT_BETWEEN("NOT BETWEEN");

    private String notation;

    private SqlLogicalOperator(String notation)
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

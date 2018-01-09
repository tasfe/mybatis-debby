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
package com.debby.mybatis.criteria;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author rocky.hu
 * @date 2017-12-08 10:10 PM
 */
public abstract class Criterion implements Serializable {

    /** the content is column && sql operator */
    private String condition;
    private Object value;
    private Object secondValue;
    private String typeHandler;
    private boolean noValue;
    private boolean singleValue;
    private boolean betweenValue;
    private boolean listValue;

    protected Criterion(String condition) {
        super();
        this.condition = condition;
        this.typeHandler = null;
        this.noValue = true;
    }

    protected Criterion(String condition, Object value) {
        this(condition, value, null);
    }

    protected Criterion(String condition, Object value, String typeHandler) {
        super();
        this.condition = condition;
        this.value = value;
        this.typeHandler = typeHandler;
        if (value instanceof List<?> || value instanceof Set<?>) {
            this.listValue = true;
        }
        else {
            this.singleValue = true;
        }
    }

    protected Criterion(String condition, Object value, Object secondValue) {
        this(condition, value, secondValue, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
        super();
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.typeHandler = typeHandler;
        this.betweenValue = true;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
    }

	public boolean isNoValue() {
		return noValue;
	}

	public void setNoValue(boolean noValue) {
		this.noValue = noValue;
	}

	public boolean isSingleValue() {
		return singleValue;
	}

	public void setSingleValue(boolean singleValue) {
		this.singleValue = singleValue;
	}

	public boolean isBetweenValue() {
		return betweenValue;
	}

	public void setBetweenValue(boolean betweenValue) {
		this.betweenValue = betweenValue;
	}

	public boolean isListValue() {
		return listValue;
	}

	public void setListValue(boolean listValue) {
		this.listValue = listValue;
	}

}

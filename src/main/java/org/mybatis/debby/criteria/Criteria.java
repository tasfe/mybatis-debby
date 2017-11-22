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

import java.util.ArrayList;
import java.util.List;

/**
 * @author rocky.hu
 * @date Aug 20, 2016, 11:45:47 PM
 */
public class Criteria {
    
    public List<Criterion> criterions;

    public Criteria()
    {
        criterions = new ArrayList<Criterion>();
    }

    public boolean isValid()
    {
        return criterions.size() > 0;
    }

    public List<Criterion> getCriteria()
    {
        return criterions;
    }

    public void addCriterion(Criterion criterion)
    {
        if (criterion == null) {
            throw new IllegalArgumentException("Criterion cannot be null");
        }
        criterions.add(criterion);
    }

}

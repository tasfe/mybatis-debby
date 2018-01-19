package com.debby.mybatis.mapping;

import com.debby.mybatis.annotation.MappingCompositeId;
import com.debby.mybatis.annotation.MappingId;

/**
 * @author rocky.hu
 * @date Jan 19, 2018 11:41:00 AM
 */
public class EntityWithMappingIdAndMappingCompositeId {

	@MappingId
	private int id;
	@MappingCompositeId
	private String name;
	private int age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}

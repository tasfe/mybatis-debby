package com.debby.mybatis.mapping;

import com.debby.mybatis.annotation.MappingId;

public class EntityWithMultipleMappingId {

	@MappingId
	private int id;
	@MappingId
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

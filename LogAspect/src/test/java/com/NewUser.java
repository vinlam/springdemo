package com;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUser {
	@JsonProperty("JsonPropertyName")
	private String name;
	private String sex;
	private Integer age;
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getSex() {
		return sex;
	}
 
	public void setSex(String sex) {
		this.sex = sex;
	}
 
	public Integer getAge() {
		return age;
	}
 
	public void setAge(Integer age) {
		this.age = age;
	}
 
	public NewUser(String name, String sex, Integer age) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
	}
 
	public NewUser() {
		super();
	}
 
	@Override
	public String toString() {
		return "User [name=" + name + ", sex=" + sex + ", age=" + age + "]";
	}
 
}
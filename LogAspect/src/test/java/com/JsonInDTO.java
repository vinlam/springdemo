package com;

public class JsonInDTO {
	private String name;
	private String age;
	private NewUser data;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}

	public NewUser getData() {
		return data;
	}

	public void setData(NewUser data) {
		this.data = data;
	}
}

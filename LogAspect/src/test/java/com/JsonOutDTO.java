package com;

public class JsonOutDTO {
	private String name;
	private String age;
	private NewUserDTO newUser;
	
	public NewUserDTO getNewUser() {
		return newUser;
	}
	public void setNewUser(NewUserDTO newUser) {
		this.newUser = newUser;
	}
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

}

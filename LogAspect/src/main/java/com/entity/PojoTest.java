package com.entity;

public class PojoTest {
	
	public PojoTest(String UserName,String Sex) {
		this.UserName = UserName;
		this.Sex = Sex;
	}
	
	@Override
	public String toString() {
		return "PojoTest [UserName=" + UserName + ", Sex=" + Sex + "]";
	}
	private String UserName = null;
	private String Sex = "";
	
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
}

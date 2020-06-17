package com;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUser {
	//@JsonProperty("JsonPropertyName")
	//例1:String ob = "{\"name\":\"jack\",\"age\":18,\"data\":{\"name\":\"tom\",\"age\":10,\"sex\":\"Man\"}}";
	//例2:String ob = "{\"name\":\"jack\",\"age\":18,\"data\":{\"Name\":\"tom\",\"age\":10,\"sex\":\"Man\"}}";
	//JavaType jType = JsonMapper.getInstance().createCollectionType(JsonDTO.class, NewUser.class);
	@JsonProperty("Name")//通过泛型反射此时注意对应数据key的大小写，如例1数据中为小写则数据为空name:""，如例2跟此定义一致则输出对应数据Name:tom
	//@JsonProperty("name")//JsonProperty别名
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
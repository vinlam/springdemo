package com.entry.xml.jxb;

import javax.xml.bind.annotation.*;

//@XmlRootElement(name = "user", namespace = "http://s3.amazonaws.com/doc/2006-03-01/")
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

	@XmlElement
	private Attr password;

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "age")
	private String age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Attr getPassword() {
		return password;
	}

	public void setPassword(Attr password) {
		this.password = password;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" + "password=" + password + ", name='" + name + '\'' + ", age='" + age + '\'' + '}';
	}
}

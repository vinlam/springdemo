package com.entity;

import javax.persistence.Entity;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.define.annotation.NewValidation;

@Entity
public class Person {
	private String id;
	@NotNull(message = "姓名不能为null")
	@NewValidation(value = {"浩哥","浩妹"})
	private String name;
	@NotNull(message = "性别不能为null")
	private String sex;
	@Range(min = 1, max = 150, message = "年龄必须在1-150之间")
	private int age;
	@Email(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*.\\w+([-.]\\w+)*$", message = "邮箱格式不正确")
	private String email;
	@Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "手机号格式不正确")
	private String phone;
	@URL(protocol = "http", host = "localhost", port = 80, message = "主页URL不正确")
	private String hostUrl;
	@AssertTrue(message = "怎么能没有工作呢？")
	private boolean isHasJob;
	private String isnull;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHostUrl() {
		return hostUrl;
	}
	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}
	public boolean isHasJob() {
		return isHasJob;
	}
	public void setHasJob(boolean isHasJob) {
		this.isHasJob = isHasJob;
	}
	public String getIsnull() {
		return isnull;
	}
	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}
}
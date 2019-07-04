package com.service;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	private String name;
	private String url;
	
	private String sex;

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
    public CustomerService() {
		// TODO Auto-generated constructor stub
	}
    
    public CustomerService(String sex) {
		// TODO Auto-generated constructor stub
    	this.sex = sex;
	}
    
	public void printName() {
		System.out.println("Customer name : " + this.name);
	}

	public void printURL() {
		System.out.println("Customer website : " + this.url);
	}

	public void printThrowException() {
		throw new IllegalArgumentException();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
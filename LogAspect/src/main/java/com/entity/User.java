package com.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@JsonIgnore
	@NotNull(message="Id不能为空")
	private Integer Id;
	@JsonIgnore
	private String name;
	
	@NotBlank(message="密码不能为空")
	private String password;
	
	public Integer getId() {
		return Id;
	}
	
	public void setId(Integer id) {
		this.Id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

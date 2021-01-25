package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Student")
public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7112480552295528591L;
	@Column(name ="id")
	private int id;
	@Column(name ="student_name")
	private String studentName;
	@Column(name ="email")
	private String email;
	@Column(name ="Dept_No")
	private int deptId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	@Override
	public String toString() {
		return "Student{" + "id=" + id + ", studentName='" + studentName + '\'' + ", email='" + email + '\''
				+ ", deptId=" + deptId + '}';
	}

}

package com.entity;

public class Student {
	private int id;
	private String studentName;
	private String email;
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

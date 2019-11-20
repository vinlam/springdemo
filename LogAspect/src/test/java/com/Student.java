package com;

import java.util.ArrayList;
import java.util.Arrays;

public class Student {
	private int id;
	private String name;
	private String sex;
	private ArrayList<String> array;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public ArrayList<String> getArray() {
		return array;
	}

	public void setArray(ArrayList<String> array) {
		this.array = array;
	}

	@Override
	public String toString() {
		return "Student{" + "id=" + id + ", name='" + name + '\'' + ", sex='" + sex + '\'' + ", array="
				+ Arrays.toString(array.toArray()) + '}';
	}
}

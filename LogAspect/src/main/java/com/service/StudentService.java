package com.service;

import java.util.List;

import com.entity.Student;

public interface StudentService {
	Student get(int id);
	void GoodBatchUpdate();
	
	int DelBatchStudent(List<Student> sList);
}

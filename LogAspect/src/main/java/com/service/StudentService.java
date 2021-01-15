package com.service;

import java.util.List;

import com.entity.Student;

public interface StudentService {
	Student get(int id);
	void GoodBatchUpdate();
	
	//线程模拟同一事务，先插入，后查询无结果
	void addStudentThread(Student s);
	
	//线程模拟同一事务，先插入，后查询改造后有返回结果
	void addStudentThreadSyncManager(Student s);
	
	int addStudent(Student s);
	
	int DelBatchStudent(List<Student> sList);
}

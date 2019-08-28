package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.StudentDao;
import com.entity.Student;
import com.service.StudentService;
@Service
public class StudentServiceImpl implements StudentService{
 
	@Autowired
	private StudentDao studentDao;
	@Override
	public Student get(int id) {
		Student s = studentDao.get(id);
		return s;
	}

	@Override
	@Transactional
	public void GoodBatchUpdate() {
		studentDao.GoodBatchUpdate();
	}
	
	@Override
	@Transactional
	public int DelBatchStudent(List<Student> sList) {
		return studentDao.batchDelete(sList);
	}

}

package com.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.entity.Student;

@Repository
public class StudentDao{
 
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    public Student get(int id)
    {
        String sql = "SELECT * FROM student WHERE id = ?";
        RowMapper<Student> rowMapper = BeanPropertyRowMapper.newInstance(Student.class);
        Student student = jdbcTemplate.queryForObject(sql,rowMapper,1);
        return student;
    }
    
    public  void GoodBatchUpdate() {
		String sql = "INSERT INTO student(id,student_name,email,Dept_No)VALUES(?,?,?,?)";
		final List<Object[]> batchArgs = new ArrayList<>();
		Random r = new Random(2);
		for(int j=1;j<1000000;j++) {
			batchArgs.add(new Object[] { 8+j, j+"EE", j+"ee@gmail.com", (r.nextInt(2)+1) });
		}
		Long s = System.currentTimeMillis();
		jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				Object[] args = batchArgs.get(i);
				ps.setString(1, (String)args[0].toString());
				ps.setString(2, (String)args[1]);
				ps.setString(3, (String)args[2]);
				ps.setString(4, (String)args[3].toString());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return batchArgs.size();
			}
		});
		Long e = System.currentTimeMillis();
		System.out.println("total:"+((e - s)*0.001));
	}
}

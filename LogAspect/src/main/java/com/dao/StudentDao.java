package com.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.entity.Student;

@Repository
public class StudentDao{
	private static Logger logger  = LoggerFactory.getLogger(StudentDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    public Student get(int id)
    {
        String sql = "SELECT * FROM student WHERE id = ?";
        RowMapper<Student> rowMapper = BeanPropertyRowMapper.newInstance(Student.class);
        Student student = jdbcTemplate.queryForObject(sql,rowMapper,1);
        return student;
    }
    
    public int addStudent(Student s)
    {
    	// 向数据库插入一条记录
        String sql = "INSERT INTO student(id,student_name,email,Dept_No)VALUES(?,?,?,?)";
        int i = jdbcTemplate.update(sql,new Object[] {s.getId(),s.getStudentName(),s.getEmail(),s.getDeptId()});
        // 根据id去查询获取 总数（若查询到了肯定是count=1）
        String query = "select count(1) from student where id = ?";
        Integer count = jdbcTemplate.queryForObject(query,new Object[] {s.getId()}, Integer.class);
        logger.info(i+"-----------"+count.toString());
        // 做其余的事情  可能抛出异常
        //System.out.println(1 / 0);
        return i;
    }
    
    public void addStudentThread(Student s)
    {
    	// 向数据库插入一条记录
    	String sql = "INSERT INTO student(id,student_name,email,Dept_No)VALUES(?,?,?,?)";
    	int i = jdbcTemplate.update(sql,new Object[] {s.getId(),s.getStudentName(),s.getEmail(),s.getDeptId()});
    	// 生产环境一般会把些操作交给线程池，此处我只是模拟一下效果而已~
        new Thread(() -> {
        	String query = "select count(1) from student where id = ?";
        	//count的值永远是0 //https://blog.csdn.net/f641385712/article/details/91538445
            Integer count = jdbcTemplate.queryForObject(query,new Object[] {s.getId()}, Integer.class);
            logger.info(i+"-----addStudentThread---count的值永远是0---"+count.toString());
        }).start();

        // 把问题放大
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    //异步线程的执行时，必须确保记录已经持久化到数据库,使用TransactionSynchronizationManager注册一个TransactionSynchronization然后在afterCommit里执行我们的后续代码，
    //这样就能100%确保我们的后续逻辑是在当前事务被commit后才执行的，完美的问题解决
    public void addStudentThreadSyncManager(Student s)
    {
    	// 向数据库插入一条记录
    	String sql = "INSERT INTO student(id,student_name,email,Dept_No)VALUES(?,?,?,?)";
    	int i = jdbcTemplate.update(sql,new Object[] {s.getId(),s.getStudentName(),s.getEmail(),s.getDeptId()});
    	// 生产环境一般会把些操作交给线程池，此处我只是模拟一下效果而已~
    	TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            // 在事务提交之后执行的代码块（方法）  此处使用TransactionSynchronizationAdapter，其实在Spring5后直接使用接口也很方便了~
            @Override
            public void afterCommit() {
                new Thread(() -> {
                	String query = "select count(1) from student where id = ?";
                    Integer count = jdbcTemplate.queryForObject(query,new Object[] {s.getId()}, Integer.class);
                    logger.info(i+"-----addStudentThreadSyncManager------"+count.toString());
                }).start();
            }
        });


        // 把问题放大
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    	
    	// 做其余的事情  可能抛出异常
    	//System.out.println(1 / 0);
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
    
    
    @Autowired
	// @Qualifier("njdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public int batchDelete(final List<Student> sList)
    {
        logger.info("batchDelete() begin, codeList.size="+sList.size());
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(sList.toArray());
        Long s = System.currentTimeMillis();
		
        int[] updatedCountArray = namedParameterJdbcTemplate.batchUpdate("delete from student where id=:id", batch);
        Long e = System.currentTimeMillis();
		System.out.println("total:" + ((e - s)));
		System.out.println("total:" + ((e - s) * 0.001));
        int sumInsertedCount = 0;
        for(int a: updatedCountArray)
        {
            sumInsertedCount+=a;
        }
        
        logger.info("batchInsert() end, sList.size="+sList.size()+",success deleted "+sumInsertedCount+" records");
        return sumInsertedCount;
    }
}

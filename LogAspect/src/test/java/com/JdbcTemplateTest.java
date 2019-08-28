package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import com.alibaba.fastjson.JSONObject;
import com.entity.MBUser;
import com.entity.Student;
import com.service.StudentService;
import com.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class JdbcTemplateTest {
	private static Logger logger  = LoggerFactory.getLogger(JdbcTemplateTest.class);
	@Autowired
	private DataSource ds;
    @Test
    public void test1() {

        // JDBC模板依赖于连接池来获得数据的连接，所以必须先要构造连接池
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setDriverClassName("com.mysql.jdbc.Driver");
//        //dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");// mysql 8
//        ds.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true");
//        ds.setUsername("test");
//        ds.setPassword("123456");

        // 创建JDBC模板
        JdbcTemplate jdbcTmp = new JdbcTemplate();
        // 这里也可以使用构造方法
        jdbcTmp.setDataSource(ds);

        // sql语句
        String sql = "select count(*)  from userinf_base";
        String sql2 = "select *  from userinf_base where cstno = ?";
        //Long num = (long) jdbcTemplate.queryForObject(sql, Long.class);
        //Class requiredType只支持基本数据类型的封装类，比如Integer.String等
//        所有在这里使用其他类型的时候需要将其转化一下,使用BeanPropertyRowMapper 
//        将上述代码转化成下列代码就可以正常查询了
        String cstno = "00000002002";
        //MBUser user = (MBUser) jdbcTmp.queryForObject(sql2,new Object[]{cstno}, new BeanPropertyRowMapper<MBUser>(MBUser.class));
        //System.out.println(JSONObject.toJSONString(user));
        //RowMapper<MBUser> rm = BeanPropertyRowMapper.newInstance(MBUser.class);
        //MBUser user = (MBUser) jdbcTmp.queryForObject(sql2,new Object[]{cstno},rm);
        //System.out.println(JSONObject.toJSONString(user));
        //System.out.println(num);
        final List<Object[]> batchArgs = new ArrayList<>();
		Random r = new Random(2);
		for (int j = 1; j < 100000; j++) {
			batchArgs.add(new Object[] { 8 + j, j + "EE", j + "ee@gmail.com", (r.nextInt(2) + 1) });
		}
		
		sql = "INSERT INTO student(id,student_name,email,Dept_No)VALUES(?,?,?,?)";
		Long s = System.currentTimeMillis();
		Connection conn = null;
		try {
			//conn = jdbcTmp.getDataSource().getConnection();
			//conn.setAutoCommit(false);

			jdbcTmp.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					Object[] args = batchArgs.get(i);
					ps.setString(1, (String) args[0].toString());
					ps.setString(2, (String) args[1]);
					ps.setString(3, (String) args[2]);
					ps.setString(4, (String) args[3].toString());
				}

				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return batchArgs.size();
				}
			});
			//conn.commit();
			Long e = System.currentTimeMillis();
			System.out.println("total:" + ((e - s)));
			System.out.println("total:" + ((e - s) * 0.001));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		}
    }
    
    @Autowired
    private UserService us;
    
    @Test
    public void autowireJdbcTemplate() {
    	MBUser user = (MBUser) us.findById("00000002002");
    	System.out.println(JSONObject.toJSONString(user));
//    	jdbcTemplate = new JdbcTemplate(dataSource);
//    	String sql = "select count(*)  from userinf_base";
//        String sql2 = "select *  from userinf_base where cstno = ?";
//        //Long num = (long) jdbcTemplate.queryForObject(sql, Long.class);
//        //Class requiredType只支持基本数据类型的封装类，比如Integer.String等
////        所有在这里使用其他类型的时候需要将其转化一下,使用BeanPropertyRowMapper 
////        将上述代码转化成下列代码就可以正常查询了
//        String cstno = "00000002002";
//        MBUser user = (MBUser) jdbcTemplate.queryForObject(sql2,new Object[]{cstno}, new BeanPropertyRowMapper<MBUser>(MBUser.class));
//        System.out.println(JSONObject.toJSONString(user));
    }
    
    @Autowired
    private StudentService ss;
    
    @Test
    //@Transactional
    public void testBatchDel() {
    	List<Student> list = new ArrayList<Student>();
    	Student s = null;
    	for (int j = 1; j < 10; j++) {
    		s = new Student();
    		s.setId(200 + j);
			list.add(s);
		}
    	//int count = batchDelete(list);
    	int count2 = ss.DelBatchStudent(list);
    	//System.out.println(count2+"---del:"+count);
    	System.out.println("---del:"+count2);
    }
    
    @Autowired
	// @Qualifier("njdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    int batchDelete(final List<Student> sList)
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
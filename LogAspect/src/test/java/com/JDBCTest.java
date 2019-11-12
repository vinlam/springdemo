package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.entity.Student;
import com.entity.UserRoleDTO;
import com.service.StudentService;
import com.util.JsonMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class JDBCTest {

//	private  ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//    private  JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
//    private  NamedParameterJdbcTemplate namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	// @Qualifier("njdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//	public void testDataSource() throws SQLException {
//		DataSource dataSource = ctx.getBean(DataSource.class);
//		System.out.println(dataSource.getConnection());
//	}

	/**
	 * 执行UPDATE,INSERT,DELETE
	 */
	@Test
	public void testUpdate() {
		String sql = "UPDATE student SET student_name = ? WHERE id = ?";
		jdbcTemplate.update(sql, "Kylin", 2);
	}

	/**
	 * 批量INSERT,UPDATE,DELETE 最后一个参数是Object[]的list类型,因为修改一条记录需要一个object数组,那么多条就需要多个
	 */
	@Test
	// @Transactional
	public void BatchUpdate() {
		String sql = "INSERT INTO student(id,student_name,email,Dept_No)VALUES(?,?,?,?)";
		List<Object[]> batchArgs = new ArrayList<>();
		batchArgs.add(new Object[] { 4, "AA", "aa@gmail.com", 1 });
		batchArgs.add(new Object[] { 5, "BB", "bb@gmail.com", 2 });
		batchArgs.add(new Object[] { 6, "CC", "cc@gmail.com", 1 });
		batchArgs.add(new Object[] { 7, "DD", "dd@gmail.com", 2 });
		batchArgs.add(new Object[] { 8, "EE", "ee@gmail.com", 1 });
		Random r = new Random(2);
		for (int j = 1; j < 10000; j++) {
			batchArgs.add(new Object[] { 8 + j, j + "EE", j + "ee@gmail.com", (r.nextInt(2) + 1) });
		}
		Long s = System.currentTimeMillis();
		jdbcTemplate.batchUpdate(sql, batchArgs);
		Long e = System.currentTimeMillis();
		System.out.println("total:" + ((e - s) * 0.001));
	}

	@Autowired
	private StudentService service;

	@Test
	public void student() {
		service.GoodBatchUpdate();
	}

	@Test
	public void getstudent() {
		Student s = service.get(8);
		System.out.println(JSON.toJSONString(s));

	}

	@Test
	// @Transactional
	public void GoodBatchUpdate() {

		final List<Object[]> batchArgs = new ArrayList<>();
//		batchArgs.add(new Object[] { 4, "AA", "aa@gmail.com", 1 });
//		batchArgs.add(new Object[] { 5, "BB", "bb@gmail.com", 2 });
//		batchArgs.add(new Object[] { 6, "CC", "cc@gmail.com", 1 });
//		batchArgs.add(new Object[] { 7, "DD", "dd@gmail.com", 2 });
//		batchArgs.add(new Object[] { 8, "EE", "ee@gmail.com", 1 });
		Random r = new Random(2);
		for (int j = 1; j < 10000; j++) {
			batchArgs.add(new Object[] { 8 + j, j + "EE", j + "ee@gmail.com", (r.nextInt(2) + 1) });
		}
		updateStudent(batchArgs);
	}

	@Test
	public void nBatchUpdate() {

		List<Student> batchArgs = new ArrayList<>();
		Random r = new Random(2);
		Student student = null;
		for (int j = 1; j < 1000; j++) {
			student = new Student();
			student.setStudentName(j + "EE");
			student.setId(8 + j);
			student.setEmail(j + "ee@gmail.com");
			student.setDeptId((r.nextInt(2) + 1));
			batchArgs.add(student);
		}
		njdbcBatchUpdate(batchArgs);
	}

	public void updateStudent(final List<Object[]> batchArgs) {
		String sql = "INSERT INTO student(id,student_name,email,Dept_No)VALUES(?,?,?,?)";
		Long s = System.currentTimeMillis();
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);

			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

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
			conn.commit();
			Long e = System.currentTimeMillis();
			System.out.println("total:" + ((e - s)));
			System.out.println("total:" + ((e - s) * 0.001));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void njdbcBatchUpdate(List<Student> list) {
		SqlParameterSource[] sqlParameterSources = SqlParameterSourceUtils.createBatch(list.toArray());
		String sql = "INSERT INTO student(id,student_name,email,Dept_No)VALUES(:id,:studentName,:email,:deptId)";
		Long s = System.currentTimeMillis();
		namedParameterJdbcTemplate.batchUpdate(sql, sqlParameterSources);
		Long e = System.currentTimeMillis();
		System.out.println("total:" + ((e - s)));
		System.out.println("total:" + ((e - s) * 0.001));
	}

	/**
	 * 从数据库中获取一条记录,实际得到一个对象 注意不是调用这个方法queryForObject(java.lang.String
	 * sql,java.lang.Class<T> requiredType,java.lang.Object... args) 需要调用中这个方法:
	 * queryForObject(java.lang.String sql,@NotNull
	 * org.springframework.jdbc.core.RowMapper<T> rowMapper,java.lang.Object...
	 * args)
	 * 1.其中的RowMapper指定如何去映射结果集的行,常用的实现类为BeanPropertyRowMapper<>(Student.class);
	 * 2.使用SQL中的列的别名完成列名和类的属性名的映射 3.不支持级联属性
	 */
	@Test
	public void testQueryForObject() {
		String sql = "SELECT id,student_name,email,Dept_No FROM student WHERE id = ?";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
		Student student = jdbcTemplate.queryForObject(sql, rowMapper, 1);
		System.out.println(student);
	}

	/**
	 * 查到实体类的集合 注意不是调用queryForList
	 */
	@Test
	public void testQueryForList() {
		String sql = "SELECT id, student_name,email,Dept_No FROM student WHERE id > ?";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
		List<Student> list = jdbcTemplate.query(sql, rowMapper, 9);
		System.out.println(list);

	}

	/**
	 * 获取单个列的值,或者做统计查询
	 */
	@Test
	public void testQueryForObject2() {
		String sql = "SELECT count(id) FROM student";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println(count);
	}

	/**
	 * 可以为参数取名字,参数多了更加便于维护 1.好处:若有多个参数,则不用再去对应位置,直接对应参数名,便于维护 2.缺点:较为麻烦
	 */
	@Test
	public void testNamedParameterJdbcTemplate() {
		String sql = "INSERT INTO student(id,student_name,email,Dept_No) VALUES(:id,:studentName,:email,:deptId)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", 9);
		paramMap.put("name", "Limbo");
		paramMap.put("email", "1610770854@qq.com");
		paramMap.put("deptId", 1);

		namedParameterJdbcTemplate.update(sql, paramMap);
	}

	/**
	 * 使用具名参数的时候,可以用update(java.lang.String
	 * sql,org.springframework.jdbc.core.namedparam.SqlParameterSource
	 * paramSource)进行更新操作 直接将一个对象作为参数 1.SQL语句中的参数名和类的属性一致
	 * 2.使用SqlParameterSource的BeanPropertySqlParameterSource 实现类作为参数
	 */
	@Test
	public void testNamedParameterJdbcTemplate2() {
		String sql = "INSERT INTO student(id,student_name,email,Dept_No) VALUES(:id,:studentName,:email,:deptId)";

		Student student = new Student();
		student.setStudentName("wyh");
		student.setId(10);
		student.setEmail("1610770854@qq.com");
		student.setDeptId(1);

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(student);
		namedParameterJdbcTemplate.update(sql, paramSource);
	}
	@Test
	public void getData() {
		String sql = "select r.id,r.role_name,r.remark,r.create_date ,ru.* from test.sys_role r left join test.sys_role_user ru on r.id = ru.role_id where r.id = ? and ru.user_id=? ";
		Object[] args = new Object[] {"dcb0f642fe9611e7b472201a068c6482","1ec421975ffe45229b48d4b9d712ff4f"};
		RowMapper<UserRoleDTO> rowMapper = new BeanPropertyRowMapper<>(UserRoleDTO.class);
		UserRoleDTO ur = jdbcTemplate.queryForObject(sql, rowMapper, args);
		System.out.println(JsonMapper.toJsonString(ur));
	}
	@Test
	public void getStudentData() {
		String sql = "select * from student s where s.student_name = ? ";
		Object[] args = new Object[] {"100EE"};
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);
		Student s = jdbcTemplate.queryForObject(sql, rowMapper, args);
		System.out.println(JsonMapper.toJsonString(s));
	}

	public void main(String[] args) {
		// testNamedParameterJdbcTemplate2();
		BatchUpdate();
	}

	private String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
	private String user = "test";
	private String password = "123456";

	@Test
	public void Test() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			String sql = "INSERT INTO student(id,student_name,email,Dept_No)VALUES(?,?,?,?)";

			pstm = conn.prepareStatement(sql);
			Long startTime = System.currentTimeMillis();
			conn.setAutoCommit(false);// 事务提交设置conn.setAutoCommit(false);

			Random r = new Random(2);
			for (int i = 1; i <= 1000000; i++) {
				pstm.setInt(1, 8 + i);
				pstm.setString(2, i + "EE");
				pstm.setString(3, i + "ee@gmail.com");
				pstm.setInt(4, (r.nextInt(2) + 1));
				pstm.addBatch();
			}
			pstm.executeBatch();// 批量操作
			conn.commit();// 提交事务
			Long endTime = System.currentTimeMillis();
			System.out.println("用时：" + (endTime - startTime));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}
}

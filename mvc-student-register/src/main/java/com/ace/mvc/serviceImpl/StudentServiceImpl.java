package com.ace.mvc.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ace.mvc.model.Course;
import com.ace.mvc.model.Student;
import com.ace.mvc.service.CourseService;
import com.ace.mvc.service.StudentService;

public class StudentServiceImpl implements StudentService {
	@Autowired
	CourseService courseService;

	@Override
	public boolean registerStudent(Student student) {
		var sql = "insert into student values (?,?,?,?,?,?,?,?)";
		var sql1 = "insert into record values (?,?)"; 
		int b = 0;
		try {
			b = jdbcTemplate.update(sql,student.getId(),student.getName(),student.getDob(),student.getGender(),student.getPhone(),student.getEducation(),getByteArray(student.getPhoto()),getOneStringFromArray(student.getCourse()));
		} catch (DataAccessException | IOException e) { 
			e.printStackTrace();
		}
		var courses = student.getCourse();
		var c =0;
		for(int i=0;i<courses.length; i++) {
			var foundcourse = courseService.findByName(courses[i]);
			 c = jdbcTemplate.update(sql1,student.getId(),foundcourse.get(0).getId());
		}
		if( b== 1) {
			return true;
		}
		return false;
	}

	@Override
	public List<Student> getAllStudent() {
		var sql = "select * from student";
		return jdbcTemplate.query(sql, mapRow());
	}

	@Override
	public List<Student> findByName(String name) {
		var sql = "select * from student where name = :name";
		var map = new MapSqlParameterSource().addValue("name", name);
		return namedParameterJdbcTemplate.query(sql, map,mapRow()); 
	}

	@Override
	public List<Student> findByCourse(String courses) {
		var sql = "select * from student where course like :course";
		var map = new MapSqlParameterSource().addValue("course", "%" + courses + "%");
		return namedParameterJdbcTemplate.query(sql, map,mapRow());
	}

	@Override
	public List<Student> findById(String id) {
		var sql = "select * from student where id = :id";
		var map = new MapSqlParameterSource().addValue("id", id);
		return namedParameterJdbcTemplate.query(sql, map,mapRow());
	}

	@Override
	public boolean updateStudet(Student student) {
		var sql = "update student set name = ?,dob = ?, gender = ?,phone = ?,education = ?,photo = ?,course = ? where id=?";
		var b = 0;
		try {
			 b = jdbcTemplate.update(sql,student.getName(),
										student.getDob(),student.getGender(),student.getPhone(),student.getEducation(),
										 getByteArray(student.getPhoto()),getOneStringFromArray(student.getCourse()),student.getId());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(b==1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteStudent(String id) {
		var sql1 = "delete from record where stu_id = ?";
		var a = jdbcTemplate.update(sql1,id);
		var sql = "delete from student where id = ?";
		var b = jdbcTemplate.update(sql,id);
		if(b== 1) {
			return true;
		}
		return false;
	}
 
	JdbcTemplate jdbcTemplate;
 
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public StudentServiceImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public String getOneStringFromArray(String[] array) {
		var arrays = "";
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				arrays += (array[i]).concat(",");
			}

		}
		return arrays.replaceAll(",$", "");
	}
	
	private byte[] getByteArray(MultipartFile file) throws IOException {
		InputStream is = file.getInputStream();
		byte[] byteArray = is.readAllBytes();
		return byteArray;
	}
	
	private RowMapper<Student> mapRow(){
		return new RowMapper<Student>() {
			
			@Override
			public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
				var student = new Student();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setPhone(rs.getString("phone"));
				student.setEducation(rs.getString("education"));
				try {
					student.setPhoto(new MockMultipartFile("photofile", new ByteArrayInputStream(rs.getBytes("photo"))));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				student.setCourse(rs.getString("course").split(","));
				return student;
			}
		};
	}
}
	
 
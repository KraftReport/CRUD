package com.ace.mvc.serviceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.ace.mvc.model.Course;
import com.ace.mvc.model.User;
import com.ace.mvc.service.CourseService;

public class CourseServiceImpl implements CourseService {
	
	JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public CourseServiceImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public boolean addCourse(Course course) {
		var sql = "insert into course values (?,?,?)";
		var b = jdbcTemplate.update(sql,course.getId(),course.getName(),course.getStatus());
		if(b==1) {
			return true;
		}
		return false;
	}

	@Override
	public List<Course> findById(String id) {
		List<Course> list = null;
		var sql = "select * from course where id = :id";
		var map = new MapSqlParameterSource().addValue("id", id);
		var b = namedParameterJdbcTemplate.query(sql, map, mapRow());
		if(b!= null) {
			return list=b;
		}
		return list;
	}

	@Override
	public List<Course> findByName(String name) {
		var sql = "select * from course where name = :name";
		var map = new MapSqlParameterSource().addValue("name", name);
		var b = namedParameterJdbcTemplate.query(sql,map, mapRow());
		if(b!= null) {
			return b;
		}
		return null;
	}

	@Override
	public List<Course> findByStatus(String status) {
		var sql = "select * from course where status = : status";
		var map = new MapSqlParameterSource().addValue("status", status);
		var b = namedParameterJdbcTemplate.query(sql, map,mapRow());
		if(b!=null) {
			return b;
		}
		return null;
	}

	@Override
	public boolean updateCourse(Course course) {
		var sql = "update course set name = :name,status =  :status where id = :id";
		var map = new MapSqlParameterSource();
		map.addValue("id", course.getId());
		map.addValue("name", course.getName());
		map.addValue("status", course.getStatus());
		var b = namedParameterJdbcTemplate.update(sql, map);
		if(b!=0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteCourse(String id) {
		var sql ="update course set status = :status where id = :id";
		var map = new MapSqlParameterSource().addValue("status", "closed").addValue("id", id);
		var b = namedParameterJdbcTemplate.update(sql,map);
		if(b!=0) {
			return true;
		}
		return false;
	}
	
	
	private RowMapper<Course> mapRow() {
		return new RowMapper<Course>() {

			@Override
			public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Course course = new Course();
				course.setId(rs.getString("id"));
				course.setName(rs.getString("name"));
				course.setStatus(rs.getString("status"));
				return course;
			}
		};
	}

	@Override
	public List<Course> getAllCourse() {
		var sql = "select * from course";
		var b = jdbcTemplate.query(sql,mapRow());
		if(b!=null) {
			return b;
		}
		return null;
	}


}

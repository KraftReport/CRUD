package com.ace.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ace.model.Course;

public class CourseService {

	private Connection connection;

	public CourseService(Connection connection) {
		this.connection = connection;
	}

	public boolean addCourse(Course course) {
		var b = false;
		var sql = "insert into course values (?,?)";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, course.getId());
			ps.setString(2, course.getName());
			var addCourseSuccess = ps.executeUpdate();
			if (addCourseSuccess == 1) {
				System.out.println("course is added successfully");
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public String findIdByName(String name) {
		var returnCourse = new Course();
		var sql = "select * from course where name  = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			var rs = ps.executeQuery();
			while (rs.next()) {
				returnCourse.setId(rs.getString("id"));
				returnCourse.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnCourse.getId();
	}
	

	public Course findById(Course course) {
		var returnCourse = new Course();
		var sql = "select * from course where id  = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, course.getId());
			var rs = ps.executeQuery();
			while (rs.next()) {
				returnCourse.setId(rs.getString("id"));
				returnCourse.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnCourse;
	}

	public List<Course> getAllCourse() {
		List<Course> courseList = new ArrayList<>();
		var sql = "select * from course";
		try {
			var ps = connection.prepareStatement(sql);
			var resultSet = ps.executeQuery();
			while (resultSet.next()) {
				var course = new Course();
				course.setId(resultSet.getString("id"));
				course.setName(resultSet.getString("name"));
				System.out.println(resultSet.getString("name"));
				courseList.add(course);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseList;

	}

	public boolean update(Course course) {
		var b = false;
		var sql = "update course set name = ? where id = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, course.getName());
			ps.setString(2, course.getId());
			var updateSuccess = ps.executeUpdate();
			if (updateSuccess == 1) {
				System.out.println("course is updated successfully");
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;

	}

	public boolean delete(Course course) {
		var b = false;
		var sql = "delete from course where id = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, course.getId());
			var deleteSuccess = ps.executeUpdate();
			if (deleteSuccess == 1) {
				System.out.println("course is deleted successfully");
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;

	}
}

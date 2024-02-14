package com.ace.mvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ace.mvc.model.Course;

@Service
public interface CourseService {

	public boolean addCourse(Course course);
	
	public List<Course> findById(String id);
	
	public List<Course> getAllCourse();
	
	public List<Course> findByName(String name);
	
	public List<Course> findByStatus(String status);
	
	public boolean updateCourse(Course course);
	
	public boolean deleteCourse(String id);
}

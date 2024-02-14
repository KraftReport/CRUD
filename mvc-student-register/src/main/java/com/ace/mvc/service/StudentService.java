package com.ace.mvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ace.mvc.model.Course;
import com.ace.mvc.model.Student;

@Service
public interface StudentService {

	public boolean registerStudent(Student student);
	
	public List<Student> getAllStudent();
	
	public List<Student> findByName(String name);
	
	public List<Student> findByCourse(String course);
	
	public List<Student> findById(String id);
	
	public boolean updateStudet(Student student);
	
	public boolean deleteStudent(String id);
	
	public String getOneStringFromArray(String[] array);
}

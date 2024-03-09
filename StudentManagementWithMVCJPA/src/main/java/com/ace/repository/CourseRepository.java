package com.ace.repository;

import com.ace.entity.Course;
import com.ace.entity.Status;
import com.ace.entity.Student;

import java.util.List;

public interface CourseRepository {
    public void addCourse(Course course);
    public void updateCourse(Course course);
    public void deleteCourse(int id);
    public List<Course> getAllCourse();
    public Course findByName(String name);
    public List<Course> findByNameLike(String name);
    public Course findById(int id);
    public List<Course> findByStatus(String status);
    public List<Course> findByStudent(Student student);
}

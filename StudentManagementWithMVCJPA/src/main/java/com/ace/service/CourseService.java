package com.ace.service;

import com.ace.entity.Course;
import com.ace.entity.Student;
import com.ace.model.CourseModel;

import java.util.List;

public interface CourseService {
    public boolean addCourse(CourseModel course);
    public boolean updateCourse(CourseModel course);
    public boolean deleteCourse(CourseModel course);
    public List<CourseModel> findAllCourse();
    public List<CourseModel> findByNameLike(String name);
    public CourseModel findByName(String name);
    public CourseModel findById(int id);
    public List<CourseModel> findByStatus(String status);
    public List<CourseModel> findByStudent(Student student);
}

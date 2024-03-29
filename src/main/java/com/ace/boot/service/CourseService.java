package com.ace.boot.service;

import com.ace.boot.entity.Course;

import java.util.List;
public interface CourseService {
    boolean registerCourse(Course course);
    boolean updateDescriptionAndNameAndLevel(Course course);
    boolean closeCourseOpenCourse(Long id);
    List<Course> getAllCourse();
    Course findById(Long id);
    List<Course> searchMethod(Long id, String name,String level);
}

package com.ace.service;

import com.ace.entity.Course;

import java.util.List;

public interface CourseService {
    public List<Course> findByName(String s);

    public List<Course> getAllCourse();

    public boolean registerCourse(Course course);

    public boolean updateCourse(Course course);

    public boolean closeCourse(Course course);


}

package com.ace.repository;

import com.ace.entity.Course;

import java.util.List;

public interface CourseRepository {

    public void addCourse(Course course);

    public void updateCourse(Course course);

    public void deleteCourse(int id);

    public List<Course> getAllCourse();

    public List<Course> getById(int id);

    public List<Course> getByName(String name);
}

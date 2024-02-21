package com.ace.service;

import com.ace.entity.Course;
import com.ace.repository.CourseRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public List<Course> findByName(String s) {
        return courseRepository.getByName(s);
    }
    @Override
    public List<Course> getAllCourse() {
        return courseRepository.getAllCourse();
    }

    @Override
    public boolean registerCourse(Course course) {
        try {
            courseRepository.addCourse(course);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCourse(Course course) {
        try {
            courseRepository.updateCourse(course);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean closeCourse(Course course) {
        try {
            var found = courseRepository.getById(course.getId()).get(0);
            found.setStatus("close");
            courseRepository.updateCourse(found);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

package com.ace.service;

import com.ace.entity.Course;
import com.ace.entity.Status;
import com.ace.entity.Student;
import com.ace.model.CourseModel;
import com.ace.repository.CourseRepository;
import com.ace.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public boolean addCourse(CourseModel course) {
        try {
            courseRepository.addCourse(changeCourseModelToCourse(course));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean updateCourse(CourseModel course) {
        try {
            courseRepository.updateCourse(changeCourseModelToCourse(course));
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCourse(CourseModel course) {
        try {
            courseRepository.deleteCourse(course.getId());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<CourseModel> findAllCourse() {
        try {
            var found = courseRepository.getAllCourse();
            List<CourseModel> courseModelList = new ArrayList<>();
            for(int i =0;i<found.size();i++){
                courseModelList.add(changeCourseToCourseModel(found.get(i)));
            }
            return courseModelList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CourseModel> findByNameLike(String name) {
        try {
            var found = courseRepository.findByNameLike(name);
            List<CourseModel> courses = new ArrayList<>();
            for(int i =0 ;i<found.size(); i++){
                courses.add(changeCourseToCourseModel(found.get(i)));
            }
            return courses;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CourseModel findByName(String name) {
        try {
            return changeCourseToCourseModel(courseRepository.findByName(name));
        }catch (Exception c){
            c.printStackTrace();
        }
        return null;
    }

    @Override
    public CourseModel findById(int id) {
        try {
            return changeCourseToCourseModel(courseRepository.findById(id));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CourseModel> findByStatus(String status) {
        try {
              var found = courseRepository.findByStatus(status);
            List<CourseModel> courses = new ArrayList<>();
            for(int i =0 ;i<found.size(); i++){
                courses.add(changeCourseToCourseModel(found.get(i)));
            }
            return courses;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CourseModel> findByStudent(Student student) {
        try {
            var found = courseRepository.findByStudent(student);
            List<CourseModel> courses = new ArrayList<>();
            for(int i =0 ;i<found.size(); i++){
                courses.add(changeCourseToCourseModel(found.get(i)));
            }
            return courses;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private CourseModel changeCourseToCourseModel(Course course) {
        var courseModel = new CourseModel();
        courseModel.setId(course.getId());
        courseModel.setName(course.getName());
        courseModel.setStatus(course.getStatus().toString());
        return courseModel;
    }

    private Course changeCourseModelToCourse(CourseModel courseModel){
        var course = new Course();
        course.setName(courseModel.getName());
        course.setId(courseModel.getId());
        course.setStatus(Status.valueOf(courseModel.getStatus()));
        course.setStudents(studentRepository.findByCourse(courseModel.getId()));
        return course;
    }
}

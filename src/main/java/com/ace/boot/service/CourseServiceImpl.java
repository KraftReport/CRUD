package com.ace.boot.service;

import com.ace.boot.entity.Course;
import com.ace.boot.entity.Status;
import com.ace.boot.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public boolean registerCourse(Course course) {
        try {
            courseRepository.save(course);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateDescriptionAndNameAndLevel(Course course) {
        try {
            var found = courseRepository.findById(course.getId()).orElseThrow(()->new RuntimeException("not found course"));
            found.setName(course.getName());
            found.setDescription(course.getDescription());
            found.setLevel(course.getLevel());
            courseRepository.save(found);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public boolean closeCourseOpenCourse(Long id) {
        try {
            var found = courseRepository.findById(id).orElseThrow(()->new RuntimeException("course id not found"));
            if(found.getStatus()!=null){
                found.setStatus(null);
            }else {
                found.setStatus(Status.ACTIVE);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(()->new RuntimeException("course not found"));
    }

    @Override
    public List<Course> searchMethod(Long id, String name, String level) {
        List<Specification<Course>> specification = new ArrayList<>();
        if(id!=null){
            specification.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"),id));
        }
        if(StringUtils.hasLength(name)){
            specification.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),"%".concat(name.toLowerCase().concat("%"))));
        }
        if(StringUtils.hasLength(level)){
            specification.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("level")),"%".concat(level.toLowerCase().concat("%"))));
        }
        Specification<Course> courseSpecification = Specification.where(null);
        for(var s :specification){
            courseSpecification = courseSpecification.and(s);
        }
        return courseRepository.findAll(courseSpecification);
    }
}

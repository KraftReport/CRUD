package com.ace.boot.service;

import com.ace.boot.entity.*;
//import com.ace.boot.repository.EnrollRepository;
import com.ace.boot.repository.EnrollRepository;
import com.ace.boot.repository.StudentRepository;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EnrollRepository enrollRepository;
    @Override
    @Transactional
    public boolean registerStudent(Student student) {
        try {
            student.setPhoto(userService.changeMultipartFileToBlob(student.getFile()));
            saveToEnroll(student);
            studentRepository.save(student);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateStudent(Student student) throws SQLException, IOException {
        System.err.println(student.getFile());
        try {
            if(student.getFile()==null){
                student.setFile(studentRepository
                        .findById(student.getId())
                        .orElseThrow(()->new RuntimeException("student not found"))
                        .getFile());
                studentRepository.save(student);
            }
            saveToEnroll(student);
            student.setPhoto(userService.changeMultipartFileToBlob(student.getFile()));
            studentRepository.save(student);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public boolean removeStudent(Long id) {
        try {
            var found = studentRepository.findById(id).orElseThrow(()->new RuntimeException("student not found"));
            if(found.getStatus()!=null){
                found.setStatus(null);
                return true;
            }else {
                found.setStatus(Status.ACTIVE);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Student> allStudents() throws SQLException, IOException {
        return addCourses(addPhotoString(studentRepository.findAll()));
    }



    @Override
    public List<Student> searchMethod(Long id, String name, String course) throws SQLException, IOException {
        List<Specification<Student>> specifications= new ArrayList<>();
        if(id!=null){
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"),id));
        }
        if(StringUtils.hasLength(name)){
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder
                                    .lower(root.get("name")),"%".concat(name.toLowerCase()).concat("%")));
        }
        if(StringUtils.hasLength(course)){
            specifications.add(getCourse(course));
        }
        Specification<Student> studentSpecification = Specification.where(null);
        for(var s :specifications){
            studentSpecification = studentSpecification.and(s);
        }
        return addCourses(addPhotoString(studentRepository.findAll(studentSpecification)));
    }

    public List<Student> addPhotoString(List<Student> students) throws SQLException, IOException {
        for(var s :students){
            s.setPhotoString(userService.getEncodedPhotoString(userService.changeBlobToMultipartfile(s.getPhoto())));
        }
        return students;
    }

    public List<Student> addCourses(List<Student> students) throws SQLException, IOException {
        for(int i =0; i<students.size();i++){
            var line = "";
            List<Course> list = new ArrayList<>();
            var enrollList = enrollRepository.findByStudentId(students.get(i).getId());
            for(int j=0;j<enrollList.size();j++){
                var course = new Course();
                course.setId(enrollList.get(j).getCourse().getId());
                course.setName(enrollList.get(j).getCourse().getName());
                list.add(course);
                line+=enrollList.get(j).getCourse().getName()+",";
            }
            students.get(i).setFile(null);
            students.get(i).setCourseList(list);
            students.get(i).setCourseString(line);
            System.err.println(line);
        }
        return students;
    }

    public static Specification<Student> getCourse(String name){
        return (root, query, criteriaBuilder) -> {
            if(StringUtils.hasLength(name)){
                Join<Student, Enroll> studentJoin = root.join("enrollList");
                Join<Enroll, Course> courseJoin = studentJoin.join("course");
                return criteriaBuilder.like(criteriaBuilder.lower(courseJoin.get("name")),"%".concat(name.toLowerCase()).concat("%"));
            }else{
                return null;
            }
        };
    }
    @Transactional
    private void saveToEnroll(Student student){
        enrollRepository.deleteAll(enrollRepository.findByStudentId(student.getId()));
        for(int i = 0 ; i < student.getCourses().length; i++){
            var enroll = new Enroll();
            var course = new Course();
            course.setId(student.getCourses()[i]);
            enroll.setStudent(student);
            enroll.setCourse(course);
            enrollRepository.save(enroll);
        }
    }
}

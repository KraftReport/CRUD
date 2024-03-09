package com.ace.model;

import com.ace.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel {
    private int id;
    private String name;
    private String dob;
    private String phone;
    private String gender;
    private String status;
    private String  education;
    private String[] courses;
    private List<Course> courseList;
    private List<CourseModel> courseModelList;
    private MultipartFile photo;
    private String photoString;
}

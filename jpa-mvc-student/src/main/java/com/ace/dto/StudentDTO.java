package com.ace.dto;

import com.ace.entity.Course;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class StudentDTO {
    private int id;
    private String name;
    private String dob;
    private String gender;
    private String phone;
    private String education;
    private String[] course;
    private List<Course> courseList;
    private MultipartFile photo;
    private String photoString;
}

package com.ace.boot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.Date;
import java.util.List;

@Entity(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name required")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "phone required")
    @Column(nullable = false)
    private String phone;
    @NotBlank(message = "birthday required")
    private Date dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Education education;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Blob photo;
    @Transient
    private MultipartFile file;
    @Transient
    private String photoString;
    @OneToMany(mappedBy = "student")
    private List<Enroll> enrollList;
    @Transient
    private Long[] courses;
    @Transient
    private List<Course> courseList;
    @Transient
    private String courseString;
}

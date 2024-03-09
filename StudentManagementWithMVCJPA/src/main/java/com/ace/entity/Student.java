package com.ace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "student")
@Table(name = "student")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Education education;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Transient
    private byte[] photos;
    private Blob photo;
    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = {@JoinColumn(name = "students_id")},
            inverseJoinColumns = {@JoinColumn(name = "courses_id")})
    private List<Course> courses;
}

package com.ace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "course")
@Table(name = "course")
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}

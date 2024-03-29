package com.ace.boot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "enroll")
@Table(name = "enroll")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Enroll(long l, long l1) {
    }
}

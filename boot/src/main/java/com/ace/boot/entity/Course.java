package com.ace.boot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Entity(name = "course")
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name required")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "description required")
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private Level level;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "course")
    private List<Enroll> enrollList;

    public Course(long l, String java) {
    }
}

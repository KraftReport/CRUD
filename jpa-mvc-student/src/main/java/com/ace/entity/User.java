package com.ace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}

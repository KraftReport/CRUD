package com.ace.repository;

import com.ace.entity.Student;

import java.util.List;

public interface StudentRepository {

    public void registerStudent(Student student);

    public void updateStudent(Student student);

    public void deleteStudent(int id);

    public List<Student> getAllStudents();

    public List<Student> getById(int id);

    public List<Student> getByName(String name);

}

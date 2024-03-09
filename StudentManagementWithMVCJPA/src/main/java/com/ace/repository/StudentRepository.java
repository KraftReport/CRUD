package com.ace.repository;

import com.ace.entity.Student;

import java.util.List;

public interface StudentRepository {
    public void registerStudent(Student student);
    public void updateStudent(Student student);
    public void deleteStudent(int id);
    public List<Student> getAllStudents();
    public Student findById(int id);
    public List<Student> findByCourse(int id);
    public List<Student> findByNameLike(String name);
    public List<Student> findByStatus(String  status);
    public List<Student> findByName(String name);
    public List<Student> findByCriteria(Student student);

}

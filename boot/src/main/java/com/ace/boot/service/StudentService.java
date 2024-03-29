package com.ace.boot.service;

import com.ace.boot.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StudentService {
    boolean registerStudent(Student student);
    boolean updateStudent(Student student) throws SQLException, IOException;
    boolean removeStudent(Long id);
    List<Student> allStudents() throws IOException, SQLException;
    List<Student> searchMethod(Long id,String name,String course) throws SQLException, IOException;
}

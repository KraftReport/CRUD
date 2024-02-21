package com.ace.service;

import com.ace.dto.StudentDTO;
import com.ace.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    public boolean registerStudent(StudentDTO studentDTO) throws IOException, SQLException;

    public boolean updateStudent(StudentDTO studentDTO) throws SQLException, IOException;

    public boolean deleteStudent(StudentDTO studentDTO);

    public List<StudentDTO> getAllStudents() throws SQLException, IOException;

    public List<StudentDTO> findById(int id) throws SQLException, IOException;

    public List<StudentDTO> findByName(String name) throws SQLException, IOException;

    public List<StudentDTO> findByCourse(String courseName) throws SQLException, IOException;
}

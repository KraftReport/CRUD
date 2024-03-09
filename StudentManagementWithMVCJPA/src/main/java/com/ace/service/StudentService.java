package com.ace.service;

import com.ace.entity.Student;
import com.ace.model.StudentModel;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StudentService {
    public boolean registerStudent(StudentModel student);
    public boolean updateStudent(StudentModel student);
    public boolean deleteStudent(StudentModel student);
    public List<StudentModel> getAllStudent();
    public StudentModel findById(int id);
    public List<StudentModel> findByCourse(int id);
    public List<StudentModel> findByNameLike(String name);
    public List<StudentModel> findByStatus(String  status);
    public List<StudentModel> findByName(String name);
    public List<StudentModel> findByStatusAndCourse(String course,String status);
    public void getPdf(HttpServletResponse httpServletResponse) throws JRException, IOException, SQLException;
    public void getExcel(HttpServletResponse httpServletResponse);
}

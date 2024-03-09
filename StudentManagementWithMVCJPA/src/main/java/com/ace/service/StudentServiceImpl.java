package com.ace.service;

import com.ace.entity.*;
import com.ace.model.StudentModel;
import com.ace.repository.CourseRepository;
import com.ace.repository.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;



@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public boolean registerStudent(StudentModel student) {
        try {
            System.out.println(student.getCourses()[0]);
            var one = changeStudentModelToStudent(student);
//            one.getCourses().get(0).setId(1);
//            System.out.println(one.getId());
            studentRepository.registerStudent(one);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStudent(StudentModel student) {
        try {
            if(student.getPhoto().isEmpty()){
                student.setPhoto(changeStudentToStudentModel(studentRepository.findById(student.getId())).getPhoto());
            }
            studentRepository.updateStudent(changeStudentModelToStudent(student));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteStudent(StudentModel student) {
        try {
            var student1 = new Student();
            student1.setId(student.getId());
            studentRepository.deleteStudent(student1.getId());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<StudentModel> getAllStudent() {
        try {
           var list =  studentRepository.getAllStudents();
           List<StudentModel> studentModels = new ArrayList<>();
           for(int i = 0; i<list.size(); i++){
               studentModels.add(changeStudentToStudentModel(list.get(i)));
           }
           return studentModels;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StudentModel findById(int id) {
        try {
           return changeStudentToStudentModel(studentRepository.findById(id)) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentModel> findByCourse(int id) {
        try{
            var found = studentRepository.findByCourse(id);
            List<StudentModel> students = new ArrayList<>();
            for(int i = 0; i<found.size(); i++){
                students.add(changeStudentToStudentModel(found.get(i)));
            }
            return students;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentModel> findByNameLike(String name) {
        try {
            var found = studentRepository.findByNameLike(name);
            List<StudentModel> studentModels = new ArrayList<>();
            for(int i =0 ;i<found.size(); i++){
                studentModels.add(changeStudentToStudentModel(found.get(i)));
            }
            return studentModels;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentModel> findByStatus(String status) {
        try {
            var found = studentRepository.findByStatus(status);
            List<StudentModel> studentModels = new ArrayList<>();
            for(int i = 0 ; i<found.size(); i++){
                studentModels.add(changeStudentToStudentModel(found.get(i)));
            }
            return studentModels;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentModel> findByName(String name) {
        try {
            var found = studentRepository.findByName(name);
            List<StudentModel> studentModels = new ArrayList<>();
            for(int i =0 ;i<found.size(); i++){
                studentModels.add(changeStudentToStudentModel(found.get(i)));
            }
            return studentModels;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentModel> findByStatusAndCourse(String course, String status) {
        try {
            var list1 = studentRepository.findByCourse(courseRepository.findByName(course).getId());
            var list2 = studentRepository.findByStatus(status);
            var list3 = new ArrayList<StudentModel>();
            for(int i = 0; i<list2.size(); i++){
                if(list1.contains(list2.get(i))){
                    list3.add(changeStudentToStudentModel(list2.get(i)));
                }
            }
            return list3;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getPdf(HttpServletResponse httpServletResponse) throws JRException, IOException, SQLException {
        var print = getPrint();
        var out = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print,out);
        out.flush();
        out.close();
    }

    @Override
    public void getExcel(HttpServletResponse httpServletResponse) {

    }

    private Student changeStudentModelToStudent(StudentModel studentModel) throws IOException, SQLException {
        var student = new Student();
        List<Course> courses = new ArrayList<>();
        var courseList = studentModel.getCourses();
        student.setId(studentModel.getId());
        student.setName(studentModel.getName());
        student.setDob(LocalDate.parse(studentModel.getDob()));
        student.setPhone(studentModel.getPhone());
        student.setGender(Gender.valueOf(studentModel.getGender()));
        student.setStatus(Status.valueOf(studentModel.getStatus()));
        student.setEducation(Education.valueOf(studentModel.getEducation()));
        for (int i = 0; i < courseList.length; i++) {
            courses.add(courseRepository.findByName(courseList[i]));
        }
        student.setCourses(courses);
        student.setPhoto(new SerialBlob(studentModel.getPhoto().getInputStream().readAllBytes()));
        return student;
    }

    private StudentModel changeStudentToStudentModel(Student student) throws SQLException, IOException {
        var studentModel = new StudentModel();
        studentModel.setId(student.getId());
        studentModel.setName(student.getName());
        studentModel.setPhone(student.getPhone());
        studentModel.setDob(student.getDob().toString());
        studentModel.setGender(student.getGender().toString());
        studentModel.setEducation(student.getEducation().toString());
        studentModel.setStatus(student.getStatus().toString());
        studentModel.setCourseList(courseRepository.findByStudent(student));
        studentModel.setPhoto(new MockMultipartFile("photofile", new ByteArrayInputStream(student.getPhoto().getBytes(1L, (int) student.getPhoto().length()))));
        studentModel.setPhotoString(Base64.getEncoder().encodeToString(studentModel.getPhoto().getBytes()));
        return studentModel;
    }

    private JasperPrint getPrint() throws JRException, FileNotFoundException, SQLException {
        var students = studentRepository.getAllStudents();
        var file = ResourceUtils.getFile("classpath:student.jrxml");
        var report = JasperCompileManager.compileReport(file.getAbsolutePath());
        var source = new JRBeanCollectionDataSource(students);
        Map<String,Object> map = new HashMap<>();
        map.put("created by","myo set paing");
        var print = JasperFillManager.fillReport(report,map,source);
        return print;
    }
}

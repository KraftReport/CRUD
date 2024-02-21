package com.ace.service;

import com.ace.dto.StudentDTO;
import com.ace.entity.Course;
import com.ace.entity.Student;
import com.ace.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public boolean registerStudent(StudentDTO studentDTO) throws IOException, SQLException {
        var courseArray = studentDTO.getCourse();
        List<Course> courseList = formArrayToArrayList(courseArray);
        var student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setDob(studentDTO.getDob());
        student.setPhone(studentDTO.getPhone());
        student.setGender(studentDTO.getGender());
        student.setEducation(studentDTO.getEducation());
        student.setCourses(courseList);
        student.setPhoto(fromMultipartToBlob(studentDTO.getPhoto()));
        try {
            studentRepository.registerStudent(student);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws SQLException, IOException {
        var student = new Student();
        student.setName(studentDTO.getName());
        student.setDob(studentDTO.getDob());
        student.setGender(studentDTO.getGender());
        student.setPhone(studentDTO.getPhone());
        student.setId(studentDTO.getId());
        student.setEducation(studentDTO.getEducation());
        student.setCourses(formArrayToArrayList(studentDTO.getCourse()));
        student.setPhoto(fromMultipartToBlob(studentDTO.getPhoto()));
        try{
            studentRepository.updateStudent(student);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStudent(StudentDTO studentDTO) {
        try{
            studentRepository.deleteStudent(studentDTO.getId());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<StudentDTO> getAllStudents() throws SQLException, IOException {
         return fromStudentToStudentDTO(studentRepository.getAllStudents());
    }

    @Override
    public List<StudentDTO> findById(int id) throws SQLException, IOException {
        return fromStudentToStudentDTO(studentRepository.getById(id));
    }

    @Override
    public List<StudentDTO> findByName(String name) throws SQLException, IOException {
        return fromStudentToStudentDTO(studentRepository.getByName(name));
    }

    @Override
    public List<StudentDTO> findByCourse(String courseName) throws SQLException, IOException {
        var studentList = studentRepository.getAllStudents();
        List<Student> returnList = new ArrayList<>();
        for(int i =0; i<studentList.size(); i++){
            for(int j =0 ; j<studentList.get(i).getCourses().size(); j++){
                if(studentList.get(i).getCourses().get(j).getName().equals(courseName)){
                        returnList.add(studentList.get(i));
                }
            }
        }
        return fromStudentToStudentDTO(returnList);
    }

    private List<Course> formArrayToArrayList(String[] array){
        List<Course> courseList = new ArrayList<>();
        for(int i =0 ; i<array.length; i++){
            courseList.add(courseService.findByName(array[i]).get(0));
        }
        return courseList;
    }

    private SerialBlob fromMultipartToBlob(MultipartFile photo) throws IOException, SQLException {
        return new SerialBlob(photo.getInputStream().readAllBytes());
    }

    private MultipartFile fromBolbToMultipartFile(Blob photo) throws SQLException, IOException {
       return new MockMultipartFile("photofile",new ByteArrayInputStream(photo.getBytes(1L,(int)photo.length())));
    }

    private  String fromMultipartFileToString(MultipartFile file) throws IOException {
        return Base64.getEncoder().encodeToString(file.getBytes());
    }

    private List<StudentDTO> fromStudentToStudentDTO(List<Student> student) throws SQLException, IOException {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for(int i = 0 ;i <student.size() ; i++){
            var studentDTO = new StudentDTO();
            studentDTO.setId(student.get(i).getId());
            studentDTO.setName(student.get(i).getName());
            studentDTO.setDob(student.get(i).getDob());
            studentDTO.setPhone(student.get(i).getPhone());
            studentDTO.setEducation(student.get(i).getEducation());
            studentDTO.setGender(student.get(i).getGender());
            studentDTO.setCourseList(student.get(i).getCourses());
            studentDTO.setPhoto(fromBolbToMultipartFile(student.get(i).getPhoto()));
            studentDTO.setPhotoString(fromMultipartFileToString(fromBolbToMultipartFile(student.get(i).getPhoto())));
            studentDTOList.add(studentDTO);
        }
        return  studentDTOList;
    }

}

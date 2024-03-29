package com.ace.boot.controller;

import com.ace.boot.entity.*;
import com.ace.boot.repository.CourseRepository;
import com.ace.boot.repository.StudentRepository;
import com.ace.boot.repository.UserRepository;
import com.ace.boot.service.CourseServiceImpl;
import com.ace.boot.service.StudentServiceImpl;
import com.ace.boot.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @InjectMocks
    UserServiceImpl userService;
    @InjectMocks
    StudentServiceImpl studentService;
    @InjectMocks
    CourseServiceImpl courseService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    CourseRepository courseRepository;
    User user = new User();
    Course course = new Course();
    Student student = new Student();
    List<Student> students = new ArrayList<>();
    List<Course> courses = new ArrayList<>();
    List<User> users = new ArrayList<>();
    @Mock
    SecurityContextHolder securityContextHolder;
    @Captor
    ArgumentCaptor<Specification<User>> userCaptor;
    @Captor
    ArgumentCaptor<Specification<Student>> studentCaptor;
    @Captor
    ArgumentCaptor<Specification<Course>> courseCaptor;
    @Mock
    ObjectMapper objectMapper;
    @Mock
    Authentication authentication;
    @BeforeEach
    void setUp() throws SQLException, JRException {
        openMocks(this);
        course.setId(1L);
        course.setName("java");
        course.setStatus(Status.ACTIVE);
        course.setLevel(Level.BASIC);
        course.setDescription("java basic course");
        courses.add(course);
        when(courseRepository.findAll(courseCaptor.capture())).thenReturn(courses);
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseRepository.findById(course.getId())).thenReturn(Optional.ofNullable(course));
        student.setId(1L);
        student.setName("msp");
        student.setDob(new Date(2002,12,26));
        student.setStatus(Status.ACTIVE);
        student.setGender(Gender.MALE);
        student.setEducation(Education.CERTIFICATE);
        student.setCourseList(List.of(new Course(1L,"java")));
        student.setEnrollList(List.of(new Enroll(1L,1L)));
        student.setFile(new MockMultipartFile("test.txt", "Hello, World!".getBytes()));
        student.setPhoto(new SerialBlob(new byte[0]));
        student.setCourseString("java");
        student.setCourses(new  Long[]{1L});
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
        when(studentRepository.findAll()).thenReturn(students);
        when(studentRepository.findAll(studentCaptor.capture())).thenReturn(students);
        user.setId(1L);
        user.setName("admin");
        user.setEmail("test@gmail.com");
        user.setPassword("testpassword");
        user.setAddress("testaddress");
        user.setRole(Role.ADMIN);
        user.setFile(new MockMultipartFile("test.txt", "Hello, World!".getBytes()));
        user.setPhoto(new SerialBlob(new byte[0]));
        users.add(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findAll(userCaptor.capture())).thenReturn(users);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(users);
//        when(securityContextHolder.getContext().getAuthentication().getName()).thenReturn(users.toString());
        doNothing().when(userRepository).delete(user);
    }

    @Test
    void updateUser() throws Exception {
        mockMvc.perform(get("/member/userUpdate").param("id","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/userUpdate"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void getStudentManager() throws Exception {
        mockMvc.perform(get("/member/studentManager"))
                .andExpect(status().isOk())
                .andExpect(view().name("/student/studentManager"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student",studentService.allStudents()))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attribute("course",courseService.getAllCourse()));
    }

    @Test
    void getStudentRegister() throws Exception {
        mockMvc.perform(get("/member/studentRegister"))
                .andExpect(status().isOk())
                .andExpect(view().name("/student/studentRegister"))
                .andExpect(model().attribute("course",courseService.getAllCourse()))
                .andExpect(model().attribute("student",new Student()));
    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void profile() throws Exception {
//        mockMvc.perform(get("/member/profile"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/profile"));
//    }

    @Test
    void courseManager() throws Exception {
        mockMvc.perform(get("/member/courseManager"))
                .andExpect(status().isOk())
                .andExpect(view().name("/course/courseManager"))
                .andExpect(model().attribute("course",courseService.getAllCourse()));
    }

    @Test
    void changePassword() throws Exception {
        mockMvc.perform(get("/member/changePassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("changePassword"))
                .andExpect(model().attribute("showForgot","Request to change password"));
    }

    @Test
    void closeCourse() throws Exception {
        mockMvc.perform(get("/member/close").param("id","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/courseManager"));
    }


    @Test
    void studentStatus() throws Exception {
        mockMvc.perform(get("/member/studentStatus").param("id","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/studentManager"));
    }

    @Test
    void openCourse() throws Exception {
        mockMvc.perform(get("/member/open").param("id","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/courseManager"));

    }

    @Test
    void getUpdate() throws Exception {
        mockMvc.perform(get("/member/update").param("id","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/course/courseUpdate"))
                .andExpect(model().attribute("course",courseService.findById(1L)));

    }


    @Test
    void getCourseRegister() throws Exception {
        mockMvc.perform(get("/member/courseRegister"))
                .andExpect(status().isOk())
                .andExpect(view().name("/course/courseRegister"))
                .andExpect(model().attribute("course",new Course()));

    }

    @Test
    void update(){
        when(userRepository.save(any(User.class))).thenReturn(user);
    }

    @Test
    void postCourseRegister(){
        when(courseRepository.save(any(Course.class))).thenReturn(course);
    }

 

}
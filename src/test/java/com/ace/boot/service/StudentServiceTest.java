package com.ace.boot.service;

import com.ace.boot.entity.*;
import com.ace.boot.repository.CourseRepository;
import com.ace.boot.repository.EnrollRepository;
import com.ace.boot.repository.StudentRepository;
import com.ace.boot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class StudentServiceTest {

    @InjectMocks
    StudentServiceImpl studentService;
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    CourseRepository courseRepository;
    @MockBean
    UserRepository userRepository;
    @Captor
    ArgumentCaptor<Specification<Student>> captor;
    @Mock
    UserServiceImpl userService;
    @MockBean
    EnrollRepository enrollRepository;
    Student student = new Student();
    List<Student> students = new ArrayList<>();

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
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
        when(studentRepository.findAll(captor.capture())).thenReturn(students);
    }

    @Test
    void registerStudent() {
        assertTrue(studentService.registerStudent(student));
        assertFalse(studentService.registerStudent(null));
    }

    @Test
    void updateStudent() throws SQLException, IOException {
        student.setName("kira");
        assertTrue(studentService.updateStudent(student));
        assertEquals("kira",student.getName());
    }

    @Test
    void removeStudent() {
        assertTrue(studentService.removeStudent(1L));
        assertNull(student.getStatus());
        studentService.removeStudent(1L);
        assertNotNull(student.getStatus());
        assertEquals(Status.ACTIVE,student.getStatus());
    }

    @Test
    void allStudents() throws SQLException, IOException {
        assertNotNull(studentService.allStudents());
    }

    @Test
    void searchMethod() throws SQLException, IOException {
        assertNotNull(studentService.searchMethod(1L,"msp","java"));
        assertNotNull(studentService.searchMethod(1L,null,null));
        assertNotNull(studentService.searchMethod(1L,"msp",null));
    }
}
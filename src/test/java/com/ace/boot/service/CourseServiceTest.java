package com.ace.boot.service;

import com.ace.boot.entity.Course;
import com.ace.boot.entity.Level;
import com.ace.boot.entity.Status;
import com.ace.boot.entity.User;
import com.ace.boot.repository.CourseRepository;
import com.ace.boot.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseServiceTest {
    @InjectMocks
    CourseServiceImpl courseService;
    @MockBean
    CourseRepository courseRepository;
    @MockBean
    StudentRepository studentRepository;
    @Captor
    ArgumentCaptor<Specification<Course>> captor;
    Course course = new Course();
    List<Course> courses = new ArrayList<>();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course.setId(1L);
        course.setName("java");
        course.setStatus(Status.ACTIVE);
        course.setLevel(Level.BASIC);
        course.setDescription("java basic course");
        courses.add(course);
        when(courseRepository.findAll(captor.capture())).thenReturn(courses);
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseRepository.findById(course.getId())).thenReturn(Optional.ofNullable(course));
    }

    @Test
    void registerCourse() {
        assertTrue(courseService.registerCourse(course));
    }

    @Test
    void updateDescriptionAndNameAndLevel() {
        course.setName("php");
        course.setDescription("php course");
        course.setLevel(Level.ADVANCED);
        courseService.updateDescriptionAndNameAndLevel(course);
        assertEquals("php",course.getName());
        assertEquals("php course",course.getDescription());
        assertEquals(Level.ADVANCED.name(),course.getLevel().toString());
    }

    @Test
    void closeCourseOpenCourse() {
        courseService.closeCourseOpenCourse(1L);
        assertNull(course.getStatus());
        courseService.closeCourseOpenCourse(1L);
        assertNotNull(course.getStatus());
    }

    @Test
    void getAllCourse() {
        assertNotNull(courseService.getAllCourse());
    }

    @Test
    void findById() {
        assertNotNull(courseService.findById(1L));
        assertThrows(Exception.class,()->courseService.findById(2L));
    }

    @Test
    void searchMethod() {
        assertNotNull(courseService.searchMethod(1L,"java","BASIC"));
        assertNotNull(courseService.searchMethod(1L,null,null));
        assertNotNull(courseService.searchMethod(1L,"java",null));
    }
}
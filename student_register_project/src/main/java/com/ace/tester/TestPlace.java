package com.ace.tester;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ace.connection.ConnectorCaller;
import com.ace.model.Course;
import com.ace.model.Student;
import com.ace.model.User;
import com.ace.repository.CourseService;
import com.ace.repository.StudentService;
import com.ace.repository.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/test")
@MultipartConfig
public class TestPlace extends HttpServlet {

	public static void main(String[] args) {
//		var user = new User("US001", "Jonathan", "JoJo1@gmail.com", "hamon", "admin");
//		var updateUser = new User();
//		updateUser.setName("updatedUser");
//		updateUser.setEmail("update@gmail.com");
//		updateUser.setPassword("updatedPassword");
//		updateUser.setId("US001");
//		updateUser.setRole("user");
//		var searchUser = new User();
//		searchUser.setId("US001");
//		searchUser.setName("UpdatedUser");
//		var userDao = new UserService(ConnectorCaller.getConnection());
//		System.out.println(userDao.register(user));
//		System.out.println(userDao.delete(updateUser));
//		System.out.println(userDao.update(updateUser));
//		System.out.println(userDao.searchByIdAndName(user).getName());
//		System.out.println(userDao.getUserAllUser());

//		var student = new Student("ST001", "set paing", "12-12-22", "male", "09971147172", "highschool", "Spring",
//				"photo.jpeg");
//		var studentService = new StudentService(ConnectorCaller.getConnection());
//		List<Student> stulist = new ArrayList<>();
//		var course = new Course();
//		course.setId("COU001");
//		stulist = studentService.findStudentWithCourseIdFromRecord(course);
//		for(Student s : stulist) {
//			System.out.println(s.getName());
//		}
//		System.out.println(studentService.register(student));
//		var searchStudent = new Student();
//		searchStudent.setId("ST001");
//		searchStudent.setName("myo set paing");
//		searchStudent.setAttend("java");
//		String[] nameList = {"Java Web Development","html"};
		System.out.println("ok".replace("ok", "ko"));
//		System.out.println(studentService.register(student));
//		System.out.println(studentService.insertRecord(student,nameList[0] ));
//		System.out.println(studentService.findByIdNameCourse(searchStudent));
//		System.out.println(studentService.getAllStudent());
//		System.out.println(studentService.findById("ST001"));
//		System.out.println(studentService.update(student));
//		System.out.println(studentService.delete(searchStudent));
		
		
//		var courseService = new CourseService(ConnectorCaller.getConnection());
//		var course = new Course();
//		course.setId("C001");
//		course.setName("Spring");
//		System.out.println(courseService.addCourse(course));
//		var list = courseService.getAllCourse();
//		for(Course c : list) {
//			System.out.println(c.getName());
//		}
//		 System.out.println(courseService.update(course));
//		System.out.println(courseService.delete(course));
	
		}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		InputStream inputStream = null;
		var photo = req.getParameter("photo");
		Part filePart = req.getPart("photo");
		System.out.println(filePart);
		if(null  != filePart) {
			System.out.println(filePart.getName());
			System.out.println(filePart.getSize());
			System.out.println(filePart.getContentType());
			inputStream = filePart.getInputStream();
			
			try {
				var sql = "insert into img values (?)";
				var ps = ConnectorCaller.getConnection().prepareStatement(sql);
				
				byte[] bb = inputStream.readAllBytes();
				System.out.println(bb);
				InputStream is= new ByteArrayInputStream(bb);
				ps.setBlob(1, is);
				
				var i = ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	}
}

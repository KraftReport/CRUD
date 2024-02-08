package com.ace.course.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ace.connection.ConnectorCaller;
import com.ace.model.Course;
import com.ace.model.Student;
import com.ace.repository.CourseService;
import com.ace.repository.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/courseUpdate")
public class courseUpdateController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var old_id = req.getParameter("old_id");
		var old_name = req.getParameter("old_name");
		var id = req.getParameter("id");
		var name = req.getParameter("name");
		var old_course = new Course();
		old_course.setId(old_id);
		old_course.setName(old_name);
		var courseService =new CourseService(ConnectorCaller.getConnection());
		var studentService = new StudentService(ConnectorCaller.getConnection());
		List<Student> stuListThatHaveCourseAttend = new ArrayList<>();
		stuListThatHaveCourseAttend = studentService.findStudentWithCourseIdFromRecord(old_course);
		var course = new Course();
		course.setId(old_id);
		course.setName(name);
		var b = courseService.update(course);
		for(int i=0; i<stuListThatHaveCourseAttend.size(); i++) {
			System.out.println(stuListThatHaveCourseAttend.get(i).getAttend());
			var stu = stuListThatHaveCourseAttend.get(i).getAttend();
			stuListThatHaveCourseAttend.get(i).setAttend(stu.replace(old_name, name));
			studentService.update(stuListThatHaveCourseAttend.get(i));
			 
			System.out.println(stuListThatHaveCourseAttend.get(i).getAttend());
			System.out.println(old_name);
			System.out.println(name);
		}
		if(b) {
			System.out.println("course is updated successfully");
			req.getSession().setAttribute("succMsg", "course is updated successfully");
			resp.sendRedirect("courseManage.jsp");
		}else {
			System.out.println("course update failed");
			req.getSession().setAttribute("errorMsg", "course update failed");
			resp.sendRedirect("courseManage.jsp");
		}
	}
}

package com.ace.course.controller;

import java.io.IOException;

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

@WebServlet("/courseDelete")
public class courseDeleteController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 var courseService = new CourseService(ConnectorCaller.getConnection());
		 var studentService = new StudentService(ConnectorCaller.getConnection()); 
		 var id = req.getParameter("id");
		 var student = new Student();
		 var course = new Course();
		 course.setId(id);
		 var foundCourse = courseService.findById(course);
		 var stuList = studentService.findStudentWithCourseIdFromRecord(course);
			for(int i=0; i<stuList.size(); i++) {
				System.out.println(stuList.get(i).getAttend());
				var stu = stuList.get(i).getAttend();
				var one =  stu.replace(","+foundCourse.getName()+",","");
				var two = one.replace(foundCourse.getName()+",","");
				var three = two.replace(","+foundCourse.getName(),"");
//				stuList.get(i).setAttend(stu.replace(foundCourse.getName()+",",""));
//				stuList.get(i).setAttend(stu.replace(","+foundCourse.getName(),""));
				stuList.get(i).setAttend(three.replace(foundCourse.getName(),""));
				studentService.update(stuList.get(i));
				System.out.println(stuList.get(i).getAttend());
				System.out.println(foundCourse.getName()+",");
				System.out.println("");
			}
		 var b= studentService.deleteRecordWithCourseId(course);
		 var b1 = courseService.delete(course);
		 if( b1) {
			 req.getSession().setAttribute("succMsg", "course deleted successfully");
			 resp.sendRedirect("courseManage.jsp");
		 }else {
			 req.getSession().setAttribute("errorMsg", "course delete fail");
			 resp.sendRedirect("courseManage.jsp");
		 }
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}

package com.ace.course.controller;

import java.io.IOException;

import com.ace.connection.ConnectorCaller;
import com.ace.model.Course;
import com.ace.repository.CourseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/courseRegister")
public class CourseRegisterController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var id = req.getParameter("id");
		var name = req.getParameter("name");
		var course = new Course();
		var courseService = new CourseService(ConnectorCaller.getConnection());
		course.setId(id);
		course.setName(name);
		var b = courseService.addCourse(course);
		if(b == true) {
			req.getSession().setAttribute("succMsg", "Course is added successfully");
			resp.sendRedirect("studentManage.jsp");
		}else {
			req.getSession().setAttribute("succMsg", "Course addition failed");
			resp.sendRedirect("studentManage.jsp");
		}
	}
}

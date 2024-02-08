package com.ace.student.controller;

import java.io.IOException;

import com.ace.connection.ConnectorCaller;
import com.ace.model.Student;
import com.ace.repository.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/studentDelete")
public class DeleteStudentController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var id = req.getParameter("id");
		var student = new Student();
		student.setId(id);
		var studentService = new StudentService(ConnectorCaller.getConnection());
		studentService.deleteRecord(student);
		var i = studentService.delete(student);
		if(i) {
			System.out.println("student is deleted successfully");
			req.getSession().setAttribute("succMsg", "A student is register successfully");
			resp.sendRedirect("studentManage.jsp");
		}else {
			System.out.println("student delete fail");
			req.getSession().setAttribute("errorMsg", "A student delete fail");
			resp.sendRedirect("studentManage.jsp");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}

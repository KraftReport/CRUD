package com.ace.student.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ace.connection.ConnectorCaller;
import com.ace.model.Student;
import com.ace.repository.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/studentSearch")
public class SearchStudentController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var id = "";
		var name ="";
		var attend = "";
		id = req.getParameter("id");
		name = req.getParameter("name");
		attend = req.getParameter("attend");
		var studentService = new StudentService(ConnectorCaller.getConnection());
		var student = new Student();
		List<Student> stuList = new ArrayList<>();
		student.setId(id);
		student.setName(name);
		student.setAttend(attend);
		if(id.equals("") && attend.equals("")) {
			stuList = studentService.findByName(student);
		}else if(id.equals("") && name.equals("")) {
			stuList = studentService.findByCourse(student);
		}else if(name.equals("")&& attend.equals("")) {
			stuList = studentService.findById(student.getId());
		}else {
		var returnStudent = studentService.findByIdNameNotList(student);
		 
			var ok = studentService.validateCourse(returnStudent, attend);
			if(ok == true) {
				stuList.add(returnStudent);}} 
		if(stuList != null) {
				System.out.println("search student is found");
				req.setAttribute("searchStudent",stuList);
				req.getSession().setAttribute("succMsg", "search student is found");
				req.getRequestDispatcher("studentManage.jsp").forward(req, resp);
			
		}else {
			System.out.println("search student is not found with id and name");
			req.getSession().setAttribute("errorMsg", "search student is not found with id and name");
			resp.sendRedirect("studentManage.jsp");
		}
	}
}
 

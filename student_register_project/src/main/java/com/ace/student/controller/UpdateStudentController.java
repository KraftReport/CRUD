package com.ace.student.controller;

import java.io.IOException;
import java.io.InputStream;

import com.ace.connection.ConnectorCaller;
import com.ace.model.Student;
import com.ace.repository.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/studentUpdate")
@MultipartConfig
public class UpdateStudentController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var id = req.getParameter("id");
		var name = req.getParameter("name");
		var dob = req.getParameter("dob");
		var gender = req.getParameter("gender");
		var phone = req.getParameter("phone");
		var education = req.getParameter("education");
		var attend = req.getParameterValues("attend");
		var photo = req.getParameter("photo");
		var student = new Student();
		var studentService = new StudentService(ConnectorCaller.getConnection());
		var attends = studentService.getOneStringFromArray(attend);
		var photodatstring = req.getParameter("photo");
		var p = req.getParameter("photo");
		System.err.println("p   " + photodatstring);
		Part photodata = req.getPart("photo");
		byte[] bytearray = null;
	 
			InputStream inputSteam = photodata.getInputStream();
			  bytearray = inputSteam.readAllBytes();
		 
		student.setId(id);
		student.setName(name);
		student.setDob(dob);
		student.setEducation(education);
		student.setGender(gender);
		student.setPhone(phone);
		student.setPhoto(bytearray);
		studentService.deleteRecord(student);
		for (int i = 0; i < attend.length; i++) {
			studentService.insertRecord(student, attend[i]);
			System.out.println(attend[i]);
		}
		student.setAttend(studentService.getOneStringFromArray(attend));
//		var foundStudent = studentService.findById(id);
//		System.out.println(foundStudent);
//		for (int i = 0; i < attend.length; i++) {
//			var ok = studentService.validateCourse(foundStudent, attend[i]);
//			if (ok) {
//				System.out.println("no new course is added");
//				student.setAttend(foundStudent.getAttend());
//			} else {
//				System.out.println("a new course is added");
//				student.setAttend(foundStudent.getAttend() + "," + attend[i]);
//			}
//		}
		var updateStudentSuccess = studentService.update(student);
		if (updateStudentSuccess) {
			System.out.println("update student successful");
			req.getSession().setAttribute("succMsg", "student is updated successful");
			resp.sendRedirect("studentManage.jsp");
		} else {
			System.out.println("update student failed");
			req.getSession().setAttribute("errorMsg", "update student failed");
			resp.sendRedirect("studentManage.jsp");
		}
	}
}

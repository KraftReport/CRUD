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

@WebServlet("/studentRegister")
@MultipartConfig
public class RegisterStudentController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var studentService = new StudentService(ConnectorCaller.getConnection());
		var id = req.getParameter("id");
		var name = req.getParameter("name");
		var dob = req.getParameter("dob");
		var gender = req.getParameter("gender");
		var phone = req.getParameter("phone");
		var education = req.getParameter("education");
		String[] attend = req.getParameterValues("attend");
		var attends = studentService.getOneStringFromArray(attend);
//		var photo = req.getParameter("photo");
		Part photodata = req.getPart("photo");
		System.out.println(photodata);
		InputStream inputStream = photodata.getInputStream();
		byte[] byteStream= inputStream.readAllBytes();
		var student = new Student();
		student.setId(id);
		student.setName(name);
		student.setDob(dob);
		student.setGender(gender);
		student.setPhone(phone);
		student.setEducation(education);
		student.setAttend(attends);
		student.setPhoto(byteStream);
		var registerSuccess = studentService.register(student);
		var insertRecordSuccess = false;
		for (int i = 0; i < attend.length; i++) {
			insertRecordSuccess = studentService.insertRecord(student, attend[i]);
		}
		if (registerSuccess && insertRecordSuccess) {
			System.out.println("student is registered successfully");
			req.getSession().setAttribute("succMsg", "A student is register successfully");
			resp.sendRedirect("studentManage.jsp");
		} else {
			System.out.println("Student register failed");
			req.getSession().setAttribute("errorMsg", "Student register failed");
			resp.sendRedirect("studentManage.jsp");
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

 
}

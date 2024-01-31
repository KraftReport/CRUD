package com.home.contoller;

import java.io.IOException;

import com.home.connection.GetConnection;
import com.home.dao.StudentDao;
import com.home.dto.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updatestudent")
public class UpdateServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var name = req.getParameter("name");
		var dob = req.getParameter("dob");
		var address = req.getParameter("address");
		var qualification = req.getParameter("qualification");
		var email = req.getParameter("email");
		var id = Integer.parseInt(req.getParameter("id"));

		var student = new Student(id, name, dob, address, qualification, email);
		var session = req.getSession();
		var stuDao = new StudentDao(GetConnection.getConnection());

		var b = stuDao.update(student);
		if (b) {
			session.setAttribute("succMsg", "update successful");
			resp.sendRedirect("index.jsp");
			System.out.println("update ok");
		} else {
			session.setAttribute("errorMsg", "update fail");
			resp.sendRedirect("index.jsp");
			System.out.println("update fail");
		}
	}
}

package com.home.contoller;

import java.io.IOException;
import java.sql.SQLException;

import com.home.connection.GetConnection;
import com.home.dao.StudentDao;
import com.home.dto.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/savestudent")
public class SaveServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var name = req.getParameter("name");
		var dob = req.getParameter("dob");
		var address = req.getParameter("address");
		var qualification = req.getParameter("qualification");
		var email = req.getParameter("email");

		var student = new Student(name, dob, address, qualification, email);
		var session = req.getSession();
		var stuDao = new StudentDao(GetConnection.getConnection());

		try {
			var  b = stuDao.insert(student);
			if(b) {
				session.setAttribute("succMsg", "save successful");
				resp.sendRedirect("save.jsp");
				System.out.println("insert ok");
			}else {
				session.setAttribute("errorMsg", "save fail");
				resp.sendRedirect("save.jsp");
				System.out.println("insert fail");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}

package com.home.contoller;

import java.io.IOException;

import com.home.connection.GetConnection;
import com.home.dao.StudentDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deletestudent")
public class DeleteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var id = Integer.parseInt(req.getParameter("id"));
		var stuDao = new StudentDao(GetConnection.getConnection());
		var session = req.getSession();
		var b = stuDao.delete(id);
		if (b) {
			session.setAttribute("succMsg", "delete successful");
			resp.sendRedirect("index.jsp");
			System.out.println("delete successful");
		} else {
			session.setAttribute("errorMsg", "delete fail");
			resp.sendRedirect("index.jsp");
			System.out.println("delete fail");
		}
	}
}

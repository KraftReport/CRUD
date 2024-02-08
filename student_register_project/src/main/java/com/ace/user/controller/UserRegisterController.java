package com.ace.user.controller;

import java.io.IOException;

import com.ace.connection.ConnectorCaller;
import com.ace.model.User;
import com.ace.repository.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/userRegister")
public class UserRegisterController extends HttpServlet {

	private static int idnum = 0;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var email = req.getParameter("email");
		var name = req.getParameter("name");
		var password = req.getParameter("password");
		var confirmPassword = req.getParameter("confirmPassword");

		var role = req.getParameter("role");
		var user = new User();
		int addId = counter();
		user.setId(generateId(addId));
		user.setEmail(email);
		user.setName(name);
		user.setPassword(confirmPassword);
		user.setRole(role);

		var userService = new UserService(ConnectorCaller.getConnection());
		var b = userService.register(user);
		if (b == true) {
			req.getSession().setAttribute("succMsg", "User registered successfully");
			resp.sendRedirect("index.jsp");

		} else {
			req.getSession().setAttribute("errorMsg", "User registered failed");
			resp.sendRedirect("index.jsp");
		}

	}

	private String generateId(int id) {
		String changeString = id + "";
		if (changeString.length() == 1) {
			changeString = "USR00" + changeString;
		} else if (changeString.length() == 2) {
			changeString = "USR0" + changeString;
		} else if (changeString.length() == 3) {
			changeString = "USR" + changeString;
		} else {
			changeString = "error";
		}
		return changeString;
	}

	private int counter() {
		return idnum++;
	}

}

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

@WebServlet("/userUpdate")
public class UpdateController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var id = req.getParameter("id");
		var email = req.getParameter("email");
		var name = req.getParameter("name");
		var password = req.getParameter("password");
		var confirmPassword = req.getParameter("confirmPassword");
		var role = req.getParameter("role");
		var user = new User();
		var userService = new UserService(ConnectorCaller.getConnection());
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setRole(role);
		user.setId(id);
		var b = userService.update(user);
		if(b == true) {
			req.getSession().setAttribute("succMsg", "User updated successfully");
			resp.sendRedirect("userManage.jsp");
		}else {
			req.getSession().setAttribute("errorMsg", "User update fail");
			resp.sendRedirect("userManage.jsp");
		}
	}
}

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

@WebServlet("/login")
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var email = req.getParameter("email");
		var password = req.getParameter("password");
		var userService = new UserService(ConnectorCaller.getConnection());
		var user = new User();
		user.setEmail(email);
		user.setPassword(password);
		var found = userService.searchByEmailAndPassword(user);
		if(found!=null) {
			var token = req.getSession();
			token.setAttribute("userObject", found);
			token.setAttribute("loginRole", found.getRole());
			token.setAttribute("tokenId", found.getId());
			token.setAttribute("tokenName", found.getName());
			req.getSession().setAttribute("succMsg", "User login successful");
			resp.sendRedirect("welcome.jsp");
		}else {
			req.getSession().setAttribute("errorMsg", "User login failed");
			resp.sendRedirect("index.jsp");
		}
	}
	
}

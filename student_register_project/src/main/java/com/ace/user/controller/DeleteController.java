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

@WebServlet("/userDelete")
public class DeleteController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var id = req.getParameter("id");
		var userService = new UserService(ConnectorCaller.getConnection());
		var user = new User();
		user.setId(id);
		var b =  userService.delete(user);
		if(b == true) {
			req.getSession().setAttribute("succMsg", "User delete successful");
			resp.sendRedirect("userManage.jsp");
		}else {
			req.getSession().setAttribute("errorMsg", "User delete fail");
			resp.sendRedirect("userManage.jsp");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}

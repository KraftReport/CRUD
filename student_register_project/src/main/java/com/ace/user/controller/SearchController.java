package com.ace.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ace.connection.ConnectorCaller;
import com.ace.model.User;
import com.ace.repository.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/userSearch")
public class SearchController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 var id = req.getParameter("id");
	 var name = req.getParameter("name");
	 var user = new User();
	 var userService = new UserService(ConnectorCaller.getConnection());
	 user.setId(id);
	 user.setName(name);
	 List<User> returnUser = new ArrayList<>();
	 if(id.equals("") && !name.equals(null)) {
		 System.out.println("one");
		 returnUser= userService.searchByName(user);
	 }else if(!id.equals(null) && name.equals("")) {
		 System.out.println("two");
		 returnUser = userService.searchById(user.getId());
	 }else {
		 returnUser = userService.searchByIdAndName(user);
	 }
	 if(returnUser != null) {
		 System.out.println("search controller is worked");
//		 System.out.println(returnUser.getName());
		 	req.setAttribute("searchUser", returnUser);
		 	req.getSession().setAttribute("succMsg", "search result");
			req.getRequestDispatcher("userManage.jsp").forward(req, resp);
	 }else {
		 req.getSession().setAttribute("errorMsg", "no user is found");
			resp.sendRedirect("userManage.jsp");
	 }
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
}

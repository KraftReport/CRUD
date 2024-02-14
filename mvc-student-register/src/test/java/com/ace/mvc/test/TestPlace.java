//package com.ace.mvc.test;
//
//import java.sql.SQLException;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import com.ace.mvc.config.RootConfig;
//import com.ace.mvc.model.User;
//import com.ace.mvc.model.User.Role;
//import com.ace.mvc.service.UserService;
//import com.ace.mvc.serviceImpl.UserServiceImpl;
//
//@SpringJUnitConfig(classes=RootConfig.class)
//public class TestPlace {
//	@Autowired
//	UserService us;
//	
//	@Test
//	void insert() {
//		User user = new User();
//		user.setId(1);
//		user.setName("myo");
//		user.setPassword("123");
//		user.setEmail("kraft@gmail.com");
//		user.setRole(Role.admin);
//		try {
//			us.addUser(user);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//
////	public static void main(String[] args) {
////		var us = new UserServiceImpl();
////		var user = new User();
////		user.setId(1);
////		user.setName("myo");
////		user.setPassword("123");
////		user.setEmail("kraft@gmail.com");
////		user.setRole(Role.admin);
////		try {
////			us.addUser(user);
////		} catch (SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
//}

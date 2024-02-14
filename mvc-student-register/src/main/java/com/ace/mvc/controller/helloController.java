package com.ace.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.mvc.service.UserService;

@Controller
@RequestMapping("/")
public class helloController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/welcome")
	public String hello(Model model) {
		return "welcome";
	}
}

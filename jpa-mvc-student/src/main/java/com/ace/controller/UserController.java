package com.ace.controller;

import com.ace.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.ace.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String login(@ModelAttribute User user) {
		return "login";
	}

	@GetMapping("/userRegister")
	public String userRegister(@ModelAttribute User user){
		return "register";
	}

	@GetMapping("/userDelete")
	public String delete(@ModelAttribute User user,ModelMap modelMap){
		userService.deleteUser(user.getId());
		modelMap.addAttribute("allUser",userService.getAllUsers());
		return "userManage";
	}

	@GetMapping("/userManage")
	public String userManage(@ModelAttribute User user,ModelMap modelMap){
		modelMap.addAttribute("allUser",userService.getAllUsers());
		return "userManage";
	}

	@GetMapping("/userUpdate")
	public String updateUser(@ModelAttribute User user,ModelMap modelMap){
		modelMap.addAttribute("updateUser",userService.getById(user.getId()).get(0));
		return "userUpdate";
	}

	@GetMapping("/logout")
	public String logout(HttpSession httpSession){
		httpSession.removeAttribute("loginUser");
		return "login";
	}

	@PostMapping("/userRegister")
	public String register(@ModelAttribute User user, ModelMap modelMap){
		userService.registerUser(user);
		modelMap.addAttribute("succMsg","successfully registered");
		return "login";
	}

	@PostMapping("/userLogin")
	public String login(@ModelAttribute User user, ModelMap modelMap, HttpSession httpSession){
		if(userService.loginUser(user)){
			httpSession.setAttribute("loginUser",userService.getByEmail(user.getEmail()).get(0));
			System.out.println(userService.getByEmail(user.getEmail()).get(0));
			httpSession.setAttribute("currentDate", LocalDate.now());
			modelMap.addAttribute("succMsg","Login successful");
			modelMap.addAttribute("allUser",userService.getAllUsers());
			return "userManage";
		}
		modelMap.addAttribute("errorMsg","Login failed");
		return "login";
	}

	@PostMapping("/userUpdate")
	public String update(@ModelAttribute User user, ModelMap modelMap){
		userService.updateUser(user);
		modelMap.addAttribute("allUser",userService.getAllUsers());
		return "userManage";
	}

	@PostMapping("/userSearch")
	public String search(@RequestParam(name = "id",required = true)String id,@RequestParam(name = "name",required = true)String name, ModelMap modelMap){
		if((id.isEmpty() && name.isEmpty()) || (id.isBlank() && name.isBlank()
		||(id.isBlank() && name.isEmpty())||(id.isEmpty() && name.isBlank()))){
			modelMap.addAttribute("allUser",userService.getAllUsers());
			return "userManage";
		} else if (id.isEmpty()) {
			modelMap.addAttribute("allUser",userService.getByName(name));
			return "userManage";
		} else if (!id.isEmpty()) {
			modelMap.addAttribute("allUser",userService.getById(Integer.parseInt(id)));
			return "userManage";
		} else if (!id.isEmpty() && !name.isEmpty()) {
			modelMap.addAttribute("allUser",userService.getById(Integer.parseInt(id)));
			return "usermanage";
		}else {
			modelMap.addAttribute("errorMsg","no result");
			return "userManage";
		}
	}



}

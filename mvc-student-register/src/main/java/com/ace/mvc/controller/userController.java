package com.ace.mvc.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ace.mvc.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ace.mvc.model.User;

import com.ace.mvc.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/")
public class userController {

	Map<String ,String> emailToCode = new HashMap<>();

	@Autowired
	EmailService emailService;

	private static int num = 0;

	@Autowired
	UserService userService;

	@GetMapping("/logout")
	public String logout(HttpSession htse) {
		htse.removeAttribute("loginUser");
		return "login";
	}

	@GetMapping
	public String log() {
		return "login";
	}

	@GetMapping("/userDelete")
	public String userDelete(@RequestParam(required = true, name = "id") String id, RedirectAttributes redAtr,
			HttpSession htse) {
		int intId = Integer.parseInt(id);
		User user = (User) htse.getAttribute("loginUser");
		if (intId == user.getId()) {
			redAtr.addFlashAttribute("errorMsg", "you can't remove current login user");
			return "redirect:/userManage";
		}

		var b = userService.deleteUser(intId);
		if (b) {
			redAtr.addFlashAttribute("succMsg", "user is deleted successfully");
			htse.setAttribute("allUser", userService.getAllUser());
			return "redirect:/userManage";
		}
		redAtr.addFlashAttribute("errorMsg", "user deletion fail");
		return "redirect:/userManage";
	}
	
	@GetMapping("/forgetPassword")
	public String forget() {
		return "forgetPassword";
	}

	@GetMapping("/userManage")
	public String userManage() {
		return "userManage";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping("/userRegister")
	public ModelAndView getRegister() {
		return new ModelAndView("userRegister", "user", new User());
	}

	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/changePassword")
	public String change(@ModelAttribute User user,ModelMap modelMap)
	{
		return "changePassword";
	}

	@GetMapping("/userUpdate")
	public String userUpdate(@RequestParam(required = true, name = "id") String id, Model model) {
		System.out.println("param " + id);
		model.addAttribute("id", id);
		 model.addAttribute("user",userService.findById(Integer.parseInt(id)));
		return "userUpdate";
	}

	@PostMapping("/login")
	public String login(@RequestParam(required = true, name = "email") String email,
			@RequestParam(required = true, name = "password") String password, Model model, RedirectAttributes redAtr,
			HttpSession htSe) throws SQLException {
		var found = userService.searchByEmail(email);
		if (found != null) {
		 
			if (found.getPassword().equals(password)) {
				System.out.println(found.getPassword());
				htSe.setAttribute("allUser", userService.getAllUser());
				htSe.setAttribute("loginUser", found);
//				 redAtr.addFlashAttribute("allUser",userList);
				return "redirect:/userManage";
			}
			redAtr.addFlashAttribute("errorMsg", "password wrong");
			return "redirect:/login";
		}
		redAtr.addFlashAttribute("errorMsg", "login fail");
		return "redirect:/login";
	}

	@PostMapping("/userRegister")
	public String register(@RequestParam(required = true, name = "email") String email,
			@RequestParam(required = true, name = "password") String password,
			@RequestParam(required = true, name = "name") String name,
			@RequestParam(required = true, name = "role") String role, RedirectAttributes redAtr) throws SQLException {
		var id = num++;
		var user = new User(id, name, password, email, role);
		var b = userService.addUser(user);
		if (b) {
			redAtr.addFlashAttribute("succMsg", "registered successfully");
			return "redirect:/login";
		}
		redAtr.addFlashAttribute("errorMsg", "registered failed");
		return "redirect:/register";

	}

	@PostMapping("/userUpdate")
	public String updateUserPost(@RequestParam(required = true, name = "id") int id,
			@RequestParam(required = true, name = "name") String name,
			@RequestParam(required = true, name = "email") String email,
			@RequestParam(required = true, name = "password") String password,
			@RequestParam(required = true, name = "role") String role, HttpSession htse, RedirectAttributes redAtr) {
//		var intid = Integer.parseInt(id);
		var user = new User(id, name, password, email, role);
		var b = userService.updateUser(user);
		if (b) {
			User loginUser =(User) htse.getAttribute("loginUser");
			if(user.getId() == loginUser.getId()) {htse.setAttribute("loginUser", user);}
			
			htse.setAttribute("allUser", getAllUserList());
			redAtr.addFlashAttribute("succMsg", "user information is updated successfully");
			return "redirect:/userManage";
		}
		redAtr.addFlashAttribute("errorMsg", "update fail");
		return "redirect:/userUpdate";
	}

	@SuppressWarnings("null")
	@PostMapping("/userSearch")
	public String searchUser(@RequestParam(required = false, name = "id") String id,
			@RequestParam(required = false, name = "name") String name, HttpSession htse, ModelMap mm) {
		try {
			if (id.isBlank() && name.isBlank()) {
				htse.setAttribute("allUser", userService.getAllUser());
				mm.addAttribute("succMsg", "find all user");
				return "userManage";
			} else {
				int intId = Integer.parseInt(id);
				System.out.println(intId);
				List<User> found = null;

				found = new ArrayList<User>();
				var user = userService.findById(intId);
				if (user != null) {
					System.out.println(user.getId());
					found.add(user);
					htse.setAttribute("allUser", found);
					mm.addAttribute("succMsg", "find by id");
					return "userManage";
				}
				System.out.println(user.getId());
				mm.addAttribute("errorMsg", "find by id not found");
				return "userManage";
			}

		} catch (Exception e) {
			var list = userService.findByName(name);
			if (list.size() > 0) {
				mm.addAttribute("succMsg", "find by name");
				htse.setAttribute("allUser", list);
				return "userManage";
			} else {
				mm.addAttribute("errorMsg", "no result");
				htse.setAttribute("allUser", new ArrayList<User>());
				return "userManage";
			}

		}
	}
	
	@PostMapping("/forgetPassword")
	public String forgetPass(ModelMap modelMap,@RequestParam("email")String email,@RequestParam(value = "code",required = false)String code,RedirectAttributes reat,HttpSession htse) {
		System.err.println(email + " user one");
		if(code==null || code.isEmpty() || code.isBlank()){
			var user = userService.findPasswordByEmail(email);
			if(user==null || user.isEmpty() ){
				reat.addFlashAttribute("errorMsg","no user with such email is found");
				return "login";
			}else{
				var codeToSend = emailService.generateCode();
				emailService.sendEmail(email,codeToSend);
				emailToCode.put(email,codeToSend);
				reat.addFlashAttribute("OTP","OTP");
				reat.addFlashAttribute("succMsg","OTP is send to your email");
				return "redirect:/forgetPassword";
			}
		}else {
			var storeCode = emailToCode.get(email.trim());
			if (storeCode != null && storeCode.equals(code.trim())) {
				modelMap.addAttribute("succMst", "OTP is correct change new password");
				modelMap.addAttribute("foundUser",userService.findPasswordByEmail(email).get(0));
				return "changePassword";
			} else {
				reat.addFlashAttribute("errorMsg", "OTP is wrong");
				return "redirect:/forgetPasswrod";
			}
		}
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@ModelAttribute User user1,HttpSession htse,RedirectAttributes reat,ModelMap modelMap) {
//		var user =(User) modelMap.getAttribute("foundUser");
		System.out.println(user1.getId());
		var user = userService.findById(user1.getId());
		System.out.println(user);
        assert user != null;
        user.setPassword(user1.getPassword());
		var b = userService.updateUser(user);
		if(b) {
			reat.addFlashAttribute("succMsg","password is changed successfully");
			return "login";
		}
		reat.addFlashAttribute("errorMsg","password is failed to change");
		return "login";
	}

	public List<User> getAllUserList() {
		return userService.getAllUser();
	}

}

package com.ace.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ace.mvc.model.Course;
import com.ace.mvc.service.CourseService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class CourseController {

	@Autowired
	private CourseService courseService;

	private static int num = 0;

	@GetMapping("/courseRegister")
	public String register() {
		return "courseRegister";
	}

	@GetMapping("/courseUpdate")
	public String update(@RequestParam(required = true,name = "id")String id,ModelMap mm) {
		var course = courseService.findById(id);
		mm.addAttribute("name",course.get(0).getName());
		mm.addAttribute("status",course.get(0).getStatus());
		mm.addAttribute("id",id);
		return "courseUpdate";
	}

	@GetMapping("/courseManage")
	public String manage(HttpSession htse) {
		htse.setAttribute("allCourse", getAll());
		return "courseManage";
	}
	
	@GetMapping("/courseDelete")
	public String delete(@RequestParam(required = true,name = "id")String id,RedirectAttributes reat,HttpSession htse) {
		var b = courseService.deleteCourse(id);
		if(b== true) {
			reat.addFlashAttribute("succMsg","Course is deleted successfully");
			htse.setAttribute("allCourse", getAll());
			return "redirect:/courseManage";
		}
		reat.addFlashAttribute("errorMsg","Course deletion fail");
		htse.setAttribute("allCourse", getAll());
		return "redirect:/courseManage";
	}
	
	@PostMapping("/courseUpdate")
	public String up(@RequestParam(required = true,name = "id")String id
			,@RequestParam(required = true,name = "name")String name,
			@RequestParam(required = true,name = "status")String status,
			RedirectAttributes reat,HttpSession htse) {
		var course = new Course();
		course.setId(id);
		course.setName(name);
		course.setStatus(status);
		var updatedCourse = courseService.updateCourse(course);
		if(updatedCourse == true) {
			reat.addFlashAttribute("succMsg","course is updated successfully");
			htse.setAttribute("allCourse", getAll());
			return "redirect:/courseManage";
		}
		reat.addFlashAttribute("errorMsg","course update is failed");
		htse.setAttribute("allCourse", getAll());
		return "redirect:/courseManage";
	}

	@PostMapping("/courseRegister")
	public String courseRegister(@RequestParam(required = true, name = "name") String name,
			@RequestParam(required = true, name = "status") String status, RedirectAttributes redAtr
			,HttpSession htse) {
		num++;
		var course = new Course();
		course.setId(getId(num + ""));
		course.setName(name);
		course.setStatus(status);
		var b = courseService.addCourse(course);
		if (b) {
			redAtr.addFlashAttribute("succMsg", "course is added successfully");
			htse.setAttribute("allCourse", getAll());
			return "redirect:/courseManage";
		}
		redAtr.addFlashAttribute("errorMsg", "course addition fail");
		htse.setAttribute("allCourse", getAll());
		return "redirect:/courseManage";
	}
	
	@PostMapping("/courseSearch")
	public String courseSearch(@RequestParam(required = true,name = "id")String id,
			@RequestParam(required = true,name = "name")String name,HttpSession htse,ModelMap mm) {
		try {
			if(id.isBlank() && name.isBlank()) {
				mm.addAttribute("succMsg","all courses");
				htse.setAttribute("allCourse", courseService.getAllCourse());
				return "courseManage";
			}else if(id.isBlank()) {
				mm.addAttribute("succMsg","find by name");
				htse.setAttribute("allCourse",courseService.findByName(name));
				return "courseManage";
			}else if(name.isBlank()) { 
				mm.addAttribute("succMsg","find by id");
				htse.setAttribute("allCourse", courseService.findById(id));
				return "courseManage";
			}else if(!id.isBlank() && !name.isBlank()){
				mm.addAttribute("succMsg","find by id");
				htse.setAttribute("allCourse", courseService.findById(id));
				return "courseManage";
			}else {
				mm.addAttribute("errorMsg","no result");
				htse.setAttribute("allCourse", new ArrayList<>());
				return "courseManage";
			}
		} catch (Exception e) {
			mm.addAttribute("errorMsg","no result");
			htse.setAttribute("allCourse", new ArrayList<>());
			return "courseManage";
		}
	}

	private String getId(String id) {
		String returnId = null;
		if (id.length() == 1) {
			returnId = "00" + id;
		} else if (id.length() == 2) {
			returnId = "0" + id;
		}
		return returnId;
	}
	
	private List<Course> getAll() {
		return courseService.getAllCourse();
	}
}

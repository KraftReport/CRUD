package com.ace.mvc.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ace.mvc.model.Course;
import com.ace.mvc.model.Product;
import com.ace.mvc.model.Student;
import com.ace.mvc.service.CourseService;
import com.ace.mvc.service.StudentService;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class StudentController {
 
	@Autowired
	StudentService studentService;
	@Autowired
	CourseService courseService;
	
	private static int num;
	
	@PostMapping("/p")
	public String pro(@ModelAttribute Product pro) {
		System.err.println(pro);
		return"welcome";
	}
	
	@GetMapping("/studentDelete")
	public String delete(@RequestParam(required = true,name = "id")String id,HttpSession htse,RedirectAttributes reat) {
		var b=  studentService.deleteStudent(id);
		if(b) {
			reat.addFlashAttribute("succMsg","Student is deleted successfully");
			htse.setAttribute("allStudent", studentService.getAllStudent());
			return "redirect:/studentManage";
		}
		reat.addFlashAttribute("errorMsg","Student is failed to delete");
		htse.setAttribute("allStudent", studentService.getAllStudent());
		return "redirect:/studentManage";
	}
	
	@GetMapping("/studentUpdate")
	public String update(ModelMap mm,@ModelAttribute Student student) {
		var id = student.getId();
		var stu = studentService.findById(id);
		mm.addAttribute("stuCourseList",studentService.getOneStringFromArray(stu.get(0).getCourse()));
		mm.addAttribute("allCourse",courseService.getAllCourse());
		mm.addAttribute("student",stu.get(0));
		return "studentUpdate";
	}
	
	@GetMapping("/studentRegister")
	public String register(ModelMap mm) {
		List<Course> courseList = courseService.getAllCourse();
		mm.addAttribute("allCourse",courseList);
		return "studentRegister";
	}
	
	@GetMapping("/studentManage")
	public String manage(ModelMap mm,HttpSession htse) throws IOException { 
		htse.setAttribute("allCourse",courseService.getAllCourse());
		htse.setAttribute("allStudent",studentService.getAllStudent());
		String list = "";
		for(int i =0; i<courseService.getAllCourse().size();i++) {
			if(courseService.getAllCourse().get(i).getStatus() == "closed") {
				list += courseService.getAllCourse().get(i).getName();
			}
		}
		htse.setAttribute("list", list);
		return "studentManage";
	}
	
	
	@GetMapping("/studentDetail")
	public String detail(@RequestParam(required = true,name = "id")String id,ModelMap mm) {
		mm.addAttribute("student",studentService.findById(id).get(0));
		return "studentDetail";
	}
	 
	
	@PostMapping("/stest")
	public String test (@RequestParam(required = true,name="file")MultipartFile file) {
		
		return "";
	}
	
	@PostMapping("/studentRegister")
	public String studentRegister(@ModelAttribute Student stu,RedirectAttributes reat) throws IOException {
		num++;
		var student = new Student();
		student.setId(getId(num+""));
		student.setName(stu.getName());
		student.setDob(stu.getDob());
		student.setGender(stu.getGender());
		student.setPhone(stu.getPhone());
		student.setEducation(stu.getEducation());
		student.setPhoto(stu.getPhoto());
		student.setCourse(stu.getCourse());
		var b = studentService.registerStudent(student);
		if(b){
			reat.addFlashAttribute("succMsg","student is added successfully");
			return "redirect:/studentManage";
		}
		reat.addFlashAttribute("errorMsg","student is addition fail");
		return "redirect:/studentRegister";
		
	}
	
	@PostMapping("/studentUpdate")
	public String stuUpdate(@ModelAttribute Student stu,ModelMap mm,HttpSession htse) {
		System.err.println("----");
		if(stu.getPhoto().isEmpty()) {
			stu.setPhoto(studentService.findById(stu.getId()).get(0).getPhoto());
			System.err.println(studentService.findById(stu.getId()).get(0).getPhoto());
		}
		var b =  studentService.updateStudet(stu);
		if(b) {
			mm.addAttribute("succMsg","Student is updated successfully");
			htse.setAttribute("allStudent", studentService.getAllStudent());
			return "studentManage";
		}
		mm.addAttribute("errorMsg","Student update failed");
		htse.setAttribute("allStudent", studentService.getAllStudent());
		return "studentManage";
	}
	
	@PostMapping("/studentSearch")
	public String search(@ModelAttribute Student student,ModelMap mm) {
		var id = student.getId();
		var name = student.getName();
		var course = student.getCourse();
		var courses = studentService.getOneStringFromArray(course);
		if(id.isBlank() && name.isBlank() && courses.isBlank()) {
			mm.addAttribute("succMsg","all results");
			mm.addAttribute("allStudent",studentService.getAllStudent());
			return "studentManage";
		}
		if(!id.isBlank() && (name.isBlank() || courses.isBlank() || !courses.isBlank() || !name.isBlank())) {
			mm.addAttribute("succMsg","find by id");
			mm.addAttribute("allStudent",studentService.findById(id));
			return "studentManage";
		}else if(id.isBlank() && !name.isBlank() || courses.isBlank() ) {
			mm.addAttribute("succMsg","find by name");
			mm.addAttribute("allStudent",studentService.findByName(name));
			return "studentManage";
		}else if(id.isBlank() && name.isBlank() && !courses.isBlank()) {
			mm.addAttribute("succMsg","find by course");
			mm.addAttribute("allStudent",studentService.findByCourse(courses));
			return "studentManage";
		}
		mm.addAttribute("errorMsg","result not found");
		return "studentManage";
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
	
	
	
}

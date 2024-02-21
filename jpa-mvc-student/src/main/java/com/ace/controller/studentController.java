package com.ace.controller;

import com.ace.dto.StudentDTO;
import com.ace.service.CourseService;
import com.ace.service.StudentService;
import jakarta.servlet.annotation.MultipartConfig;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLException;

@Controller
@MultipartConfig
@RequestMapping("/")
public class studentController {
    @Autowired
    CourseService courseService;
    @Autowired
    StudentService studentService;

    @GetMapping("/studentRegister")
    public String register(@ModelAttribute StudentDTO student, ModelMap modelMap){
        modelMap.addAttribute("allCourse",courseService.getAllCourse());
        return "studentRegister";
    }

    @GetMapping("/studentUpdate")
    public String update(@ModelAttribute StudentDTO studentDTO,ModelMap modelMap) throws SQLException, IOException {
        modelMap.addAttribute("updateStudent",studentService.findById(studentDTO.getId()).get(0));
        modelMap.addAttribute("updateId",studentDTO.getId());
        modelMap.addAttribute("allCourse",courseService.getAllCourse());
        return "studentUpdate";
    }

    @GetMapping("/studentManage")
    public String manage(@ModelAttribute StudentDTO studentDTO,ModelMap modelMap) throws SQLException, IOException {
        modelMap.addAttribute("allStudent",studentService.getAllStudents());
        modelMap.addAttribute("allCourse",courseService.getAllCourse());
        return "studentManage";
    }

    @PostMapping("/studentRegister")
    public String registerPost(@ModelAttribute StudentDTO studentDTO,ModelMap modelMap) throws SQLException, IOException {
        studentService.registerStudent(studentDTO);
        return "studentRegister";
    }

    @PostMapping("/studentSearch")
    public String search(@RequestParam(name = "course",required = false)String course, ModelMap modelMap) throws SQLException, IOException {
        modelMap.addAttribute("allStudent",studentService.findByCourse(course));
        modelMap.addAttribute("allCourse",courseService.getAllCourse());
        return "studentManage";
    }

    @PostMapping("/studentUpdate")
    public String updateStudent(@ModelAttribute StudentDTO studentDTO, ModelMap modelMap, RedirectAttributes redirectAttributes) throws SQLException, IOException {
        try {
            studentService.updateStudent(studentDTO);
            System.out.println(studentDTO.getId());
            System.out.println(studentDTO.getName());
            modelMap.addAttribute("allStudent",studentService.getAllStudents());
            modelMap.addAttribute("allCourse",courseService.getAllCourse());
            return "studentManage";
        }catch (Exception e){
            e.printStackTrace();
            return "studentUpdate";
        }
    }
}

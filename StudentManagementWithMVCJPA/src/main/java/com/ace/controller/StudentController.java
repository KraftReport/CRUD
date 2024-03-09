package com.ace.controller;

import com.ace.model.StudentModel;
import com.ace.service.CourseService;
import com.ace.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@Controller
@RequestMapping("/")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @GetMapping("/studentManage")
    public ModelAndView goToStudentManager(ModelMap modelMap){
        var mav = new ModelAndView("studentManage");
        modelMap.addAttribute("courses",courseService.findAllCourse());
        return mav.addObject("students",studentService.getAllStudent());
    }
    @GetMapping("/studentRegister")
    public ModelAndView registerStudent(ModelMap modelMap){
        var mav = new ModelAndView("studentRegister");
        modelMap.addAttribute("courses",courseService.findAllCourse());
        return mav.addObject("student",new StudentModel());
    }

    @GetMapping("/studentUpdate")
    public ModelAndView updateStudent(@RequestParam("id")String id,ModelMap modelMap){
        var mav = new ModelAndView("studentUpdate");
        modelMap.addAttribute("courses",courseService.findAllCourse());
        var line = "";
        var courses = studentService.findById(Integer.parseInt(id)).getCourseList();
        for(int i = 0 ;i<courses.size(); i++){
            line += courses.get(i).getName();
        }
        modelMap.addAttribute("line",line);
        System.err.println(line);
        return mav.addObject("student",studentService.findById(Integer.parseInt(id)));
    }

    @GetMapping("/studentDelete")
    public String delete(@RequestParam("id")String id,ModelMap modelMap){
        if(studentService.deleteStudent(studentService.findById(Integer.parseInt(id)))){
            modelMap.addAttribute("students",studentService.getAllStudent());
            modelMap.addAttribute("succMsg","student is removed ");
            return "studentManage";
        }else {
            modelMap.addAttribute("errorMsg","failed to remove a student");
            modelMap.addAttribute("students",studentService.getAllStudent());
            return "studentManage";
        }
    }

    @GetMapping("/pdf1")
    public String pdf(HttpServletResponse httpServletResponse) throws JRException, IOException, SQLException {
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=student.pdf");
        studentService.getPdf(httpServletResponse);
        return "studentManage";
    }

    @PostMapping("/studentSearch")
    public String searchByCourse(@RequestParam(required = false,value = "course")String course,@RequestParam(required = false,value = "status")String status,ModelMap modelMap){
       try {
           if(course != null && status != null && !course.isEmpty() && !course.isBlank() && !status.isBlank() && !status.isEmpty()){

               if(studentService.findByStatusAndCourse(course,status)!=null){
                   modelMap.addAttribute("students",studentService.findByStatusAndCourse(course,status));
                   return "studentManage";
               }else {
                   modelMap.addAttribute("errorMsg", "failed to search ");
                   modelMap.addAttribute("courses", courseService.findAllCourse());
                   return "studentManage";
               }

           }else if(status.isEmpty()) {
               if (studentService.findByCourse(courseService.findByName(course).getId()) != null) {
                   modelMap.addAttribute("students", studentService.findByCourse(courseService.findByName(course).getId()));
                   modelMap.addAttribute("courses", courseService.findAllCourse());
                   return "studentManage";
               } else {
                   modelMap.addAttribute("errorMsg", "failed to search ");
                   modelMap.addAttribute("courses", courseService.findAllCourse());
                   return "studentManage";
               }
           }else if(course.isEmpty()){
               if(studentService.findByStatus(status)!=null){
                   modelMap.addAttribute("students",studentService.findByStatus(status));
                   modelMap.addAttribute("courses",courseService.findAllCourse());
                   modelMap.addAttribute("succMsg","တွေ့ပြီ");
                   return "studentManage";
               }else {
                   modelMap.addAttribute("error","error တက်နေတယ် ");
                   modelMap.addAttribute("courses",courseService.findAllCourse());
                   return "studentManage";
               }
           }else{
               modelMap.addAttribute("courses",courseService.findAllCourse());
               return "studentManage";
           }
       }catch (Exception e){
           modelMap.addAttribute("students", studentService.getAllStudent());
           modelMap.addAttribute("courses",courseService.findAllCourse());
           return "studentManage";
       }
    }



    @PostMapping("/studentRegister")
    public String register(@ModelAttribute StudentModel studentModel,ModelMap modelMap){
        if(studentService.registerStudent(studentModel)){
            modelMap.addAttribute("succMsg","student is registered successfully");
            modelMap.addAttribute("students",studentService.getAllStudent());
            return "studentManage";
        }else {
            modelMap.addAttribute("errorMsg","register failed");
            return "studentRegister";
        }
    }

    @PostMapping("/studentUpdate")
    public String update(@ModelAttribute StudentModel studentModel,ModelMap modelMap){
        System.err.println(studentModel.getName());
        System.err.println(studentModel.getId());
        if(studentService.updateStudent(studentModel)){
            System.err.println(studentModel);
            modelMap.addAttribute("succMsg","student is updated successfully");
            modelMap.addAttribute("students",studentService.getAllStudent());
            return "studentManage";
        }else {
            modelMap.addAttribute("errorMsg","update failed");
            return "studentUpdate";
        }
    }
}

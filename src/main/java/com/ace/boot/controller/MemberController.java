package com.ace.boot.controller;

import com.ace.boot.entity.Course;
import com.ace.boot.entity.Role;
import com.ace.boot.entity.Student;
import com.ace.boot.entity.User;
import com.ace.boot.repository.EnrollRepository;
import com.ace.boot.service.CourseService;
import com.ace.boot.service.CourseServiceImpl;
import com.ace.boot.service.StudentService;
import com.ace.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private UserService userService;
    @Autowired
    private EnrollRepository enrollRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    public MemberController(CourseServiceImpl mock) {
    }

    @GetMapping("/userUpdate")
    public ModelAndView updateUser(ModelMap modelMap, @RequestParam("id")String id) throws IOException, SQLException {
        var mav = new ModelAndView("/user/userUpdate");
        var user = userService.findById(Long.valueOf(id)).get(0);
        user.setFile(userService.changeBlobToMultipartfile(user.getPhoto()));
        user.setPhotoString(userService.getEncodedPhotoString(user.getFile()));
        return mav.addObject("user",user);
    }

    @GetMapping("/studentManager")
    public ModelAndView getStudentManager(ModelMap modelMap) throws SQLException, IOException {
        var mav = new ModelAndView("/student/studentManager");
        return mav.addObject("student",studentService.allStudents())
                .addObject("course",courseService.getAllCourse()) ;
    }

    @GetMapping("/studentRegister")
    public ModelAndView getStudentRegister(ModelMap modelMap){
        var mav = new ModelAndView("/student/studentRegister");
        modelMap.addAttribute("course",courseService.getAllCourse());
        return mav.addObject("student",new Student());
    }

    @GetMapping("/profile")
    public ModelAndView profile() throws SQLException, IOException {
        return new ModelAndView("/profile").addObject("user",userService.addPhotoString(userService
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())).get(0));
    }

    @GetMapping("/courseManager")
    public ModelAndView courseManager(){
        var mav = new ModelAndView("/course/courseManager");
        System.err.println(courseService.getAllCourse());
        return mav.addObject("course",courseService.getAllCourse());
    }

    @GetMapping("/changePassword")
    public String changePassword(ModelMap modelMap){
        modelMap.addAttribute("showForgot","Request to change password");
        return "changePassword";
    }

    @GetMapping("/close")
    public String closeCourse(@RequestParam("id")String id){
        courseService.closeCourseOpenCourse(Long.valueOf(id));
        return "redirect:/member/courseManager";
    }

    @GetMapping("/studentStatus")
    public String studentStatus(@RequestParam("id")Long id){
        if(studentService.removeStudent(id)){
            return "redirect:/member/studentManager";
        }else {
            return "redirect:/member/studentManager";
        }
    }

    @GetMapping("/open")
    public String openCourse(@RequestParam("id")String id){
        courseService.closeCourseOpenCourse(Long.valueOf(id));
        return "redirect:/member/courseManager";
    }

    @GetMapping("update")
    public ModelAndView getUpdate(@RequestParam("id")String id){
        var mav = new ModelAndView("/course/courseUpdate");
        return mav.addObject("course",courseService.findById(Long.valueOf(id)));
    }

    @GetMapping("/courseRegister")
    public ModelAndView getCourseRegister(){
        var mav = new ModelAndView("/course/courseRegister");
        return mav.addObject("course",new Course());
    }

    @PostMapping("/updateUser")
    public String update(@ModelAttribute User user){
        if(userService.updateUser(user)){
            return "redirect:/member/userUpdate?id="+user.getId();
        }else {
            return "redirect:/member/userUpdate?id="+user.getId();
        }
    }
    @PostMapping("/courseRegister")
    public String postCourseRegister(@ModelAttribute("course") @Validated Course course,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/course/courseRegister";
        }else {
            if (courseService.registerCourse(course)) {
                return "redirect:/member/courseManager";
            } else {
                return "redirect:/member/courseRegister";
            }
        }
    }

    @PostMapping("courseUpdate")
    public String postUpdateCourse(@ModelAttribute("course") @Validated Course course,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/course/courseUpdate";
        }else {
            if (courseService.updateDescriptionAndNameAndLevel(course)) {
                return "redirect:/member/courseManager";
            } else {
                return "redirect:/member/courseUpdate?" + course.getId();
            }
        }
    }

    @PostMapping("/courseSearch")
    public ModelAndView courseSearch(@RequestParam(value = "id",required = false)Long id,
                                     @RequestParam(value = "name",required = false)String name,
                                     @RequestParam(value = "level",required = false)String level){
        var mav = new ModelAndView("/course/courseManager");
        return mav.addObject("course",courseService.searchMethod(id,name,level));
    }
    @GetMapping("/userRegister")
    public ModelAndView register(){
        var mav = new ModelAndView("user/userRegister");
        return mav.addObject("user",new User());
    }
    @PostMapping("/studentRegister")
    public String postStudentRegister(@ModelAttribute("student") @Validated Student student,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/student/studentRegister";
        }else {
            if (studentService.registerStudent(student)) {
                return "redirect:/member/studentManager";
            } else {
                return "redirect:/member/studentRegister";
            }
        }
    }

    @PostMapping("/studentUpdate")
    public String postStudentUpdate(@ModelAttribute @Validated Student student,BindingResult bindingResult) throws SQLException, IOException {
        System.err.println(student.getFile());
        if(studentService.updateStudent(student)){
            return "redirect:/member/studentManager";
        }else {
            return "redirect:/error";
        }
    }

    @PostMapping("/studentSearch")
    public ModelAndView studentSearch(@RequestParam(value = "id",required = false)Long id,
                               @RequestParam(value = "name",required = false)String name,
                               @RequestParam(value = "course",required = false)String course) throws SQLException, IOException {
        var mav = new ModelAndView("/student/studentManager");
        return mav.addObject("student",studentService.searchMethod(id,name,course))
                .addObject("course",courseService.getAllCourse());
    }
    @PostMapping("/userRegister")
    public String userRegister(@ModelAttribute("user")@Validated User user, BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            for(var error : bindingResult.getAllErrors()){
                System.out.println(error.getDefaultMessage());
            }
            return "/user/userRegister";
        } else {

            var auth = SecurityContextHolder.getContext().getAuthentication();
            System.err.println(auth);
            if (userService.registerUser(user)) {
                if (userService.findByEmail(auth.getName()).get(0).getRole() == Role.ADMIN) {
                    return "redirect:/admin/userManager";
                }
                return "redirect:/member/studentManager";
            }
            return "redirect:/member/studentRegister";
        }
    }
}

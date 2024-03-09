package com.ace.controller;

import com.ace.model.CourseModel;
import com.ace.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @GetMapping("/courseManage")
    public ModelAndView courseManager(ModelMap modelMap){
        var mav = new ModelAndView("courseManage");
        return mav.addObject("courses",courseService.findAllCourse());
    }

    @GetMapping("/courseRegister")
    public ModelAndView register(ModelMap modelMap){
        var mav = new ModelAndView("courseRegister");
        return mav.addObject("course",new CourseModel());
    }

    @GetMapping("/courseUpdate")
    public ModelAndView courseUpdate(@RequestParam("id")String id){
        var mav = new ModelAndView("courseUpdate");
        return mav.addObject("courseUpdate",courseService.findById(Integer.parseInt(id)));
    }

    @GetMapping("/courseDelete")
    public String delete(@RequestParam("id")String id,ModelMap modelMap){
        if(courseService.deleteCourse(courseService.findById(Integer.parseInt(id)))){
            modelMap.addAttribute("succMsg","course is closed");
            modelMap.addAttribute("courses",courseService.findAllCourse());
            return "courseManage";
        }else {
            modelMap.addAttribute("errorMsg","fail to close the course");
            modelMap.addAttribute("courses",courseService.findAllCourse());
            return "courseManage";
        }
    }

    @PostMapping("/courseRegister")
    public String registerCourse(ModelMap modelMap, @ModelAttribute CourseModel courseModel){
        if(courseService.addCourse(courseModel)){
            modelMap.addAttribute("succMsg","course is added successfully");
            modelMap.addAttribute("courses",courseService.findAllCourse());
            return "courseManage";
        }else {
            modelMap.addAttribute("errorMsg","course is faild to add");
            modelMap.addAttribute("courses",courseService.findAllCourse());
            return "courseManage";
        }
    }

    @PostMapping("/courseUpdate")
    public String updateCourse(@ModelAttribute CourseModel courseModel,ModelMap modelMap){
        if(courseService.updateCourse(courseModel)){
            modelMap.addAttribute("succMsg","course is updated successfully");
            modelMap.addAttribute("courses",courseService.findAllCourse());
            return "courseManage";
        }else {
            modelMap.addAttribute("errorMsg","course is failed to update");
            modelMap.addAttribute("courseUpdate",courseService.findById(courseModel.getId()));
            return "courseUpdate";
        }
    }
}

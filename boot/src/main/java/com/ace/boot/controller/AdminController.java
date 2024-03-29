package com.ace.boot.controller;

import com.ace.boot.entity.User;
import com.ace.boot.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/delete")
    public String delete(@RequestParam("id")String id){
       if(userService.findById(Long.valueOf(id)).get(0).getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
           return "redirect:/admin/userManager";
       }else {
           if(userService.deleteUser(Long.valueOf(id))){
               return "redirect:/admin/userManager";
           }else{
               return "redirect:/admin/userManager";
           }
       }
    }
    @GetMapping("/userManager")
    public ModelAndView userManager(ModelMap modelMap, HttpSession httpSession){
        var mav = new ModelAndView("/user/userManager");
        httpSession.setAttribute("loginUserName",userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get(0).getName());
        httpSession.setAttribute("currentDate",LocalDate.now());
        modelMap.addAttribute("user",userService.getAllUser());
        return mav.addObject("user",userService.getAllUser());
    }


    @PostMapping("/userSearch")
    public ModelAndView  userSearch(@RequestParam(value = "id",required = false)Long id,@RequestParam(value = "name",required = false)String name,
                           @RequestParam(value = "email")String email,@RequestParam(value = "role")String role) throws SQLException, IOException {
        var mav = new ModelAndView("/user/userManager");
        return mav.addObject("user",userService.searchMethod(id,name,email,role));
    }




}

package com.ace.controller;

import com.ace.model.UserModel;
import com.ace.service.UserService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

@Controller
@RequestMapping("/")
@MultipartConfig
public class userController {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender javaMailSender;
    HashMap<String ,String > mapCode = new HashMap<>();
    @GetMapping("/userRegister")
    public ModelAndView goToForm(){
        var mav = new ModelAndView("userRegister");
        return mav.addObject("UserDTO",new UserModel());
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/userManage")
    public String gotomanage(@ModelAttribute UserModel userModel, ModelMap modelMap){
        modelMap.addAttribute("Users",userService.showAllUser());
        return "userManage";
    }

    @GetMapping("/logout")
    public String logout(ModelMap modelMap,HttpSession httpSession){
        httpSession.removeAttribute("loginUserName");
        httpSession.removeAttribute("loginUserRole");
        modelMap.addAttribute("UserDTO",new UserModel());
        return "login";
    }

    @GetMapping("/userDelete")
    public String delete(@RequestParam("id")String id,ModelMap modelMap,HttpSession httpSession) throws SQLException, IOException {
       if(userService.findById(Integer.parseInt(id)).getName().equals(httpSession.getAttribute("loginUserName"))){
           modelMap.addAttribute("errorMsg","Can't delete current login User");
           modelMap.addAttribute("Users",userService.showAllUser());
           return "userManage";
       }else {
           if(userService.deleteUser(Integer.parseInt(id))){
               modelMap.addAttribute("succMsg","user is deleted successfully");
               modelMap.addAttribute("Users",userService.showAllUser());
               return "userManage";
           }else {
               modelMap.addAttribute("errorMsg","failed to delete a user");
               modelMap.addAttribute("Users",userService.showAllUser());
               return "userManage";
           }
       }
    }

    @GetMapping("/changePassword")
    public String change(){
        return "changePassword";
    }

    @GetMapping("/forgotPassword")
    public String forgot(ModelMap modelMap) {
        return "forgetPassword";

    }

    @GetMapping({"/","/login"})
    public ModelAndView goToLogin(){
            var mav = new ModelAndView("login");
            return mav.addObject("UserDTO",new UserModel());
    }

    @GetMapping("/userUpdate")
    public ModelAndView goToUpdate(@ModelAttribute UserModel userModel, ModelMap modelMap) throws SQLException, IOException {
        var mav = new ModelAndView("userUpdate");
        var found = userService.findById(userModel.getId());
        System.out.println(found.getPhotoString());
        modelMap.addAttribute("encodeString",found.getPhotoString());
        return mav.addObject("UserDTO",found);
    }

    @GetMapping("/pdf")
    public String pdf(HttpServletResponse httpServletResponse) throws JRException, IOException {
    httpServletResponse.setContentType("application/pdf");
    httpServletResponse.setHeader("Content-Disposition", "attachment; filename=user.pdf");
    userService.getPdf(httpServletResponse);
    return "userManage";
    }

    @GetMapping("/excel")
    public String excel(HttpServletResponse httpServletResponse) throws JRException, IOException {
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=user.xls");
        userService.getExcel(httpServletResponse);
        return "userManage";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, ModelMap modelMap, RedirectAttributes redirectAttributes, HttpSession httpSession){
        try {
            if(userService.userLogin(userModel)){
                modelMap.addAttribute("succMsg","login successful");
                httpSession.setAttribute("loginUserName",userService.findByEmail(userModel.getEmail()).getName());
                httpSession.setAttribute("loginUserRole",userService.findByEmail(userModel.getEmail()).getRole());
                httpSession.setAttribute("loginUserId",userService.findByEmail(userModel.getEmail()).getId());
                System.err.println(httpSession.getAttribute("loginUserName"));
                return "welcome";
            }else{
                modelMap.addAttribute("errorMsg","login failed");
                redirectAttributes.addFlashAttribute("errorMsg","login failed");
                return "redirect:/login";
            }
        }catch (Exception e){
            modelMap.addAttribute("errorMsg","login failed");
            redirectAttributes.addFlashAttribute("errorMsg","login failed");
            return "redirect:/login";
        }
    }

    @PostMapping("/userUpdate")
    public String update(@ModelAttribute UserModel userModel, RedirectAttributes redirectAttributes, ModelMap modelMap,HttpSession httpSession) throws SQLException, IOException {
        if(userModel.getPhoto().isEmpty()){
            userModel.setPhoto(userService.findById(userModel.getId()).getPhoto());
        }
        if(userService.updateUser(userModel)){
            redirectAttributes.addFlashAttribute("succMsg","user is updated successfully");
            httpSession.setAttribute("loginUserName",userModel.getName());
            httpSession.setAttribute("loginUserRole",userModel.getRole());
            return "redirect:/welcome";
        }else {
            redirectAttributes.addFlashAttribute("errorMsg","update failed");
            return "redirect:/userUpdate";
        }

    }

    @PostMapping("/forgotPassword")
    public String forget(@RequestParam("email")String email,@RequestParam(required = false,value = "code",name = "code")String code,ModelMap modelMap){
        if(code==null){
            if(userService.findByEmail(email)==null){
                modelMap.addAttribute("errorMsg","no user with email address is found");
                return "forgotPassword";
            }else {
                var generatedRandomCode = generateRandomCode();
                mapCode.put(email,generatedRandomCode);
                sendEmail(email,generatedRandomCode);
                modelMap.addAttribute("succMsg","email is found OTP code is send to your email");
                modelMap.addAttribute("emailFound",email);
                return "forgetPassword";
            }
        } else{
                var getCode = mapCode.get(email.trim());
                if(getCode!=null && getCode.equals(code.trim())){
                    modelMap.addAttribute("succMsg","OTP is correct ");
                    modelMap.addAttribute("oldPassword",userService.findByEmail(email).getPassword());
                    modelMap.addAttribute("email",email);
                    return "changePassword";
                }else {
                    modelMap.addAttribute("errorMsg","OTP is not correct");
                    modelMap.addAttribute("emailFound",email);
                    return "forgotPassword";
                }
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("email")String email,@RequestParam("password") String password,@RequestParam("oldPassword")String oldPassword, ModelMap modelMap) throws SQLException, IOException {
        var user = userService.findByEmail(email);
        user.setPassword(password);
        if(userService.updateUser(user)){
            modelMap.addAttribute("succMsg","password is changed successfully");
            modelMap.addAttribute("UserDTO",new UserModel());
            return "login";
        }else {
            modelMap.addAttribute("errorMsg","password is faild to change");
            modelMap.addAttribute("oldPassword",oldPassword);
            return "changePassword";
        }
    }

    @PostMapping("/userRegister")
    public String register(@ModelAttribute UserModel user, RedirectAttributes redirectAttributes,HttpSession httpSession) throws SQLException, IOException {
        if(userService.registerUser(user)){
            redirectAttributes.addFlashAttribute("succMsg","register successful");
            if(httpSession.getAttribute("loginUserName")==null){
                return "redirect:/login";
            }
            redirectAttributes.addFlashAttribute("users",userService.showAllUser());
            return "redirect:/userManage";
        }else{
            redirectAttributes.addFlashAttribute("errorMsg","register failed");
            return "redirect:/userRegister";
        }
    }

    private String generateRandomCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private void sendEmail(String email,String code){
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("OTP code to reset your password");
        simpleMailMessage.setText("your OTP code is " + code);
        javaMailSender.send(simpleMailMessage);
    }
}

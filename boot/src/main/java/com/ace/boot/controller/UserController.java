package com.ace.boot.controller;

import com.ace.boot.entity.Role;
import com.ace.boot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;
    HashMap<String ,String > mapCode = new HashMap<>();

    @GetMapping("/login")
    public String login() {return "login";}

    @GetMapping("/user/userRegister")
    public String loginOne() {
        return "/user/userRegister";
    }

    @GetMapping("/")
    public String loginTwo(HttpSession httpSession) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> ((GrantedAuthority) a).getAuthority().equals(Role.ADMIN.name()))) {
            System.err.println("one"+auth.getName());
            httpSession.setAttribute("loginUserName",userService.findByEmail(auth.getName()).get(0).getName());
            return "redirect:/admin/userManager";
        } else if(auth.getAuthorities().stream().anyMatch(a -> ((GrantedAuthority) a).getAuthority().equals(Role.MEMBER.name()))){
            httpSession.setAttribute("loginUserName",userService.findByEmail(auth.getName()).get(0).getName());
            httpSession.setAttribute("currentDate", LocalDate.now());
            return "redirect:/member/studentManager";
        }
        System.out.println("login Not success");
        return "/login";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam(value = "email",required = false)String email,
                                 @RequestParam(value = "code",required = false)String code,
                                 @RequestParam(value = "newPassword",required = false)String password,
                                 ModelMap modelMap) {
        System.err.println(email);
        System.err.println(code);
        System.err.println(password);
        if (StringUtils.hasLength(email) && !StringUtils.hasLength(code) && !StringUtils.hasLength(password)) {
            if (userService.findByEmail(email) != null) {
                var OTP = userService.generateRandomCode();
                userService.sendEmail(email, OTP);
                mapCode.put(email, OTP);
                modelMap.addAttribute("done", "yes");
                modelMap.addAttribute("sendCode", "yes");
                modelMap.addAttribute("showForgot", "OTP code is send to your email :-D");
                modelMap.addAttribute("sendEmail", email);
                modelMap.addAttribute("emailSend", "yes");
                return "changePassword";
            } else {
                modelMap.addAttribute("showForgot", "email not found !!! TRY AGAIN?");
                return "changePassword";
            }
        } else if (StringUtils.hasLength(code)) {
            var getCode = mapCode.get(email.trim());
            if (code.equals(getCode)) {
                modelMap.addAttribute("done", "yes");
                modelMap.addAttribute("emailSend", "yes");
                modelMap.addAttribute("sendCode", "no");
                modelMap.addAttribute("OTP", "ok");
                modelMap.addAttribute("sendEmail", email);
                modelMap.addAttribute("showForgot", "OTP is correct.Change password now");
                return "changePassword";
            } else {
                modelMap.addAttribute("done", "yes");
                modelMap.addAttribute("sendCode", "yes");
                modelMap.addAttribute("sendEmail", email);
                modelMap.addAttribute("emailSend", "yes");
                modelMap.addAttribute("showForgot", "OTP is not correct Try again");
                return "changePassword";
            }
        } else if (StringUtils.hasLength(password)) {
            var update = userService.findByEmail(email).get(0);
            update.setPassword(password);
            if (userService.updateUser(update)) {
                return "redirect:/login";
            } else {
                return "redirect:/member/changePassword";
            }
        }else {
            return "redirect:/member/changePassword";
        }
    }

}
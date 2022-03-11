package com.yanxu.book.controller;

import com.yanxu.book.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@Controller
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

    @GetMapping("login")
    public String login(HttpServletRequest request) {
        request.getCookies();
        return "login";
    }

    @RequestMapping("loginFailure")
    public String loginFailure(){
        return "loginFailure";
    }

}

package com.yanxu.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerLoginController {

    @RequestMapping("login")
    public String managerLogin(){
        return "manager";
    }
}

package com.yanxu.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager")
public class ManagerLoginController {

    @RequestMapping("login")
    public String managerLogin(){

        return "managerLogin";
    }

}

package com.yanxu.book.controller;


import com.yanxu.book.service.SettingSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/manager")
@Controller
public class SettingSaveController {

    @Autowired
    SettingSaveService settingSaveService;

    @RequestMapping("settingSave")
    public void settingSave(@RequestParam("parameterName") String parameterName,@RequestParam("parameterValue") String parameterValue){
        settingSaveService.saveByname(parameterName,parameterValue);
    }

    @RequestMapping("setting")
    public String toSetting(){
        return "ManagerSetting";
    }

}

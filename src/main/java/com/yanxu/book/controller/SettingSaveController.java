package com.yanxu.book.controller;


import com.yanxu.book.service.SettingSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/manager")
@Controller
public class SettingSaveController {

    @Autowired
    SettingSaveService settingSaveService;

    @GetMapping("settingSave")
    @ResponseBody
    public String settingSave(@RequestParam("parameterName") String parameterName,@RequestParam("parameterValue") String parameterValue){
        settingSaveService.saveByname(parameterName,parameterValue);
        return "成功";
    }

    @RequestMapping("setting")
    public String toSetting(){
        return "ManagerSetting";
    }

}

package com.yanxu.book.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yanxu.book.cache.UserInfoCache;
import com.yanxu.book.entity.User;
import com.yanxu.book.mapper.UserMapper;
import com.yanxu.book.service.UserPasswordReset;
import com.yanxu.book.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/password")
public class UserPasswordController {

    @Autowired
    private PageParam<User> pageParamUser;

    @Autowired
    private UserPasswordReset userPasswordReset;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("toUpdate")
    public String toUpdate(){
        return "ManagerResetPassword";
    }

    @GetMapping("toUserUpdate")
    public String toUserUpdate(){
        return "UserResetPassword";
    }

    @GetMapping("update")
    public String restore(Model model, String password,String rowPassword){
        String name=UserInfoCache.map.get("name");
        User getuser=userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName,name));
        if (getuser.getPassWord().equals(rowPassword)){
        User user = new User();
            getuser.setPassWord(password);
        userMapper.update(getuser,new UpdateWrapper<User>().lambda().eq(User::getUserName,name));
        model.addAttribute("mag","成功");
        return "ManagerResetPassword";
        }else {
            model.addAttribute("mag","原密码不正确");
            return "ManagerResetPassword";
        }
    }

    @GetMapping("userRestore")
    public String userRestore(Model model, String password,String rowPassword){
        String name=UserInfoCache.map.get("name");
        User getuser=userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName,name));
        if (getuser.getPassWord().equals(rowPassword)){
            getuser.setPassWord(password);
            userMapper.update(getuser,new UpdateWrapper<User>().lambda().eq(User::getUserName,name));
            model.addAttribute("mag","成功");
            return "UserResetPassword";
        }else {
            model.addAttribute("mag","原密码不正确");
            return "UserResetPassword";
        }
    }
}

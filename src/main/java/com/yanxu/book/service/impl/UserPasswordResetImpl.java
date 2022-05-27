package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.User;
import com.yanxu.book.mapper.UserMapper;
import com.yanxu.book.service.UserPasswordReset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserPasswordResetImpl extends ServiceImpl<BaseMapper<User>, User> implements UserPasswordReset {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list(User user) {
        List<User> userList=new LinkedList<>();
        userList.add(userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName,user.getUserName())));
        return userList;
    }
}

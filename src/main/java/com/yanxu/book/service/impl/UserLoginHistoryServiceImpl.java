package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.User;
import com.yanxu.book.entity.UserLoginHistory;
import com.yanxu.book.service.UserLoginHistoryService;
import com.yanxu.book.service.UserLoginService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginHistoryServiceImpl extends ServiceImpl<BaseMapper<UserLoginHistory>, UserLoginHistory> implements UserLoginHistoryService {

    @Override
    public List<UserLoginHistory> list(UserLoginHistory userLoginHistory) {
        return null;
    }
}

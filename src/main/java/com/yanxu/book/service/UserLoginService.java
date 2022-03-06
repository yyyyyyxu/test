package com.yanxu.book.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanxu.book.entity.User;
import com.yanxu.book.entity.UserLoginHistory;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserLoginService extends IService<User>, UserDetailsService,BasePageService<UserLoginHistory,UserLoginHistory> {
}

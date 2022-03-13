package com.yanxu.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanxu.book.entity.Setting;
import com.yanxu.book.entity.UserLoginHistory;

public interface SettingSaveService extends IService<Setting> {

    public void saveByname(String name,String value);
}

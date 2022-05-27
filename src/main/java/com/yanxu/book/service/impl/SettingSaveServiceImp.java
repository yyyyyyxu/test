package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.Setting;
import com.yanxu.book.mapper.SettingMapper;
import com.yanxu.book.service.SettingSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SettingSaveServiceImp extends ServiceImpl<BaseMapper<Setting>,Setting> implements SettingSaveService {

    @Autowired
    SettingMapper settingMapper;


    @Override
    public void saveByname(String name, String value) {
        Setting setting=new Setting();
        setting.setParameterValue(value);
        if(name.equals("overdueReturnBookTask")){
            settingMapper.update(setting,new UpdateWrapper<Setting>().lambda().eq(Setting::getParameterName,"cron").eq(Setting::getParameterCode,"4"));
            log.info("Task:{},value:{}","overdueReturnBookTask",value);
        }else {
            settingMapper.update(setting,new UpdateWrapper<Setting>().lambda().eq(Setting::getParameterName,name));
            log.info("Task:{},value:{}",name,value);
        }

    }
}

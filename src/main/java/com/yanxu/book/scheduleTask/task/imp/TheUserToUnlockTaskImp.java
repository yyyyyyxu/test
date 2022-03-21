package com.yanxu.book.scheduleTask.task.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanxu.book.config.SpringContextUtil;
import com.yanxu.book.entity.Setting;
import com.yanxu.book.entity.User;
import com.yanxu.book.mapper.SettingMapper;
import com.yanxu.book.mapper.UserMapper;
import com.yanxu.book.scheduleTask.task.Task;
import com.yanxu.book.service.UserLoginService;
import com.yanxu.book.service.impl.UserLoginServiceImp;
import com.yanxu.book.settingEnum.ParameterCodeEnum;
import lombok.Data;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Component
public class TheUserToUnlockTaskImp implements Task {


    private String taskName;

    private String taskCode = "5";

    private static TheUserToUnlockTaskImp theUserToUnlockTask;

    public static TheUserToUnlockTaskImp getTheUserToUnlockTask() {
        if (theUserToUnlockTask == null) {
            synchronized (TheUserToUnlockTaskImp.class) {
                if (theUserToUnlockTask == null) {
                    theUserToUnlockTask = new TheUserToUnlockTaskImp();
                    theUserToUnlockTask.setTaskName("TheUserToUnlockTask");
                }
            }
        }
        return theUserToUnlockTask;
    }

    @Override
    public Task getInstance() {
        return TheUserToUnlockTaskImp.getTheUserToUnlockTask();
    }

    @Override
    public String getName() {
        return taskName;
    }

    @Override
    public String getCode() {
        return taskCode;
    }

    @Override
    public void run() {
        AbstractApplicationContext ac = (AbstractApplicationContext) SpringContextUtil.getApplicationContext();
        SettingMapper settingMapper = ac.getBean(SettingMapper.class);
        UserMapper userMapper=ac.getBean(UserMapper.class);
        UserLoginService userLoginService=ac.getBean(UserLoginServiceImp.class);

        Setting setting=settingMapper.selectOne(new QueryWrapper<Setting>().lambda().eq(Setting::getParameterName, ParameterCodeEnum.THE_USER_TO_UNLOCK_TASK_RETAIN.getParameterName()).eq(Setting::getParameterCode,ParameterCodeEnum.THE_USER_TO_UNLOCK_TASK_RETAIN.getParameterCode()));
        int minute=Integer.parseInt(setting.getParameterValue());

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,-minute);

        List<User> userList=userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getFaultTime,3).le(User::getUpdateTime,calendar.getTime()));
        userList.forEach(x->{
            x.setFaultTime(0);
        });
        userLoginService.updateBatchById(userList);

    }
}

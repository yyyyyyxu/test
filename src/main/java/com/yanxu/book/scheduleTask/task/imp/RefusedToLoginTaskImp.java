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
public class RefusedToLoginTaskImp implements Task {


    private String taskName;

    private String taskCode = "16";

    private static RefusedToLoginTaskImp refusedToLoginTaskImp;

    public static RefusedToLoginTaskImp getRefusedToLoginTask() {
        if (refusedToLoginTaskImp == null) {
            synchronized (OverdueReturnBookTaskImp.class) {
                if (refusedToLoginTaskImp == null) {
                    refusedToLoginTaskImp = new RefusedToLoginTaskImp();
                    refusedToLoginTaskImp.setTaskName("RefusedToLoginTask");
                }
            }
        }
        return refusedToLoginTaskImp;
    }

    @Override
    public Task getInstance() {
        return RefusedToLoginTaskImp.getRefusedToLoginTask();
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

        Setting setting=settingMapper.selectOne(new QueryWrapper<Setting>().lambda().eq(Setting::getParameterName, ParameterCodeEnum.REFUSED_TOLOGIN.getParameterName()).eq(Setting::getParameterCode,ParameterCodeEnum.REFUSED_TOLOGIN.getParameterCode()));
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

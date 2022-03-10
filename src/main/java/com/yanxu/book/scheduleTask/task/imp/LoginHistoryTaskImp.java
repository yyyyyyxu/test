package com.yanxu.book.scheduleTask.task.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanxu.book.entity.Setting;
import com.yanxu.book.entity.UserLoginHistory;
import com.yanxu.book.mapper.SettingMapper;
import com.yanxu.book.mapper.UserLoginHistoryMapper;
import com.yanxu.book.service.UserLoginHistoryService;
import com.yanxu.book.settingEnum.ParameterCodeEnum;
import com.yanxu.book.mapper.UserMapper;
import com.yanxu.book.scheduleTask.task.Task;
import com.yanxu.book.util.DateFormatUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Data
@Component
public class LoginHistoryTaskImp implements Task {

    @Autowired
    UserLoginHistoryMapper userLoginHistoryMapper;

    @Autowired
    UserLoginHistoryService userLoginHistoryService;

    @Autowired
    SettingMapper settingMapper;

    private static LoginHistoryTaskImp loginHistoryTaskImp;

    private String taskName;

    private String taskCode="1";

    public static LoginHistoryTaskImp getLoginHistoryTask() {
        if (loginHistoryTaskImp == null) {
            synchronized (BorrowHistoryTaskImp.class) {
                if (loginHistoryTaskImp == null) {
                    loginHistoryTaskImp = new LoginHistoryTaskImp();
                    loginHistoryTaskImp.setTaskName("LoginHistoryTask");
                }
            }
        }
        return loginHistoryTaskImp;
    }

    @Override
    public void run() {
        Setting setting=settingMapper.selectOne(new QueryWrapper<Setting>().lambda().eq(Setting::getParameterCode,ParameterCodeEnum.LOGININ_HISTORY_TASK_RETAIN.getParameterCode()).
                eq(Setting::getParameterName,ParameterCodeEnum.LOGININ_HISTORY_TASK_RETAIN.getParameterName()));

        Date date=new Date();
        String nowFormat=DateFormatUtil.LongStringFormat(date);
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,-Integer.valueOf(setting.getParameterValue()));
        List<UserLoginHistory> userLoginHistories=userLoginHistoryMapper.selectList(new QueryWrapper<UserLoginHistory>().lambda().le(UserLoginHistory::getCreatTime,calendar.getTime()));
        List<Integer> id=userLoginHistories.stream().map(x-> Integer.valueOf(x.getID())).collect(Collectors.toList());
        userLoginHistoryService.removeByIds(id);

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
    public Task getInstance() {
        return LoginHistoryTaskImp.getLoginHistoryTask();
    }
}

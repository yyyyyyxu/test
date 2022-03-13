package com.yanxu.book.scheduleTask;

import com.yanxu.book.config.SpringContextUtil;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.mapper.SettingMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduleStart implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        TaskScheduleService taskScheduleService=new TaskScheduleService();
//        taskScheduleService.start();
    }

}

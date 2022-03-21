package com.yanxu.book.scheduleTask;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanxu.book.config.SpringContextUtil;
import com.yanxu.book.entity.Setting;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.mapper.SettingMapper;
import com.yanxu.book.scheduleTask.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class TaskScheduleService {



    static ConcurrentHashMap<String, ScheduledFuture> container = new ConcurrentHashMap<>();


    public  void start() {
        AbstractApplicationContext ac = (AbstractApplicationContext) SpringContextUtil.getApplicationContext();
        SettingMapper settingMapper = ac.getBean(SettingMapper.class);
        if (ThreadPoolUtil.getRunnableList().size() != 0) {
            for (Task task:ThreadPoolUtil.getRunnableList()) {
                Setting setting=settingMapper.selectOne(new QueryWrapper<Setting>().lambda().eq(Setting::getParameterCode, task.getCode())
                        .eq(Setting::getParameterName,"cron"));
                if (!container.containsKey(task.getInstance().getName())) {
                    ThreadPoolTaskScheduler threadPoolTaskScheduler = ThreadPoolUtil.getInstance();
                    ScheduledFuture future = threadPoolTaskScheduler.schedule(task.getInstance(), new CronTrigger(setting.getParameterValue()));
                    container.put(task.getName(), future);
                }
            }
        }
    }

}



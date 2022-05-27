package com.yanxu.book.scheduleTask.task.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanxu.book.config.SpringContextUtil;
import com.yanxu.book.entity.BookBorrowHistory;
import com.yanxu.book.entity.Setting;
import com.yanxu.book.entity.UserLoginHistory;
import com.yanxu.book.mapper.BookBorrowHistoryMapper;
import com.yanxu.book.mapper.SettingMapper;
import com.yanxu.book.scheduleTask.task.Task;
import com.yanxu.book.service.BookBorrowHistoryService;
import com.yanxu.book.settingEnum.ParameterCodeEnum;
import com.yanxu.book.util.DateFormatUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
@Slf4j
public class BorrowHistoryTaskImp implements Task {

    @Autowired
    private BookBorrowHistoryMapper bookBorrowHistoryMapper;

    @Autowired
    private BookBorrowHistoryService bookBorrowHistoryService;

    @Autowired
    SettingMapper settingMapper;

    private static BorrowHistoryTaskImp borrowHistoryTask;

    private String taskName;

    private String taskCode="2";

    public static BorrowHistoryTaskImp getBorrowHistoryTask() {
        if (borrowHistoryTask == null) {
            synchronized (BorrowHistoryTaskImp.class) {
                if (borrowHistoryTask == null) {
                    borrowHistoryTask = new BorrowHistoryTaskImp();
                    borrowHistoryTask.setTaskName("borrowHistoryTask");
                }
            }
        }
        return borrowHistoryTask;
    }

    @Override
    public void run() {
        log.info("BorrowHistoryTask start");
        AbstractApplicationContext ac = (AbstractApplicationContext) SpringContextUtil.getApplicationContext();
        SettingMapper settingMapper = ac.getBean(SettingMapper.class);
        BookBorrowHistoryMapper bookBorrowHistoryMapper = ac.getBean(BookBorrowHistoryMapper.class);
        Setting setting=settingMapper.selectOne(new QueryWrapper<Setting>().lambda().eq(Setting::getParameterCode, ParameterCodeEnum.BORROW_HISTORY_TASK_RETAIN.getParameterCode()).
                eq(Setting::getParameterName,ParameterCodeEnum.BORROW_HISTORY_TASK_RETAIN.getParameterName()));
        Date date=new Date();
        String nowFormat= DateFormatUtil.LongStringFormat(date);
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,-Integer.valueOf(setting.getParameterValue()));
        List<BookBorrowHistory> borrowHistories=bookBorrowHistoryMapper.selectList(new QueryWrapper<BookBorrowHistory>().lambda().le(BookBorrowHistory::getCreatTime,calendar.getTime()));
        List<String> id=borrowHistories.stream().map(x-> x.getID()).collect(Collectors.toList());
        if (!id.isEmpty()){
            bookBorrowHistoryMapper.deleteBatchIds(id);
        }
    }

    @Override
    public Task getInstance() {
        return BorrowHistoryTaskImp.getBorrowHistoryTask();
    }

    @Override
    public String getName() {
        return taskName;
    }

    @Override
    public String getCode() {
        return taskCode;
    }
}

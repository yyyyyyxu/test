package com.yanxu.book.scheduleTask.task.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanxu.book.entity.ExpireyUserRemind;
import com.yanxu.book.entity.Setting;
import com.yanxu.book.entity.ToEmail;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.mapper.SettingMapper;
import com.yanxu.book.scheduleTask.task.Task;
import com.yanxu.book.settingEnum.ParameterCodeEnum;
import com.yanxu.book.util.DateFormatUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Component
public class RemindReturnBookTaskImp implements Task {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    SettingMapper settingMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    private String taskName;

    private String taskCode = "3";

    private static RemindReturnBookTaskImp remindReturnBookTaskImp;

    public static RemindReturnBookTaskImp getRemindReturnBookTask() {
        if (remindReturnBookTaskImp == null) {
            synchronized (BorrowHistoryTaskImp.class) {
                if (remindReturnBookTaskImp == null) {
                    remindReturnBookTaskImp = new RemindReturnBookTaskImp();
                    remindReturnBookTaskImp.setTaskName("RemindReturnBookTask");
                }
            }
        }
        return remindReturnBookTaskImp;
    }

    @Override
    public void run() {

        Setting setting = settingMapper.selectOne(new QueryWrapper<Setting>().lambda().eq(Setting::getParameterCode, ParameterCodeEnum.REMIND_RETURNBOOK_TASK_RETAIN.getParameterCode()).
                eq(Setting::getParameterName, ParameterCodeEnum.REMIND_RETURNBOOK_TASK_RETAIN.getParameterName()));
        Integer daysInAdvance = Integer.parseInt(setting.getParameterValue());
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -daysInAdvance);
        String deadLine = DateFormatUtil.LongStringFormat(calendar.getTime());

        List<ExpireyUserRemind> expireyUserRemindList = bookMapper.getExpireyUser(deadLine, DateFormatUtil.LongStringFormat(new Date()));

        expireyUserRemindList.forEach(x -> {

            ToEmail toEmail = new ToEmail();
            String[] getEmail=new String[1];
            getEmail[0]=x.getEmail();

            toEmail.setTos(getEmail);
            toEmail.setSubject("您近期有将要到期归还的图书");
            toEmail.setContent("图书名："+x.getBookName()+"归还时间："+x.getExpirationTime());


            SimpleMailMessage message = new SimpleMailMessage();
            //谁发的
            message.setFrom(from);
            //谁要接收
            message.setTo(toEmail.getTos());
            //邮件标题
            message.setSubject(toEmail.getSubject());
            //邮件内容
            message.setText(toEmail.getContent());
            try {
                mailSender.send(message);
            } catch (MailException e) {
                e.printStackTrace();
            }
        });


    }


    @Override
    public Task getInstance() {
        return RemindReturnBookTaskImp.getRemindReturnBookTask();
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

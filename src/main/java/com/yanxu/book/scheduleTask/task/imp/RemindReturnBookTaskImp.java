package com.yanxu.book.scheduleTask.task.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanxu.book.config.SpringContextUtil;
import com.yanxu.book.entity.Email;
import com.yanxu.book.entity.ExpireyUserRemind;
import com.yanxu.book.entity.Setting;
import com.yanxu.book.entity.ToEmail;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.mapper.SettingMapper;
import com.yanxu.book.scheduleTask.task.Task;
import com.yanxu.book.settingEnum.ParameterCodeEnum;
import com.yanxu.book.util.DateFormatUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Component
@Slf4j
public class RemindReturnBookTaskImp implements Task {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    SettingMapper settingMapper;

    @Autowired
    private JavaMailSender mailSender;


    private String taskName;

    private String taskCode = "3";

    private static RemindReturnBookTaskImp remindReturnBookTaskImp;

    public static RemindReturnBookTaskImp getRemindReturnBookTask() {
        if (remindReturnBookTaskImp == null) {
            synchronized (RemindReturnBookTaskImp.class) {
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
        log.info("RemindReturnBookTask start");
        AbstractApplicationContext ac = (AbstractApplicationContext) SpringContextUtil.getApplicationContext();
        SettingMapper settingMapper = ac.getBean(SettingMapper.class);
        BookMapper bookMapper = ac.getBean(BookMapper.class);
        Email email = ac.getBean(Email.class);
        JavaMailSender mailSender = ac.getBean(JavaMailSender.class);
        Setting setting = settingMapper.selectOne(new QueryWrapper<Setting>().lambda().eq(Setting::getParameterCode, ParameterCodeEnum.REMIND_RETURNBOOK_TASK_RETAIN.getParameterCode()).
                eq(Setting::getParameterName, ParameterCodeEnum.REMIND_RETURNBOOK_TASK_RETAIN.getParameterName()));
        Integer daysInAdvance = Integer.parseInt(setting.getParameterValue());
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, daysInAdvance);
        String deadLine = DateFormatUtil.LongStringFormat(calendar.getTime());

        List<ExpireyUserRemind> expireyUserRemindList = bookMapper.getExpireyUser(deadLine, DateFormatUtil.LongStringFormat(new Date()));

        expireyUserRemindList.forEach(x -> {


            try {
                String returnDate = DateFormatUtil.LongStringFormat(x.getExpirationTime());
                ToEmail toEmail = new ToEmail();
                String[] getEmail = new String[1];
                getEmail[0] = x.getEmail();

                toEmail.setTos(getEmail);
                toEmail.setSubject("您近期有将要到期归还的图书");
                toEmail.setContent("图书名：" + x.getBookName() + "归还时间：" + returnDate);


                SimpleMailMessage message = new SimpleMailMessage();
                //谁发的
                message.setFrom(email.getFrom());
                //谁要接收
                message.setTo(toEmail.getTos());
                //邮件标题
                message.setSubject(toEmail.getSubject());
                //邮件内容
                message.setText(toEmail.getContent());
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

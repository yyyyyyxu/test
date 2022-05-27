package com.yanxu.book.scheduleTask.task.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yanxu.book.config.SpringContextUtil;
import com.yanxu.book.entity.Email;
import com.yanxu.book.entity.ExpireyUserRemind;
import com.yanxu.book.entity.ToEmail;
import com.yanxu.book.entity.User;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.mapper.SettingMapper;
import com.yanxu.book.mapper.UserMapper;
import com.yanxu.book.scheduleTask.task.Task;
import com.yanxu.book.util.DateFormatUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@Component
@Slf4j
public class OverdueReturnBookTaskImp implements Task {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    SettingMapper settingMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private JavaMailSender mailSender;


    private String taskName;

    private String taskCode = "4";

    private static OverdueReturnBookTaskImp overdueReturnBookTaskImp;

    public static OverdueReturnBookTaskImp getOverdueReturnBookTask() {
        if (overdueReturnBookTaskImp == null) {
            synchronized (OverdueReturnBookTaskImp.class) {
                if (overdueReturnBookTaskImp == null) {
                    overdueReturnBookTaskImp = new OverdueReturnBookTaskImp();
                    overdueReturnBookTaskImp.setTaskName("OverdueReturnBookTask");
                }
            }
        }
        return overdueReturnBookTaskImp;
    }

    @Override
    public void run() {
        log.info("OverdueReturnBookTask start");
        AbstractApplicationContext ac = (AbstractApplicationContext) SpringContextUtil.getApplicationContext();
        BookMapper bookMapper = ac.getBean(BookMapper.class);
        Email email = ac.getBean(Email.class);
        UserMapper userMapper = ac.getBean(UserMapper.class);
        JavaMailSender mailSender = ac.getBean(JavaMailSender.class);

        List<ExpireyUserRemind> expireyUserRemindList = bookMapper.getOverdueUser(DateFormatUtil.LongStringFormat(new Date()));

        expireyUserRemindList.forEach(x -> {


            try {
                User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, x.getUserName()));
                user.setOverdueTime(x.getOverdueTime() + 1);
                userMapper.update(user, new UpdateWrapper<User>().lambda().eq(User::getUserName, x.getUserName()));
                String returnDate = DateFormatUtil.LongStringFormat(x.getExpirationTime());
                ToEmail toEmail = new ToEmail();
                String[] getEmail = new String[1];
                getEmail[0] = x.getEmail();
                toEmail.setTos(getEmail);
                toEmail.setSubject("您有逾期归还的图书请尽快归还");
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
        return OverdueReturnBookTaskImp.getOverdueReturnBookTask();
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

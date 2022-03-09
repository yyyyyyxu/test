package com.yanxu.book.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //使用mp进行添加操作这个方法执行
    @Override
    public void insertFill(MetaObject metaObject) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR,+8);
        this.setFieldValByName("creatTime",calendar.getTime(),metaObject);
        this.setFieldValByName("updateTime",calendar.getTime(),metaObject);
    }

    //使用mp进行修改操作这个方法执行
    @Override
    public void updateFill(MetaObject metaObject) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR,+8);
        this.setFieldValByName("updateTime",calendar.getTime(),metaObject);
    }
}

package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "login_history")
public class UserLoginHistory implements Serializable {

    private static final long serialVersionUID = 2084905519336665564L;
    @TableId( value = "ID",type = IdType.ASSIGN_UUID)
    private String ID;

    @TableField("user_name")
    public String userName;

    @TableField(value = "creat_time" ,fill = FieldFill.INSERT)
    private Date creatTime;
}

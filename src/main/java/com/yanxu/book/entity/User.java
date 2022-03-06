package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 2179296727127640940L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("user_name")
    private String userName;

    @TableField("pass_word")
    private String passWord;

    @TableField("role")
    private String role;

    @TableField("fault_time")
    private int faultTime;

    @TableField(value = "creat_time" ,fill = FieldFill.INSERT)
    public Date creatTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    public Date updateTime;
}

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

    @TableField("user_id")
    private String userId;

    @TableField("pass_word")
    private String passWord;

    @TableField("role")
    private String role;

    @TableField("email")
    private String email;

    @TableField("borrow_days")
    private int borrowDays;

    @TableField("number_borrow")
    private int numberOfBorrow;

    @TableField("maxnumber_borrow")
    private int maxNumberOfBorrow;

    @TableField("overdue_time")
    private int overdueTime;

    @TableField("fault_time")
    private int faultTime;

    @TableField(value = "creat_time" ,fill = FieldFill.INSERT)
    public Date creatTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    public Date updateTime;
}

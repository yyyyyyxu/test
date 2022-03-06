package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.sun.javafx.beans.IDProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;



@Data
public class Book implements Serializable {
    private static final long serialVersionUID = -745198244513964252L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
     public String id;

    @TableField("book_code")
    public String bookCode;

    @TableField("book_name")
    public String bookName;

    @TableField("user_id")
    public String userId;

    //0-没人借阅 1-有人借阅
    public String flag;

    @TableField(value = "creat_time" ,fill = FieldFill.INSERT)
    public Date creatTime;

        @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    public Date updateTime;

    public byte[] image;

}

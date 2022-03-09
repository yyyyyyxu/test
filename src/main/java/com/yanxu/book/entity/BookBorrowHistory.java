package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bookborrow_history")
public class BookBorrowHistory implements Serializable {
   private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String ID;

    @TableField("user_name")
    private String userName;
 /**
  * 借阅类型
  */
    @TableField("borrowing_bookname")
    private String borrowingBookname;


    @TableField(value = "creat_time", fill = FieldFill.INSERT)
    private Date creatTime;
}

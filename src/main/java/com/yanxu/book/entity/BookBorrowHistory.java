package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBorrowHistory implements Serializable {
   private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String ID;

    @TableField("user_name")
    private String userName;
 /**
  * 借阅类型
  */
 @TableField("borrowing_type")
    private String borrowingType;

    @TableField(value = "creat_time", fill = FieldFill.INSERT)
    private Date creatTime;
}

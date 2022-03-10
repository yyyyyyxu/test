package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
public class ExpireyUserRemind {

    private String bookName;

    private Date expirationTime;

    private String email;

}

package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_setting")
public class Setting implements Serializable {

    private String parameterName;

    private String parameterCode;

    private String parameterValue;

}

package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_setting")
public class Setting implements Serializable {

    @TableField("parameterName")
    private String parameterName;
    @TableField("parameterCode")
    private String parameterCode;
    @TableField("parameterValue")
    private String parameterValue;

}

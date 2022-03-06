package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_setting")
public class Setting {

    private String parameterName;

    private String parameterCode;

    private String parameterValue;

}

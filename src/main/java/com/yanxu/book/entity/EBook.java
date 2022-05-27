package com.yanxu.book.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@TableName("ebook")
@Data
public class EBook implements Serializable {

    private static final long serialVersionUID = 4018806646911947180L;

    @TableField("ebook_name")
    private String ebookName;

    @TableField("file_group")
    private String fileGroup;

    @TableField("file_url")
    private String fileUrl;


}

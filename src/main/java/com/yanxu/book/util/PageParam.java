package com.yanxu.book.util;

import com.github.pagehelper.IPage;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PageParam<T> implements IPage {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String orderBy;

    private T param;

    public PageParam<T> setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }
}

package com.yanxu.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanxu.book.entity.Book;

import java.util.List;

public interface BookInsertService extends IService<Book> {

    void bookInsert(List<Book> list);


}

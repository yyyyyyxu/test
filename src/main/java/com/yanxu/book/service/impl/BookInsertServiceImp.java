package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.Book;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.service.BookInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookInsertServiceImp extends ServiceImpl<BaseMapper<Book>,Book> implements BookInsertService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public void bookInsert(List<Book> list) {
    }
}

package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.Book;
import com.yanxu.book.entity.User;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.service.HadToBorrowBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HadToBorrowBooksServiceImp extends ServiceImpl<BaseMapper<Book>,Book> implements HadToBorrowBooksService {

    @Autowired
    BookMapper bookMapper;


    /**
     * create by: yanxu
     * description: 个人已借图书查询
     * create time: 2022/3/10 14:04
     * @Param: null
     * @return
     */
    @Override
    public List<Book> list(User user) {
        return bookMapper.selectList(new QueryWrapper<Book>().lambda().eq(Book::getUserId,user.getUserId()));
    }
}

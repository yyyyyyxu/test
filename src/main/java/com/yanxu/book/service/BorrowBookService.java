package com.yanxu.book.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanxu.book.entity.Book;
import com.yanxu.book.entity.User;
import com.yanxu.book.exeception.ResultExecept;

public interface BorrowBookService extends IService<Book> {
     void borrowBook(User user, String bookCode) throws ResultExecept;
}

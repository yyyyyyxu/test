package com.yanxu.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanxu.book.entity.Book;
import com.yanxu.book.entity.User;

public interface HadToBorrowBooksService extends IService<Book> ,BasePageService<User,Book>{
}

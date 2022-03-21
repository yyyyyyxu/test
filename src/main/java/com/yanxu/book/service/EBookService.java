package com.yanxu.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanxu.book.entity.Book;
import com.yanxu.book.entity.EBook;
import com.yanxu.book.entity.User;

public interface EBookService extends IService<EBook>,BasePageService<EBook,EBook> {
}

package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.Book;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.service.BookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class BookSearchServiceImp extends ServiceImpl<BaseMapper<Book>, Book> implements BookSearchService {

    @Autowired
    private BookMapper bookMapper;


    public List list(Book book) {
        BufferedOutputStream bufferedOutputStream = null;

        List<Book> allBook;
        allBook = bookMapper.selectList(new QueryWrapper<Book>().lambda().eq(!StringUtils.isEmpty(book.getBookCode()), Book::getBookCode, book.getBookCode())
                .eq(!StringUtils.isEmpty(book.getBookName()), Book::getBookName, book.bookName));
        for (Book book1 : allBook) {
            if (book1.getImage() != null) {
                try {
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("D:\\java程序\\book\\src\\main\\resources\\static\\bookpic\\" + book1.getBookCode() + ".png"));
                    bufferedOutputStream.write(book1.getImage());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return allBook;
    }


}

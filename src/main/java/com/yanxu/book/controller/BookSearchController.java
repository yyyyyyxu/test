package com.yanxu.book.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.yanxu.book.entity.Book;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.service.BookInsertService;
import com.yanxu.book.service.BookSearchService;
import com.yanxu.book.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

@RequestMapping("/book")
@Controller
public class BookSearchController {

    @Autowired
    private BookInsertService bookInsertService;

    @Autowired
    private BookSearchService bookSearchService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private PageParam<Book> pageParam;


    @RequestMapping("insertBook")
    @ResponseBody
    public void insert(@RequestBody List<Book> list) {
        bookInsertService.saveBatch(list);
    }


    @RequestMapping("getBookList")
    public String getBookList(@RequestParam(required = false, defaultValue = "1") String num, String bookName, String bookCode, Model model) {
        int pageNum = Integer.parseInt(num);
        Book book = new Book();
        book.setBookCode(bookCode);
        book.setBookName(bookName);
        pageParam.setPageNum(pageNum);
        pageParam.setParam(book);
        PageInfo<Book> pageInfo = bookSearchService.page(pageParam);
        model.addAttribute("page", pageInfo);
        return "page";
    }

    @PostMapping("uploadLog")
    public String uploadLog(@RequestParam("uploadLog") MultipartFile file, @RequestParam(value = "Code",required = false) String code,Model model) {

        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        if (!file.isEmpty() && !StringUtils.isEmpty(code)) {
            byte[] bytes = new byte[512000];
            int len = 0;
            try {
                bufferedInputStream = new BufferedInputStream(file.getInputStream());
                bufferedInputStream.read(bytes);

                Book book = new Book();
                book.setImage(bytes);
                bookMapper.update(book, new UpdateWrapper<Book>().lambda().eq(Book::getBookCode, code));
                model.addAttribute("mag", new String("成功"));
                return "forward:/book/getBookList";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("mag", new String("失败"));
                return "forward:/book/getBookList";
            } finally {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            model.addAttribute("mag", new String("失败"));
            return "forward:/book/getBookList";
        }
    }

    @GetMapping("bookDetails")
    @ResponseBody
    public String bookDetails(String bookCode){
        Book book = bookMapper.selectOne(new QueryWrapper<Book>().lambda().eq(Book::getBookCode,bookCode));
        return book.getBookName();
    }


}
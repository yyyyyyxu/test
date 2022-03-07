package com.yanxu.book.controller;

import com.github.pagehelper.PageInfo;
import com.yanxu.book.aspect.LoginInAspect;
import com.yanxu.book.entity.BookBorrowHistory;
import com.yanxu.book.mapper.BookBorrowHistoryMapper;
import com.yanxu.book.service.BookBorrowHistoryService;
import com.yanxu.book.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("getBookBorrowHistory")
public class BookBorrowHistorySearchController {

    @Autowired
    private PageParam<BookBorrowHistory> pageParam;

    @Autowired
    private BookBorrowHistoryService bookBorrowHistoryService;

    @Autowired
    private BookBorrowHistoryMapper borrowHistoryMapper;


    @RequestMapping("allHistory")
    public String allHistory(@RequestParam(required = false, defaultValue = "1") String num, @RequestParam(value = "username", required = false) String userName, Model model) {
        int pageNum = Integer.parseInt(num);
        BookBorrowHistory bookBorrowHistory = new BookBorrowHistory();
        bookBorrowHistory.setUserName(userName);
        pageParam.setPageNum(pageNum);
        pageParam.setParam(bookBorrowHistory);
        PageInfo<BookBorrowHistory> pageInfo = bookBorrowHistoryService.page(pageParam);
        model.addAttribute("page", pageInfo);
        return "page";
    }

    @RequestMapping("selfHistory")
    public String selfHistory(@RequestParam(required = false, defaultValue = "1") String num,@RequestParam(value = "name") String name, Model model) {
        int pageNum = Integer.parseInt(num);
        BookBorrowHistory bookBorrowHistory = new BookBorrowHistory();
        bookBorrowHistory.setUserName(name);
        pageParam.setPageNum(pageNum);
        pageParam.setParam(bookBorrowHistory);
        PageInfo<BookBorrowHistory> pageInfo = bookBorrowHistoryService.page(pageParam);
        model.addAttribute("page", pageInfo);
        return "page";
    }
}

package com.yanxu.book.controller;

import com.github.pagehelper.PageInfo;
import com.yanxu.book.entity.BookBorrowHistory;
import com.yanxu.book.entity.User;
import com.yanxu.book.mapper.BookBorrowHistoryMapper;
import com.yanxu.book.service.BookBorrowHistoryService;
import com.yanxu.book.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("getBookBorrowHistory")
public class BookBorrowHistorySearchController {

    @Autowired
    private PageParam<BookBorrowHistory> pageParam;

    @Autowired
    private BookBorrowHistoryService bookBorrowHistoryService;

    @Autowired
    private BookBorrowHistoryMapper borrowHistoryMapper;

    /**
     * create by: yanxu
     * description: TODO
     * create time: 2022/3/8 11:13
     * @Param: null
     * @return
     */
    @RequestMapping("allHistory")
    public String allHistory(@RequestParam(required = false, defaultValue = "1") String num, @RequestParam(value = "username", required = false) String userName,@RequestParam(value = "bookname", required = false) String bookname, Model model) {
        int pageNum = Integer.parseInt(num);
        BookBorrowHistory bookBorrowHistory = new BookBorrowHistory();
        bookBorrowHistory.setUserName(userName);
        bookBorrowHistory.setBorrowingBookname(bookname);
        pageParam.setPageNum(pageNum);
        pageParam.setParam(bookBorrowHistory);
        pageParam.setOrderBy("creat_time");
        PageInfo<BookBorrowHistory> pageInfo = bookBorrowHistoryService.page(pageParam);
        model.addAttribute("page", pageInfo);
        return "allHistory";
    }

    /**
     * create by: yanxu
     * description: TODO
     * create time: 2022/3/8 11:12
     * @Param:num,model,request
     * @return
     */
    @RequestMapping("selfHistory")
    public String selfHistory(@RequestParam(required = false, defaultValue = "1") String num, Model model, HttpServletRequest request) {
        int pageNum = Integer.parseInt(num);
        BookBorrowHistory bookBorrowHistory = new BookBorrowHistory();
        User user=(User) request.getSession().getAttribute("user");
        bookBorrowHistory.setUserName(user.getUserId());
        pageParam.setPageNum(pageNum);
        pageParam.setParam(bookBorrowHistory);
        PageInfo<BookBorrowHistory> pageInfo = bookBorrowHistoryService.page(pageParam);
        model.addAttribute("page", pageInfo);
        return "selfHistory";
    }
}

package com.yanxu.book.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.yanxu.book.entity.Book;
import com.yanxu.book.entity.User;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.service.*;
import com.yanxu.book.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private PageParam<Book> pageParamBook;

    @Autowired
    private PageParam<User> pageParamUser;

    @Autowired
    private BorrowBookService borrowBookService;

    @Autowired
    HadToBorrowBooksService hadToBorrowBooksService;

    @Autowired
    FdfsService fdfsService;


    @RequestMapping("insertBooks")
    public String insert() {
          return "InsertBook";
    }


    @RequestMapping("userGetBookList")
    public String userGetBookList(@RequestParam(required = false,value = "name") String name,@RequestParam(required = false, defaultValue = "1") String num, String bookName, String bookCode, Model model,HttpServletRequest request) {
        int pageNum = Integer.parseInt(num);
        Book book = new Book();
        book.setBookCode(bookCode);
        book.setBookName(bookName);
        pageParamBook.setPageNum(pageNum);
        pageParamBook.setParam(book);
        PageInfo<Book> pageInfo = bookSearchService.page(pageParamBook);
        model.addAttribute("page", pageInfo);
        request.getSession().setAttribute("name",name);
        return "UserGetBookList";
    }

    @RequestMapping("managerGetBookList")
    public String managerGetBookList(@RequestParam(required = false,value = "name") String name,@RequestParam(required = false, defaultValue = "1") String num, String bookName, String bookCode, Model model,HttpServletRequest request) {
        int pageNum = Integer.parseInt(num);
        Book book = new Book();
        book.setBookCode(bookCode);
        book.setBookName(bookName);
        pageParamBook.setPageNum(pageNum);
        pageParamBook.setParam(book);
        PageInfo<Book> pageInfo = bookSearchService.page(pageParamBook);
        model.addAttribute("page", pageInfo);
        request.getSession().setAttribute("name",name);
        return "ManagerGetBookList";
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
                return "forward:/book/insertBooks";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("mag", new String("失败"));
                return "forward:/book/insertBooks";
            } finally {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            model.addAttribute("mag", new String("失败"));
            return "forward:/book/insertBooks";
        }
    }

    @GetMapping("bookDetails")
    public String bookDetails(String bookCode,Model model){
        Book book = bookMapper.selectOne(new QueryWrapper<Book>().lambda().eq(Book::getBookCode,bookCode));
        model.addAttribute("bookName",book.getBookName());
        model.addAttribute("bookDetail",book.getBookDetail());
        model.addAttribute("bookCode",book.getBookCode());
        model.addAttribute("bookFlag",book.getFlag());
        return "BookDetail";
    }

    @RequestMapping("borrowBook")
    public String borrowBook(@RequestParam("bookCode") String bookCode,HttpServletRequest request,Model model){
        User user=(User) request.getSession().getAttribute("user");
        try {
            borrowBookService.borrowBook(user,bookCode);
        }catch (Exception e){
            model.addAttribute("msg",e.getMessage());
            return "succeed";
        }
            model.addAttribute("msg","succeed");
        return "succeed";
    }

    @RequestMapping("managerGetBorrowedBook")
    public String managerGetBorrowedBook(@RequestParam(required = false, defaultValue = "1") String num,HttpServletRequest request,Model model){
        User user=(User) request.getSession().getAttribute("user");
        int pageNum = Integer.parseInt(num);
        pageParamUser.setPageNum(pageNum);
        pageParamUser.setParam(user);
        PageInfo<Book> pageInfo = hadToBorrowBooksService.page(pageParamUser);
        model.addAttribute("page", pageInfo);
        return "ManagerGetBorrowedBook";
    }

    @RequestMapping("userGetBorrowedBook")
    public String userGetBorrowedBook(@RequestParam(required = false, defaultValue = "1") String num,HttpServletRequest request,Model model){
        User user=(User) request.getSession().getAttribute("user");
        int pageNum = Integer.parseInt(num);
        pageParamUser.setPageNum(pageNum);
        pageParamUser.setParam(user);
        PageInfo<Book> pageInfo = hadToBorrowBooksService.page(pageParamUser);
        model.addAttribute("page", pageInfo);
        return "UserGetBorrowedBook";
    }

    @RequestMapping("ebookUpload")
    public String ebookUpload(){
        return "EbookUpload";
    }

    @PostMapping("getEbookUpload")
    public String getEbookUpload(@RequestParam("upload") MultipartFile file, @RequestParam(value = "name",required = false) String name,Model model) {
        try {
            String url=fdfsService.upload(file,name);
            System.out.println(url);
            model.addAttribute("mag", new String("成功"));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("mag", new String("失败"));
        }
        return "EbookUpload";
    }

}
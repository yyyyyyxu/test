package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.Book;
import com.yanxu.book.entity.BookBorrowHistory;
import com.yanxu.book.entity.User;
import com.yanxu.book.exeception.ResultExecept;
import com.yanxu.book.mapper.BookBorrowHistoryMapper;
import com.yanxu.book.mapper.BookMapper;
import com.yanxu.book.mapper.UserMapper;
import com.yanxu.book.service.BookBorrowHistoryService;
import com.yanxu.book.service.BorrowBookService;
import com.yanxu.book.settingEnum.ActivityException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowBookServiceImp extends ServiceImpl<BaseMapper<Book>,Book> implements BorrowBookService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookBorrowHistoryService bookBorrowHistoryService;

    @Autowired
    BookBorrowHistoryMapper borrowHistoryMapper;


    /**
     * create by: yanxu
     * description:借书服务方法
     * create time: 2022/3/9 13:14
     * @Param: User,bookCode
     * @return void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void borrowBook(User user,String bookCode) throws ResultExecept{
        User getUser=userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserId,user.getUserId()));
        Book getBook=bookMapper.selectOne(new QueryWrapper<Book>().lambda().eq(Book::getBookCode,bookCode));
        if ((getUser.getNumberOfBorrow()+1)<=getUser.getMaxNumberOfBorrow()){
            Book book=new Book();
            book.setUserId(getUser.getUserId());
            book.setFlag("1");

            BookBorrowHistory bookBorrowHistory=new BookBorrowHistory();
            bookBorrowHistory.setBorrowingBookname(getBook.getBookName());
            bookBorrowHistory.setUserName(getUser.getUserId());
            getUser.setNumberOfBorrow(getUser.getNumberOfBorrow()+1);
            bookMapper.update(book,new UpdateWrapper<Book>().lambda().eq(Book::getBookCode,bookCode));
            borrowHistoryMapper.insert(bookBorrowHistory);

            userMapper.update(getUser,new UpdateWrapper<User>().lambda().eq(User::getUserId,getUser.getUserId()));
        }else {throw new ResultExecept(ActivityException.BORROW_BOOK_NUMBER_GETMAX.getCode(),ActivityException.BORROW_BOOK_NUMBER_GETMAX.getMessage());}

    }



}

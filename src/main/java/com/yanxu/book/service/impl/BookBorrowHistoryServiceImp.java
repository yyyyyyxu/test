package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.BookBorrowHistory;
import com.yanxu.book.mapper.BookBorrowHistoryMapper;
import com.yanxu.book.service.BookBorrowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

public class BookBorrowHistoryServiceImp extends ServiceImpl<BaseMapper<BookBorrowHistory>, BookBorrowHistory> implements BookBorrowHistoryService {

    @Autowired
    BookBorrowHistoryMapper bookBorrowHistoryMapper;

    /**
     * create by: yanxu
     * description: 借阅记录分页
     * create time: 2022/3/7 14:28
     * @Param: BorrowHistory
     * @return BorrowHistory
     */
    @Override
    public List<BookBorrowHistory> list(BookBorrowHistory bookBorrowHistory) {
        List<BookBorrowHistory> borrowHistories= bookBorrowHistoryMapper.selectList(new QueryWrapper<BookBorrowHistory>().lambda().eq(!StringUtils.isEmpty(bookBorrowHistory.getUserName()), BookBorrowHistory::getUserName, bookBorrowHistory.getUserName())
                .eq(!StringUtils.isEmpty(bookBorrowHistory.getBorrowingType()), BookBorrowHistory::getBorrowingType, bookBorrowHistory.getBorrowingType())
                .eq(!StringUtils.isEmpty(bookBorrowHistory.getCreatTime()), BookBorrowHistory::getCreatTime, bookBorrowHistory.getCreatTime()));
        return borrowHistories;
    }
}

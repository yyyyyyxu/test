package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.BookBorrowHistory;
import com.yanxu.book.mapper.BookBorrowHistoryMapper;
import com.yanxu.book.service.BookBorrowHistoryService;
import com.yanxu.book.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
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
                .eq(!StringUtils.isEmpty(bookBorrowHistory.getBorrowingBookname()), BookBorrowHistory::getBorrowingBookname, bookBorrowHistory.getBorrowingBookname())
                .eq(!StringUtils.isEmpty(bookBorrowHistory.getCreatTime()), BookBorrowHistory::getCreatTime, bookBorrowHistory.getCreatTime()));
       for (BookBorrowHistory c:borrowHistories){
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(c.getCreatTime());
            calendar.add(Calendar.HOUR,-8);
            DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString=simpleDateFormat.format(calendar.getTime());
           try {
               Date date=simpleDateFormat.parse(dateString);
               c.setCreatTime(date);
           } catch (ParseException e) {
               e.printStackTrace();
           }

       }
        return borrowHistories;
    }
}

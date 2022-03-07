package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.BorrowHistory;
import com.yanxu.book.mapper.BorrowHistoryMapper;
import com.yanxu.book.service.BookBorrowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

public class BookBorrowHistoryServiceImp extends ServiceImpl<BaseMapper<BorrowHistory>, BorrowHistory> implements BookBorrowHistoryService {

    @Autowired
    BorrowHistoryMapper borrowHistoryMapper;

    /**
     * create by: yanxu
     * description: 借阅记录分页
     * create time: 2022/3/7 14:28
     * @Param: BorrowHistory
     * @return BorrowHistory
     */
    @Override
    public List<BorrowHistory> list(BorrowHistory borrowHistory) {
        List<BorrowHistory> borrowHistories=borrowHistoryMapper.selectList(new QueryWrapper<BorrowHistory>().lambda().eq(!StringUtils.isEmpty(borrowHistory.getUserName()),BorrowHistory::getUserName,borrowHistory.getUserName())
                .eq(!StringUtils.isEmpty(borrowHistory.getBorrowingType()),BorrowHistory::getBorrowingType,borrowHistory.getBorrowingType())
                .eq(!StringUtils.isEmpty(borrowHistory.getCreatTime()),BorrowHistory::getCreatTime,borrowHistory.getCreatTime()));
        return borrowHistories;
    }
}

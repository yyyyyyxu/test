package com.yanxu.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanxu.book.entity.BookBorrowHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BookBorrowHistoryMapper extends BaseMapper<BookBorrowHistory> {
}

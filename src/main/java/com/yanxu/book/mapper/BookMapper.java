package com.yanxu.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanxu.book.entity.Book;
import com.yanxu.book.entity.ExpireyUserRemind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface BookMapper extends BaseMapper<Book> {

    List<ExpireyUserRemind> getExpireyUser(@Param("date") String date,@Param("nowDate") String nowDate);

    List<ExpireyUserRemind> getOverdueUser(@Param("date") String date);
}

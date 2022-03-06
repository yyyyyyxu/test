package com.yanxu.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanxu.book.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}

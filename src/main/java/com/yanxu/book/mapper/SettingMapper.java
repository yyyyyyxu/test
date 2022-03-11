package com.yanxu.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanxu.book.entity.Setting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SettingMapper extends BaseMapper<Setting> {
}

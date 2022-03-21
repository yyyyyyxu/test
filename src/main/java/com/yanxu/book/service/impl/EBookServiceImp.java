package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanxu.book.entity.EBook;
import com.yanxu.book.mapper.EBookMapper;
import com.yanxu.book.service.EBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class EBookServiceImp extends ServiceImpl<BaseMapper<EBook>,EBook> implements EBookService {

    @Autowired
    EBookMapper eBookMapper;

    @Override
    public List<EBook> list(EBook eBook) {
        List<EBook> eBooks = eBookMapper.selectList(new QueryWrapper<EBook>().lambda().eq(!StringUtils.isEmpty(eBook.getEbookName()),EBook::getEbookName, eBook.getEbookName()));
        return eBooks;
    }
}

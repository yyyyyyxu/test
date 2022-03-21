package com.yanxu.book.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.yanxu.book.entity.Book;
import com.yanxu.book.entity.EBook;
import com.yanxu.book.service.EBookService;
import com.yanxu.book.service.FdfsService;
import com.yanxu.book.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ebook")
public class EBookController {

    @Autowired
    private PageParam<EBook> pageParam;

    @Autowired
    EBookService eBookService;

    @Autowired
    private FdfsService fdfsService;

    @RequestMapping("ebookUpload")
    public String ebookUpload() {
        return "EbookUpload";
    }

    @PostMapping("getEbookUpload")
    public String getEbookUpload(@RequestParam("upload") MultipartFile file, @RequestParam(value = "name", required = false) String name, Model model) {
        try {
            String url = fdfsService.upload(file, name);
            System.out.println(url);
            model.addAttribute("mag", new String("成功"));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("mag", new String("失败"));
        }
        return "EbookUpload";
    }

    @RequestMapping("download")
    public String download(@RequestParam("name") String name, HttpServletResponse response, HttpServletRequest request, Model model) {


        try {
            fdfsService.download(name, response);
            model.addAttribute("mag", new String("成功"));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("mag", new String("失败"));
        }
        return "EbookUpload";
    }

    @RequestMapping("/getEbook")
    public String getEbook(@RequestParam(value = "name", required = false) String name, @RequestParam(required = false, defaultValue = "1") String num, Model model) {
        EBook eBook = new EBook();
        eBook.setEbookName(name);
        pageParam.setParam(eBook);
        pageParam.setPageNum(Integer.parseInt(num));
        pageParam.setOrderBy(null);
        PageInfo<EBook> pageInfo = eBookService.page(pageParam);
        model.addAttribute("page", pageInfo);
        return "EBookGetList";
    }
}

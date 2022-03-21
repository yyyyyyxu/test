package com.yanxu.book.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FdfsService {

    public String upload(MultipartFile file, String fileExtName) throws Exception;

    public void download(String url, HttpServletResponse httpServletResponse) throws Exception;
}

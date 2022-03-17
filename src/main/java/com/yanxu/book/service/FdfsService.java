package com.yanxu.book.service;

import org.springframework.web.multipart.MultipartFile;

public interface FdfsService {

    public String upload(MultipartFile file, String fileExtName) throws Exception;

    public MultipartFile download(String group,String url);
}

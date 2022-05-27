package com.yanxu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yanxu.book.entity.EBook;
import com.yanxu.book.mapper.EBookMapper;
import com.yanxu.book.service.FdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Service
public class FdfsServiceImpl implements FdfsService {

    @Autowired
    FastFileStorageClient fastFileStorageClient;

    @Autowired
    EBookMapper eBookMapper;


    @Override
    public String upload(MultipartFile file, String fileExtName) throws Exception {

        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtName, null);
        EBook eBook = new EBook();
        eBook.setEbookName(fileExtName);
        eBook.setFileGroup(storePath.getGroup());
        eBook.setFileUrl(storePath.getPath());
        eBookMapper.insert(eBook);
        String path = storePath.getFullPath();
        return path;
    }

    @Override
    public void download(String name, HttpServletResponse httpServletResponse) throws Exception {

        try {
            EBook eBook = eBookMapper.selectOne(new QueryWrapper<EBook>().lambda().eq(EBook::getEbookName, name));
            String fileName = eBook.getEbookName();
            DownloadByteArray callback = new DownloadByteArray();
            byte[] b = fastFileStorageClient.downloadFile(eBook.getFileGroup(), eBook.getFileUrl(), callback);
            httpServletResponse.reset();
            httpServletResponse.setContentType("application/x-download");
            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=\"" + fileName+".pdf" + "\"");
            httpServletResponse.getOutputStream().write(b);
            httpServletResponse.getOutputStream().close();
        } catch (Exception e) {
            throw e;
        }
    }

//    @Override
//    public MultipartFile download(String group, String url, HttpServletResponse httpServletResponse) {
//
//
//            //操作数据库，读取文件上传时的信息
//            FileInfo fileInfo = service.getFileInfo(id);
//            if(fileInfo!=null){
//                try {
//                    String fileName = URLEncoder.encode(fileInfo.getFileName(),"UTF8");
//                    String fileUrl =
//                    String filepath = fileUrl.substring(fileUrl.lastIndexOf("group1/")+7);
//                    DownloadByteArray callback = new DownloadByteArray();
//                    byte[] b = fastFileStorageClient.downloadFile("group1", filepath,callback);
//                    httpServletResponse.reset();
//                    httpServletResponse.setContentType("application/x-download");
//                    httpServletResponse.addHeader("Content-Disposition" ,"attachment;filename=\"" +fileName+ "\"");
//                    httpServletResponse.getOutputStream().write(b);
//                    httpServletResponse.getOutputStream().close();
//
//
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }
}
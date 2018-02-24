package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.interf.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * create by YuWen
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    //上传文件,并将上传文件的 文件名(经过处理的,给文件名加前缀)  返回出去
    //因为如果两个上传了同名文件,则原来的会被覆盖
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();   //拿到上传文件的原始文件名(D:/image.dog.jpg---->dog.jpg)
        //获取文件扩展名(dog.jpg---->jpg)
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." +fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名是:{},上传的路径是:{},新文件名是:{} " ,
                        fileName, path, uploadFileName);
        File fileDir = new File(path);      //声明目录文件
        if (!fileDir.exists()) {
            //赋予写权限,因为启动tomcat的用户权限不一定拥有在tomcat  webapp下发布文件夹的权限
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);    //new File(String parent, String child)
        try {
            file.transferTo(targetFile);
            //文件上传成功
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));     //文件已成功上传到FTP服务器
            // 上传完之后,删除upload下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();

    }
}

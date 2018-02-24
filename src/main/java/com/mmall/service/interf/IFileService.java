package com.mmall.service.interf;

import org.springframework.web.multipart.MultipartFile;

/**
 * create by YuWen
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}

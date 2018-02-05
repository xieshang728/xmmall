package com.xmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xies
 * @date 2018/1/26
 */
public interface IFileService {
    String upload(MultipartFile multipartFile, String path);
}

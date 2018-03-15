package com.xmall.service.impl;

import com.google.common.collect.Lists;
import com.xmall.service.IFileService;
import com.xmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author xies
 * @date 2018/1/26
 */
@Service
@Slf4j
public class FileServiceImpl implements IFileService {


    @Override
    public String upload(MultipartFile multipartFile, String path) {
        File targetFile = null;
        try {
            String fileName = multipartFile.getOriginalFilename();
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            targetFile = new File(path, uploadFileName);
            multipartFile.transferTo(targetFile);
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (Exception e) {
            log.error("FileServiceImpl exception: " + e.toString());
            return null;
        }
        return targetFile.getName();
    }
}

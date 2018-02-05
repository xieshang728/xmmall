package com.xmall.service.impl;

import com.google.common.collect.Lists;
import com.xmall.service.IFileService;
import com.xmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author xies
 * @date 2018/1/26
 */
@Service
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
            logger.error("FileServiceImpl exception: " + e.toString());
            return null;
        }
        return targetFile.getName();
    }
}

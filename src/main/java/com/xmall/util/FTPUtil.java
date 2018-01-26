package com.xmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * @author xies
 * @date 2018/1/25
 */
public class FTPUtil {

    private final static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private final static String ftpIp = PropertiesUtil.getProperty("ftp.server");

    private final static String ftpUser = PropertiesUtil.getProperty("ftp.user");

    private final static String ftpPassword = PropertiesUtil.getProperty("ftp.password");


    public  boolean connectServer(String ip ,int port,String user,String pwd){
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try{
            ftpClient.connect(InetAddress.getByName(ip));
            isSuccess = ftpClient.login(user,pwd);
        }catch (Exception e){
            logger.error("连接服务器异常"+e.toString());
        }
        return isSuccess;
    }


    private String ip ;

    private int port ;

    private String user ;

    private String pwd ;

    private FTPClient ftpClient ;


    public FTPUtil(String ip, int port, String user, String pwd){
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }


    public static boolean uploadFile(List<File> fileList) throws IOException{
        FTPUtil ftpUtil = new FTPUtil(ftpIp,21,ftpUser,ftpPassword);
        System.out.println("ftpIp: "+ftpIp);
        System.out.println("ftpUser: "+ftpUser);
        System.out.println("ftpPassword: "+ftpPassword);
        logger.info("开始连接服务器");
        boolean result = ftpUtil.uploadFile("img",fileList);
        logger.info("开始连接ftp服务器，结束上传，上传结果是：{}");
        return result;
    }



    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException{
        boolean upload = true;
        FileInputStream fis = null ;
        if(connectServer(this.ip,this.port,this.user,this.pwd)){
            try{
                //ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(4096);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for(File file : fileList){
                    fis = new FileInputStream(file);
                    ftpClient.storeFile(file.getName(),fis);
                }
            }catch (IOException e){
                logger.error("上传文件异常"+e.toString());
                upload = false;
                e.printStackTrace();
            }finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return upload;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}

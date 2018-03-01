package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * create by YuWen
 */
@Slf4j
public class FTPUtil {

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");
    
    private String ip;
    private int port;
    private String user;
    private String pass;
    private FTPClient ftpClient;

    private FTPUtil(String ip, int port, String user, String pass) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }

    //返回上传成功还是失败 （对外开放）
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        log.info("开始连接FTP服务器");
        boolean result = ftpUtil.uploadFile("image",fileList);
        log.info("结束上传,上传结果: {}", result);

        return result;
    }

    //上传具体逻辑,并返回上传状态
    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = false;
        FileInputStream fis = null;
        //连接FTP服务器
        if (connectServer(this.getIp(), this.getPort(), this.getUser(), this.getPass())) {
            try {
                //更改工作目录
                ftpClient.changeWorkingDirectory(remotePath);
                //设置缓冲区
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);    //将文件类型都设置成2进制文件类型,防止乱码
                ftpClient.enterLocalPassiveMode();

                //开始上传
                for (File fileItem : fileList) {
                    fis = new FileInputStream(fileItem);
                    //存储文件  文件名,输入流
                    ftpClient.storeFile(fileItem.getName(), fis);
                }
            } catch (IOException e) {
                log.error("上传文件异常",e);
                uploaded = false;
            } finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    //连接FTP服务器
    private boolean connectServer(String ip, int port, String user, String pass) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            //连接FTP服务器
            ftpClient.connect(ip);
            //登录
            isSuccess = ftpClient.login(ftpUser, ftpPass);
        } catch (IOException e) {
            log.error("连接FTP服务器异常",e);
        }
        return isSuccess;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}

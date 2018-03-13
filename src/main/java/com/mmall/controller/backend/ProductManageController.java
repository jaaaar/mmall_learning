package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.interf.IFileService;
import com.mmall.service.interf.IProductService;
import com.mmall.service.interf.IUserService;
import com.mmall.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * create by YuWen
 * 产品相关
 */
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IFileService iFileService;

    //新增或者更新产品
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest request, Product product) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(
//                    ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //增加产品业务逻辑
//            return iProductService.saveOrUpdateProduct(product);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }

        return iProductService.saveOrUpdateProduct(product);
    }


    //修改产品销售状态
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaveStatus(HttpServletRequest request, Integer productId, Integer productStatus) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(
//                    ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //修改产品上下架状态
//            return iProductService.setSaleStatus(productId, productStatus);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }

        return iProductService.setSaleStatus(productId, productStatus);
    }


    //获取产品详情
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest request, Integer productId) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(
//                    ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //获取产品详情
//            return iProductService.manageProductDetail(productId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }

        return iProductService.manageProductDetail(productId);
    }


    //后台获取产品列表 动态分页
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpServletRequest request,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(
//                    ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //获取产品详情
//            return iProductService.getProductList(pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }

        return iProductService.getProductList(pageNum, pageSize);
    }

    //后台产品搜索  根据产品名根据产品id
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest request, String productName, Integer productId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(
//                    ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //搜索产品
//            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }

        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }


    //Spring MVC文件上传
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(
            @RequestParam(value = "upload_file",required = false) MultipartFile file,
            HttpServletRequest request) {

//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(
//                    ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //上传文件
//            //上传文件的文件夹就叫upload,此文件夹在项目发布后会创建在webapp目录下与 WEB-INF和index.jsp平级
//            //但是不应该把创建文件夹这种操作依赖于业务,可以通过代码来创建这个文件夹
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String targetFileName = iFileService.upload(file, path);
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//
//            Map fileMap = Maps.newHashMap();
//            fileMap.put("uri", targetFileName);
//            fileMap.put("url", url);
//            return ServerResponse.createBySuccess(fileMap);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }

        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    //富文本文件上传
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(
            @RequestParam(value = "upload_file",required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {

//        Map resultMap = Maps.newHashMap();
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            resultMap.put("success", false);
//            resultMap.put("msg", "未登录,请登录管理员");
//            return resultMap;
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            resultMap.put("success", false);
//            resultMap.put("msg", "未登录,请登录管理员");
//            return resultMap;
//        }
//        //富文本中对于返回值有自己的要求,使用的是simditor 所以按照simditor的要求进行返回
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //上传文件
//            //上传文件的文件夹就叫upload,此文件夹在项目发布后会创建在webapp目录下与 WEB-INF和index.jsp平级
//            //但是不应该把创建文件夹这种操作依赖于业务,可以通过代码来创建这个文件夹
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String targetFileName = iFileService.upload(file, path);
//            if (StringUtils.isBlank(targetFileName)) {
//                resultMap.put("success", false);
//                resultMap.put("msg", "上传失败");
//                return resultMap;
//            }
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//            resultMap.put("success", true);
//            resultMap.put("msg", "上传成功");
//            resultMap.put("file_path", url);
//
//            response.addHeader("Access-Controller-Allow-Headers", "X-File-Name");
//            return resultMap;
//        } else {
//            resultMap.put("success", false);
//            resultMap.put("msg", "无权限操作");
//            return resultMap;
//        }


        //上传文件
        //上传文件的文件夹就叫upload,此文件夹在项目发布后会创建在webapp目录下与 WEB-INF和index.jsp平级
        //但是不应该把创建文件夹这种操作依赖于业务,可以通过代码来创建这个文件夹
        Map resultMap = Maps.newHashMap();
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        if (StringUtils.isBlank(targetFileName)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);

        response.addHeader("Access-Controller-Allow-Headers", "X-File-Name");
        return resultMap;
    }

}

package com.mmall.controller.backend;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.interf.ICategoryService;
import com.mmall.service.interf.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisSharededPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * create by YuWen
 * 处理分类
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    //增加分类
    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest request, String categoryName,
                                      @RequestParam(value = "parentId",defaultValue = "0") Integer parentId) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
//                    "用户未登录,请登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //处理分类
//            return iCategoryService.addCategory(categoryName, parentId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及是否具有管理员权限
        return iCategoryService.addCategory(categoryName, parentId);
    }

    //修改品类名字
    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {
        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
//                    "用户未登录,请登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //更新categoryName
//            return iCategoryService.updateCategoryName(categoryName, categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及是否具有管理员权限
        return iCategoryService.updateCategoryName(categoryName, categoryId);
    }


    //获取直接子级的category信息
    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request,
                          @RequestParam(value = "categoyId", defaultValue = "0") Integer categoryId) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
//                    "用户未登录,请登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //查询直接子节点category信息
//            return iCategoryService.getChildrenParallelCategory(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及是否具有管理员权限
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    //递归查询当前节点及所有子节点的id
    @RequestMapping(value = "get_deep_category.do")
    @ResponseBody
    public ServerResponse getChildrenAndDeepChildrenCategory(HttpServletRequest request,
                          @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
//                    "用户未登录,请登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //查询当前节点和递归查询子节点category信息
//            return iCategoryService.selectCategoryAndChildrenById(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及是否具有管理员权限
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }
}

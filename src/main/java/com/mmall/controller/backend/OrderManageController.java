package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.interf.IOrderService;
import com.mmall.service.interf.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisSharededPoolUtil;
import com.mmall.vo.OrderVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * create by YuWen
 * 处理订单
 */
@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IOrderService iOrderService;

    //后台查看订单list页
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpServletRequest request,
                                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorMessage("用户未登录请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //逻辑
//            return iOrderService.manageList(pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }


        //全部通过拦截器验证是否登录以及是否具有管理员权限
        return iOrderService.manageList(pageNum, pageSize);
    }

    //后台查看订单详情
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(HttpServletRequest request, Long orderNo) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorMessage("用户未登录请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //逻辑
//            return iOrderService.manageDetail(orderNo);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }
//

        //全部通过拦截器验证是否登录以及是否具有管理员权限
        return iOrderService.manageDetail(orderNo);
    }


    //后台利用订单号进行订单搜索
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpServletRequest request, Long orderNo,
                                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorMessage("用户未登录请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //逻辑
//            return iOrderService.manageSearch(orderNo, pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }
//

        //全部通过拦截器验证是否登录以及是否具有管理员权限
        return iOrderService.manageSearch(orderNo, pageNum, pageSize);
    }


    //后台进行发货
    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpServletRequest request, Long orderNo) {
//        String loginToken = CookieUtil.readLoginCookie(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        User user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorMessage("用户未登录请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //逻辑
//            return iOrderService.manageSendGoods(orderNo);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }


        //全部通过拦截器验证是否登录以及是否具有管理员权限
        return iOrderService.manageSendGoods(orderNo);
    }
}

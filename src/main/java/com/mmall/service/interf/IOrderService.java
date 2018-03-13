package com.mmall.service.interf;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.OrderVo;

import java.util.Map;

/**
 * create by YuWen
 */
public interface IOrderService {

    //portal

    ServerResponse pay(Long orderNum, Integer userId, String path);

    ServerResponse aliCallBack(Map<String, String> params);

    ServerResponse<Boolean> queryOrderPayStauts(Integer userId, Long orderNum);

    ServerResponse createOrder(Integer userId, Integer shippingId);

    ServerResponse<String> cancel(Integer userId, Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);


    //backend

    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);



    //timer task
    //hour个小时未付款的订单进行关闭
    void closeOrder(int hour);
}

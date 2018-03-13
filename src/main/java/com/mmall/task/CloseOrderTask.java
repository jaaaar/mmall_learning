package com.mmall.task;

import com.mmall.service.interf.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.sun.scenario.effect.Crop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * create by YuWen
 *
 * 订单关闭定时任务
 * 因为是tomcat集群,所以需要创建一个分布式锁
 */
@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    //一个定时任务,每分钟执行一次,每次执行扫描订单是否有超过两个小时未支付的,如果有就关闭订单
    @Scheduled(cron = "0 */1 * * * ?")  //每分钟(每个分钟的整数倍)执行一次
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(
                PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }
}

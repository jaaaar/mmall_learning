package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.service.interf.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisSharededPoolUtil;
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
//    @Scheduled(cron = "0 */1 * * * ?")  //每分钟(每个分钟的整数倍)执行一次
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(
                PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }


    /**
     * Redis分布式锁
     */
    @Scheduled(cron = "0 */1 * * * ?")  //每分钟(每个分钟的整数倍)执行一次
    public void closeOrderTaskV2(){
        log.info("关闭订单定时任务启动");
        //锁超时时间
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout"));
        Long setnxResult= RedisSharededPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,
                String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //如果返回值是1,代表setnx设置成功,  获取锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获得分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束");
    }



    private void closeOrder(String lockName) {
        //将锁的有效期设置为50秒,防止死锁
        RedisSharededPoolUtil.expire(lockName, 50);
        log.info("获取{},ThreadName:{}",lockName,Thread.currentThread().getName());

        int hour = Integer.parseInt(
                PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
//        iOrderService.closeOrder(hour);
        RedisSharededPoolUtil.del(lockName);
        log.info("释放:{},ThreadName:{}",lockName, Thread.currentThread().getName());
        log.info("=====================================================");
    }




}

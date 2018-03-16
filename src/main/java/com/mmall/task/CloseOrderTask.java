package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.service.interf.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisSharededPoolUtil;
import com.sun.scenario.effect.Crop;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * create by YuWen
 * <p>
 * 订单关闭定时任务
 * 因为是tomcat集群,所以需要创建一个分布式锁
 */
@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    @PreDestroy
    public void delLock() {
        RedisSharededPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
    }

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
//    @Scheduled(cron = "0 */1 * * * ?")  //每分钟(每个分钟的整数倍)执行一次
    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务启动");
        //锁超时时间
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout"));
        Long setnxResult = RedisSharededPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,
                String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //如果返回值是1,代表setnx设置成功,  获取锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获得分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束");
    }


    @Scheduled(cron = "0 */1 * * * ?")  //每分钟(每个分钟的整数倍)执行一次
    public void closeOrderTaskV3() {
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout"));
        Long setnxResult = RedisSharededPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            //没有获得分布式锁,继续判断,判断时间戳,看是否可以重置并重新获取到锁
            String lockValueStr = RedisSharededPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if (lockValueStr != null && System.currentTimeMillis() > Long.valueOf(lockValueStr)) {
                //可以删除锁

                //再次用当前时间戳进行getset
                //返回给定的key的旧值       ---->判断旧值是否可以获取锁
                //当key 没有旧值时,即Key不存在时会返回nil          ------>获取锁
                //这里set了一个新的value值,并返回旧的值
                String getSetResult = RedisSharededPoolUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
                if (getSetResult == null || (getSetResult != null && StringUtils.equals(lockValueStr, getSetResult))) {
                    //真正获取到锁
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("没有获取到分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            } else {
                log.info("没有获取到分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
    }


    private void closeOrder(String lockName) {
        //将锁的有效期设置为50秒,防止死锁
        RedisSharededPoolUtil.expire(lockName, 5);
        log.info("获取{},ThreadName:{}", lockName, Thread.currentThread().getName());

        int hour = Integer.parseInt(
                PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
//        iOrderService.closeOrder(hour);
        RedisSharededPoolUtil.del(lockName);
        log.info("释放:{},ThreadName:{}", lockName, Thread.currentThread().getName());
        log.info("=====================================================");
    }


}

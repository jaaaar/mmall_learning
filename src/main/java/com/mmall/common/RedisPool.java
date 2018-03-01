package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * create by YuWen
 */
public class RedisPool {

    private static JedisPool pool;      //设置为static 保证jedis连接池在tomcat启动时就被加载进来
    private static Integer maxTotal = PropertiesUtil.getIntProperty("redis.max.total", 20);//最大连接数
    private static Integer maxIdle = PropertiesUtil.getIntProperty("redis.max.idle", 10);//Jedis连接池中最多有多少个状态为Idel(空闲)的jedis实例
    private static Integer minIdle = PropertiesUtil.getIntProperty("redis.min.idle", 2);//Jedis连接池中最少有多少个状态为Idel(空闲)的jedis实例
    /*在jedis连接池中取出实例时(和redis server进行通信)是否要进行验证操作,如果赋值为true则得到的jedis实例为可用的*/
    private static Boolean testOnBorrow = PropertiesUtil.getBooleanProperty("redis.test.borrow", false);
    /*在jedis连接池中还回实例时(和redis server进行通信)是否要进行验证操作,如果赋值为true则放回jedis pool的jedis实例为可用的*/
    private static Boolean testOnReturn = PropertiesUtil.getBooleanProperty("redis.test.return", false);

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.valueOf(PropertiesUtil.getProperty("redis.port"));


    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true); //连接耗尽时是否阻塞,false会抛出异常, true会阻塞直到超时,会抛出超时异常。源码中默认为true,此条不必设置
        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);    //超时时间单位为毫秒
    }

    static {
        initPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("key", "value");
        returnResource(jedis);

        pool.destroy();         //临时调用销毁连接池中的所有连接
        System.out.println("program is end");
    }
}
